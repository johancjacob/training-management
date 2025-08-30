package com.litmus7.inventoryfeed.exception;

public class ProductApplicationException extends Exception{
	public int errorcode;
	
	public ProductApplicationException(int errorcode) {
		this.errorcode=errorcode;
	}
	public ProductApplicationException(int errorcode,Throwable cause){
		super(cause);
		this.errorcode=errorcode;
	}
	public ProductApplicationException(int errorcode,String message,Throwable cause){
		super(message,cause);
		this.errorcode=errorcode;
	}
	public ProductApplicationException(String message){
		super(message);
	}
	public ProductApplicationException(String message,Throwable cause){
		super(message,cause);
	}	
}
