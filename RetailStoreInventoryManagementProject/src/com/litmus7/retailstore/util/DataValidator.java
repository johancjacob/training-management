package com.litmus7.retailstore.util;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DataValidator {
	public static List<String> VALID_STATUS_LIST=Arrays.asList("Available","Sold Out","Limited Quantity");
	public static List<String> VALID_BRAND_LIST=Arrays.asList("Nike","Puma","Adidas","USPolo");
	public static List<String> VALID_SIZE_LIST=Arrays.asList("S","M","L","XL","XXL","XXXL");
	public  static List<String> VALID_MATERIAL_LIST=Arrays.asList("Linen","Cotton","Polyester");
	public static List<String> VALID_CATEGORY_LIST=Arrays.asList("Electronics","Clothing","Grocery");
	public static List<String> VALID_SORTING_CONDITION_LIST=Arrays.asList("Price Ascending","Price Descending","Name");
	
	public static boolean isValidProductId(String productId) {
		
		//code to check if the Id is valid.
		
		return true;
	}
	
	public static boolean isValidStatus(String productStatus) {
		return VALID_STATUS_LIST.contains(productStatus);
	}
	
	public static boolean isValidBrand(String brand) {
		return VALID_BRAND_LIST.contains(brand);
	}
	
	public static boolean isValidSize(String size) {
		return VALID_SIZE_LIST.contains(size);
	}
	
	public static boolean isValidMaterial(String material) {
		return VALID_MATERIAL_LIST.contains(material);
	}
	
	public static boolean isValidCategory(String category) {
		return VALID_CATEGORY_LIST.contains(category);
	}
	
	public static boolean idValidSortingCondition(String condition) {
		return  VALID_SORTING_CONDITION_LIST.contains(condition);
	}
	
	public static boolean isValidexpiryDate(Date expiryDate) {
		
		//code to check if its a valid Date.
		
		return true;
	}
}
