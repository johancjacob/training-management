package com.litmus7.inventoryfeed.service;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.litmus7.inventoryfeed.constants.FilePaths;
import com.litmus7.inventoryfeed.constants.LogMessages;
import com.litmus7.inventoryfeed.dao.InventoryFeedDao;
import com.litmus7.inventoryfeed.dto.Product;
import com.litmus7.inventoryfeed.exception.ProductApplicationException;
import com.litmus7.inventoryfeed.util.CSVFileReader;
import com.litmus7.inventoryfeed.util.DataValidator;
import com.litmus7.inventoryfeed.util.FileMover;

public class InventoryFeedService implements Runnable{
	private static final Logger logger=LogManager.getLogger(InventoryFeedService.class);
	AtomicInteger savedCount;
	InventoryFeedDao dao=new InventoryFeedDao();
	File inputFile;
	public InventoryFeedService(File inputFile,AtomicInteger savedCount) {
		this.inputFile=inputFile;
		this.savedCount=savedCount;
	}
	@Override
	public void run(){
		logger.trace(LogMessages.ENTERED_METHOD_LOG_MESSAGE,"run()");
		try {
			saveFileToDb(inputFile);
			logger.trace("Exiting from run() for inputFile "+inputFile.getName());
		} catch (Exception e) {
			
		}
	}
	
	public void saveFileToDb(File inputFile) {
		logger.trace(LogMessages.ENTERED_METHOD_LOG_MESSAGE,"saveFileToDb()");
			try {
				logger.info("Reading file {}",inputFile);
				List<Product> products=CSVFileReader.readCSVFile(inputFile);
				areValidProducts(products);
				if(products.size()>0) {
					logger.info("Exititng areValidProducts() after getting {} valid product(s)",products.size());
					if(dao.saveProductsInFileToDb(products,inputFile)) {
						logger.info(LogMessages.MOVING_FILE_TO_PROCESSED_FOLDER_LOG_MESSAGE,inputFile.getName());
						FileMover.moveFileToProcessedFolder(inputFile,FilePaths.PROCESSED_FOLDER_PATH);
						logger.info(LogMessages.MOVED_FILE_TO_PROCESSED_LOG_MESSAGE,inputFile.getName());
						savedCount.incrementAndGet();
					}
				}
				else {
					logger.warn("Exiting areValidProducts() after getting zero valid products for {}",inputFile);
					throw new ProductApplicationException(103);
				}
			}
			catch(ProductApplicationException e) {
				logger.info(LogMessages.MOVING_FILE_TO_ERROR_FOLDER_LOG_MESSAGE,inputFile.getName());
				FileMover.moveFileToErrorFolder(inputFile,FilePaths.ERROR_FOLDER_PATH);
			}
		}
	
	public void areValidProducts(List<Product> products){
		logger.trace(LogMessages.ENTERED_METHOD_LOG_MESSAGE,"areValidProducts()");
		Iterator<Product> iterator=products.iterator();
		while(iterator.hasNext()) {
			Product product=iterator.next();
			if(DataValidator.isValidSku(product.getSku()) && DataValidator.isValidProductName(product.getProductName())){
				continue;
			}
			else {
				iterator.remove();
			}
		}
	}

}
