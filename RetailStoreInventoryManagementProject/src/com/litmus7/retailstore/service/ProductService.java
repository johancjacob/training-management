package com.litmus7.retailstore.service;

import java.util.List;

import com.litmus7.retailstore.dao.ProductDao;
import com.litmus7.retailstore.dto.Clothing;
import com.litmus7.retailstore.dto.Electronics;
import com.litmus7.retailstore.dto.Grocery;
import com.litmus7.retailstore.dto.Product;
import com.litmus7.retailstore.exception.ProductApplicationException;
import com.litmus7.retailstore.util.DataValidator;
import com.litmus7.retailstore.util.ProductSorter;

public class ProductService {
	ProductDao dao=new ProductDao();
	
	public boolean addProduct(Product product) throws ProductApplicationException {
		//category-specific checks
		if(product instanceof Electronics) {
			//specific checks for electronic products.
			return dao.addProduct(product);
		}
		else if(product instanceof Clothing) {
			//specific checks for Clothing products.
			return dao.addProduct(product);
		}
		else if(product instanceof Grocery) {
			//specific checks for Grocery products.
			return dao.addProduct(product);
		}
		else {
			throw new ProductApplicationException(" ");
		}
	}
	
	public List<Product> viewAllProducts() throws ProductApplicationException{
		return dao.viewAllProducts();
	}
	
	public List<Product> viewProductsByCategory(String category) throws ProductApplicationException{
		if(DataValidator.isValidCategory(category)) {
			return dao.viewProductsByCategory(category);
		}
		else {
			throw new ProductApplicationException(" ");
		}
		
	}
	
	public <T> List<Product> sortProducts(String condition) throws ProductApplicationException{
		List<Product> products=dao.viewAllProducts();
		switch(condition.toLowerCase()) {
			case "price ascending":
				products.sort(ProductSorter.sortProductsByPriceAscending());
				break;
			case "price descending":
				products.sort(ProductSorter.sortProductsByPriceDescending());
				break;
			case "name":
				products.sort(ProductSorter.sortProductsByName());
				break;
			default:
				throw new ProductApplicationException(" ");
		}	
		return products;
	}

}
