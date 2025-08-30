package com.litmus7.retailstore.util;

import java.util.Comparator;

import com.litmus7.retailstore.dto.Product;

public class ProductSorter {
	public static Comparator<Product> sortProductsByPriceAscending(){
		return new Comparator<Product>() {
			
			@Override
			public int compare(Product p1, Product p2) {
				return Double.compare(p1.price,p2.price);
			}		
		};
	}
	
	public static Comparator<Product> sortProductsByPriceDescending(){
		return new Comparator<Product>() {
			
			@Override
			public int compare(Product p1, Product p2) {
				return Double.compare(p2.price,p1.price);
			}		
		};
	}
	
	public static Comparator<Product> sortProductsByName(){
		return new Comparator<Product>() {
			
			@Override
			public int compare(Product p1, Product p2) {
				return p1.productName.compareToIgnoreCase(p2.productName);
			}
		};
	}
}
