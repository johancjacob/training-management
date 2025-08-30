package com.litmus7.retailstore.dto;

//Electronics DTO
public class Electronics extends Product {
	
	public String brand;
	public int warrantyMonths;
	
	public Electronics(String id,String name,String category,double price,String status,String brand,int warrantyMonths) {
		this.productId=id;
		this.productName=name;
		this.category=category;
		this.price=price;
		this.status=status;
		this.brand=brand;
		this.warrantyMonths=warrantyMonths;
	}
}
