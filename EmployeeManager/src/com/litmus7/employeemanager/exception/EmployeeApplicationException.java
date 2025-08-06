package com.litmus7.employeemanager.exception;

public class EmployeeApplicationException extends Exception{
	
	public EmployeeApplicationException(String message){
		super(message);
	}
	public EmployeeApplicationException(String message,Throwable cause){
		super(message,cause);
		
	}
}
