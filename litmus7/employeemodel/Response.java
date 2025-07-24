package com.litmus7.employeemodel;

public class Response {
	
	public boolean success;
	public String message;
	public int count;
	
	@Override
	public String toString() {
		return message;
	}
	
	public Response(boolean suc,String mes) {
		this.success=suc;
		this.message=mes;
	}
	
	public Response(boolean suc,String mes,int cnt) {
		this.success=suc;
		this.message=mes;
		this.count=cnt;
	}

	
	
	
	

}
