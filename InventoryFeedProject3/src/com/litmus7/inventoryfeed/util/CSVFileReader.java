package com.litmus7.inventoryfeed.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.litmus7.inventoryfeed.dto.Product;
import com.litmus7.inventoryfeed.exception.ProductApplicationException;

public class CSVFileReader {
	static FileReader fr;
	static BufferedReader br;

	public static List<File> getCSVFiles(String folderpath){
		File inputFolder=new File(folderpath);
		List<File> inputFiles=new ArrayList<>(Arrays.asList(inputFolder.listFiles()));
		return inputFiles;
	}
	
	public static List<Product> readCSVFile(File file) throws ProductApplicationException{
		try(BufferedReader br=new BufferedReader(new FileReader(file))) {
			List<Product> products=new ArrayList<>();
	    	br.readLine();
			String line;
			while((line=br.readLine())!=null) {
				line=line.replace("\"", "");
				String[] fields=line.split(",");
				Product product=new Product();
				product.setSku(Integer.parseInt(fields[0]));
				product.setProductName(fields[1]);
				product.setQuantity(Integer.parseInt(fields[2]));
				product.setPrice(Double.parseDouble(fields[3]));
				products.add(product);
			}
			return products;
		}
		catch(IOException e) {
			throw new ProductApplicationException(101);
		}
		
	}
}
