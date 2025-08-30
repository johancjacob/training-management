package com.litmus7.retailstore.dto;

import java.util.Date;

public class Grocery extends Product{
	
	public Date expiryDate;
	public double weightKg;
	
	public Grocery(String id,String name,String category,double price,String status,Date expiryDate,double weightKg) {
		this.productId=id;
		this.productName=name;
		this.category=category;
		this.price=price;
		this.status=status;
		this.expiryDate=expiryDate;
		this.weightKg=weightKg;
	}
}
