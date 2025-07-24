package com.litmus7.databaseaccess;

public class DbStatements {
	
	public static final String insertsql="insert into employee(emp_id,first_name,last_name,email,phone,department,salary,join_date) values(?,?,?,?,?,?,?,?)";
	public static final String deletesql="delete from employee where emp_id=?";
	public static final String updateemployeesalarysql="update employee set salary=? where emp_id=?";
	
}
