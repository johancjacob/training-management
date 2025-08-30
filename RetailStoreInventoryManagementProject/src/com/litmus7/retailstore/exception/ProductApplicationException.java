package com.litmus7.retailstore.exception;

public class ProductApplicationException extends Exception{
	public ProductApplicationException(String message){
		super(message);
	}
	public ProductApplicationException(String message,Throwable cause){
		super(message,cause);
	}	
}
