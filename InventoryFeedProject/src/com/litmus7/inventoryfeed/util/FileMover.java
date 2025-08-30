package com.litmus7.inventoryfeed.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.litmus7.service.InventoryFeedService;


public class FileMover {
	private static final Logger logger=LogManager.getLogger(FileMover.class);
	public static void moveFileToErrorFolder(File inputFile,String errorFolderPath) {
		logger.info("Moving the file {} to error folder",inputFile);
		File errorFolder=new File(errorFolderPath);
		Path destination=Paths.get(errorFolder.getAbsolutePath(),inputFile.getName());
		try {
			Files.move(inputFile.toPath(),destination,StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
		public static void moveFileToProcessedFolder(File inputFile,String errorFolderPath) {
			logger.info("Moving the file {} to processed folder",inputFile);
			File errorFolder=new File(errorFolderPath);
			Path destination=Paths.get(errorFolder.getAbsolutePath(),inputFile.getName());
			try {
				Files.move(inputFile.toPath(),destination,StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		
}
