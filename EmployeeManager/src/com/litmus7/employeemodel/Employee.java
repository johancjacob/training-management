package com.litmus7.employeemodel;

import java.sql.Date;

public class Employee {
	
	
	public int empId;
	public String firstName;
	public String lastName;
	public String email="";
	public String phone="";
	public String dept="";
	public int salary=0;
	public String joinDate="";
	
	public Employee() {
		
	}
	
	public Employee(int empId,String firstName,String lastName,String email,String phone,String dept,int salary,String joinDate) {
		
		this.empId=empId;
		this.firstName=firstName;
		this.lastName=lastName;
		this.email=email;
		this.phone=phone;
		this.dept=dept;
		this.salary=salary;
		this.joinDate=joinDate;
		
	}
	
	@Override
	public String toString() {
		return empId+" "+firstName+" "+lastName+" "+email+" "+phone+" "+dept+" "+salary+" "+joinDate;
	}

}