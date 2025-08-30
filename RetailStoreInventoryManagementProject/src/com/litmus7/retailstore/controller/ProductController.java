package com.litmus7.retailstore.controller;

import java.util.List;

import com.litmus7.retailstore.dto.Product;
import com.litmus7.retailstore.dto.Response;
import com.litmus7.retailstore.exception.ProductApplicationException;
import com.litmus7.retailstore.service.ProductService;
import com.litmus7.retailstore.util.DataValidator;

public class ProductController {
	ProductService productService=new ProductService();
	
	public Response<?> addProduct(Product product) {
		try {
			if(DataValidator.isValidProductId(product.productId) &&( product.price>0)) {
				productService.addProduct(product);
				return new Response<>(200,"");
			}
			else {
				//pass the message using Response.
				return new Response<>(400,"");
			}
		}
		catch(ProductApplicationException e) {
			//catch the exception that was propagated and wrap it inside Response.
			return new Response<Exception>(400,"",e);
		}
	}
	
	public Response<?> viewAllProducts() {
		try {
			List<Product> products=productService.viewAllProducts();
			//check if products is empty or not and wrap the response accordingly.
			return new Response<List<Product>>(200,"",products);
		}
		catch(ProductApplicationException e) {
			//catch the exception that was propagated and wrap it inside Response.
			return new Response<Exception>(400,"",e);
		}
	}
	
	public Response<?> viewProductsByCategory(String category){
		try {
			if(category!=null) {
				List<Product> products=productService.viewProductsByCategory(category);
				return new Response<List<Product>>(200,"",products);
			}
			else {
				//category passed was null
				return new Response<>(200,"");
			}
				
		}
		catch(ProductApplicationException e) {
			//catch the exception that was propagated and wrap it inside Response.
			return new Response<Exception>(200,"",e);
		}
	}
	
	public Response<?> sortProducts(String condition){
		try {
			if(DataValidator.idValidSortingCondition(condition)) {
				List<Product> products=productService.sortProducts(condition);
				return new Response<List<Product>>(200,"",products);
			}
			else {
				//invalid condition
				return new Response<>(200,"");
			}
		}
		catch(ProductApplicationException e) {
			//catch the exception that was propagated and wrap it inside Response.
			return new Response<Exception>(200,"",e);
		}
	}
}
