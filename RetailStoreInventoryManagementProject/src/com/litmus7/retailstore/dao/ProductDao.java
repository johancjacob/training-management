package com.litmus7.retailstore.dao;

import java.util.ArrayList;
import java.util.List;
import com.litmus7.retailstore.exception.ProductApplicationException;

import com.litmus7.retailstore.dto.Product;

public class ProductDao {
	
	//open file and get its handle
	
	public boolean addProduct(Product product) throws ProductApplicationException {
		try {
			//code to add the product into the file.
		}
		catch(Exception e) {
			throw new ProductApplicationException(" ",e);
		}
		return true;
	}
	
	public List<Product> viewAllProducts() throws ProductApplicationException{
		List<Product> products=new ArrayList<Product>();
		try {
			//code to get all products and store it in 'products'.
		}
		catch(Exception e) {
			throw new ProductApplicationException(" ",e);
		}
		return products;
	}
	
	public List<Product> viewProductsByCategory(String category) throws ProductApplicationException{
		List<Product> products=new ArrayList<Product>();
		try {
			//code to get the category-specific products and store it in 'products'.
		}
		catch(Exception e) {
			throw new ProductApplicationException(" ",e);
		}
		return products;
	}

}
