package com.litmus7.inventoryfeed.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.litmus7.inventoryfeed.constants.LogMessages;
import com.litmus7.inventoryfeed.constants.StatusCode;
import com.litmus7.inventoryfeed.dto.Response;
import com.litmus7.inventoryfeed.exception.ProductApplicationException;
import com.litmus7.inventoryfeed.service.InventoryFeedService;
import com.litmus7.inventoryfeed.util.CSVFileReader;
import com.litmus7.inventoryfeed.util.ErrorCodeLoader;

public class InventoryFeedController {	
	private static final Logger logger=LogManager.getLogger(InventoryFeedController.class);
	public Response saveFilesToDb(String folderpath) {
		try {
			logger.trace(LogMessages.ENTERED_METHOD_LOG_MESSAGE,"saveFilesToDb()");
			List<File> inputFiles=CSVFileReader.getCSVFiles(folderpath);
			int countOfInputFiles=inputFiles.size();
			if(countOfInputFiles==0) {
				logger.warn("Exiting saveFilesToDb() because no files exist in the input folder.");
				throw new ProductApplicationException(103);
			}
			for(File inputfile:inputFiles) {
				if(inputfile.getName().endsWith(".csv")) {
					continue;
				}
				else {
					throw new ProductApplicationException(100);
				}
			}
			AtomicInteger savedCount=new AtomicInteger(0);
			List<Thread> threads=new ArrayList();
			for(File inputFile:inputFiles) {
				InventoryFeedService service=new InventoryFeedService(inputFile,savedCount);
				Thread t=new Thread(service);
				t.start();
				threads.add(t);
			}
			for(Thread t:threads) {
				try {
					t.join();
				}
				catch(Exception e) {
					
				}
			}
			if(savedCount.get()==countOfInputFiles) {
				return new Response(StatusCode.SUCCESS,"All files saved successfully");
			}
			else {
				return new Response(StatusCode.PARTIAL_SUCCESS,savedCount.get()+" files out of "+countOfInputFiles+" files saved");
			}
			
		}
		catch(ProductApplicationException e) {
			return new Response<>(StatusCode.FAILURE,ErrorCodeLoader.getMessage(String.valueOf(e.errorcode)));
		}
	}



}
