package com.litmus7.employeemanager.employeemodel;

public class Response<T> {
	
	public int statuscode;
	public String message;
	public T data;
	public int count;
	public boolean hascount=false;
	
	public Response(int suc,String mes,T data) {
		this.statuscode=suc;
		this.message=mes;
		this.data=data;
	}
	public Response(int suc,String mes,T data,int cnt) {
		this.statuscode=suc;
		this.message=mes;
		this.data=data;
		this.count=cnt;
		this.hascount=true;
	}
	public Response(int suc,String mes) {
		this.statuscode=suc;
		this.message=mes;
	}
	public Response(int suc) {
		this.statuscode=suc;
	}
}