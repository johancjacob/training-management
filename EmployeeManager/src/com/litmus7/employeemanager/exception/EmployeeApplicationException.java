package com.litmus7.employeemanager.exception;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmployeeApplicationException extends Exception{
	public int errorcode;
	
	public EmployeeApplicationException(int errorcode) {
		this.errorcode=errorcode;
	}
	public EmployeeApplicationException(int errorcode,Throwable cause){
		super(cause);
		this.errorcode=errorcode;
	}
	public EmployeeApplicationException(int errorcode,String message,Throwable cause){
		super(message,cause);
		this.errorcode=errorcode;
	}
	public EmployeeApplicationException(String message){
		super(message);
	}
	public EmployeeApplicationException(String message,Throwable cause){
		super(message,cause);
	}	
}
