package com.litmus7.inventoryfeed.dto;

public class Response<T> {
	
	public int statusCode;
	public String message;
	T data;
	
	public Response(int statusCode,String message) {
		this.statusCode=statusCode;
		this.message=message;
	}
	public Response(int statusCode,String message,T data) {
		this.statusCode=statusCode;
		this.message=message;
		this.data=data;
	}
}