package com.litmus7.inventoryfeed.util;

public class DataValidator {
	public static boolean isValidSku(int sku) {
		return ((sku>=100) && (sku<1000));
	}
	public static boolean isValidProductName(String productName) {
		return((productName.startsWith("Widget")));
	}

}
