package com.litmus7.retailstore.dto;

public class Response<T> {
	
	public int statuscode;
	public String message;
	public T data;
	
	public Response(int statusCode,String message,T data) {
		this.statuscode=statusCode;
		this.message=message;
		this.data=data;
	}
	public Response(int statusCode,String message) {
		this.statuscode=statusCode;
		this.message=message;
	}
	
	//can have more constructors
	
}