package com.litmus7.retailstore.dto;

//Clothing DTO
public class Clothing extends Product{
	
	public String size;
	public String material;
	
	public Clothing(String id,String name,String category,double price,String status,String size,String material) {
		this.productId=id;
		this.productName=name;
		this.category=category;
		this.price=price;
		this.status=status;
		this.size=size;
		this.material=material;
	}
}
