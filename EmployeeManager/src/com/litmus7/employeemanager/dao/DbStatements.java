package com.litmus7.employeemanager.dao;

public class DbStatements {
	
	public static final String insertsql="insert into employee(emp_id,first_name,last_name,email,phone,department,salary,join_date) values(?,?,?,?,?,?,?,?)";
	public static final String deletesql="delete from employee where emp_id=?";
	public static final String updateemployeesalarysql="update employee set salary=? where emp_id=?";
	public static final String updateemployeephonesql="update employee set phone=? where emp_id=?";
	public static final String getemployeesbydeptsql="select emp_id from employee where department=?";
	public static final String showemployeessql="select * from employee where emp_id=?";
	public static final String showallemployeessql="select * from employee";
	public static final String checkifexists="select count(*) from employee where emp_id=?";
}
