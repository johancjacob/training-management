package com.litmus7.inventoryfeed.controller;

import java.io.File;
import java.util.List;

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
			InventoryFeedService service=new InventoryFeedService(inputFiles);
			Thread t1=new Thread(service);
			t1.start();
			try {
				t1.join();
			} catch (InterruptedException e) {
				
			}
			if(service.countOfSavedFiles==countOfInputFiles) {
				logger.trace(LogMessages.SAVED_ALL_FILES_LOG_MESSAGE,service.countOfSavedFiles);
				return new Response<>(StatusCode.SUCCESS,"Successfully saved records from all "+service.countOfSavedFiles+" files to Db");
			}
			else if(service.countOfSavedFiles==0) {
				logger.trace(LogMessages.SAVED_NO_FILES_LOG_MESSAGE);
				return new Response<>(StatusCode.FAILURE,"Could'nt save any of the files to Db");
			}
			else {
				logger.trace(LogMessages.SAVED_SOME_FILES_LOG_MESSAGE,service.countOfSavedFiles,countOfInputFiles);
				return new Response<>(StatusCode.PARTIAL_SUCCESS,"Could save only "+service.countOfSavedFiles+" file(s) out of the total "+countOfInputFiles+" files.");
			}
		}
		catch(ProductApplicationException e) {
			return new Response<>(StatusCode.FAILURE,ErrorCodeLoader.getMessage(String.valueOf(e.errorcode)));
		}
	}



}
