package com.litmus7.employeemodel;

public class Response {
	
	public boolean success;
	public String message;
	public int count;
	public boolean hascount=false;
	
	@Override
	public String toString() {
		if(!hascount)
			return message;
		else
			return message+""+count;
	}
	
	public Response(boolean suc,String mes) {
		this.success=suc;
		this.message=mes;
		
	}
	
	public Response(boolean suc,String mes,int cnt) {
		this.success=suc;
		this.message=mes;
		this.count=cnt;
		this.hascount=true;
	}

	
	
	
	

}