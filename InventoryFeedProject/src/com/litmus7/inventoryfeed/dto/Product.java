package com.litmus7.inventoryfeed.dto;

public class Product {
	private int sku;
	private String productName;
	private int quantity;
	private double price;
	
	public Product() {
		
	}
	
	public Product(int sku,String productName,int quantity,double price) {
		this.sku=sku;
		this.productName=productName;
		this.quantity=quantity;
		this.price=price;
	}
	
	public int getSku() {
		return sku;
	}
	public String getProductName() {
		return productName;
	}
	public int getQuantity() {
		return quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setSku(int sku) {
		this.sku=sku;
	}
	public void setProductName(String productName) {
		this.productName=productName;
	}
	public void setQuantity(int quantity) {
		this.quantity=quantity;
	}
	public void setPrice(double price) {
		this.price=price;
	}
}
