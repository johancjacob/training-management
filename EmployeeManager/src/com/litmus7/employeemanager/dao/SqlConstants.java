package com.litmus7.employeemanager.dao;

public class SqlConstants {
	
	public static final String INSERT_EMPLOYEE_QUERY="insert into employee(emp_id,first_name,last_name,email,phone,department,salary,join_date) values(?,?,?,?,?,?,?,?)";
	public static final String DELETE_EMPLOYEE_BY_ID_QUERY="delete from employee where emp_id=?";
	public static final String UPDATE_EMPLOYEE_SALARY_BY_ID_QUERY="update employee set salary=? where emp_id=?";
	public static final String GET_EMPLOYEE_BY_ID_QUERY="select * from employee where emp_id=?";
	public static final String GET_ALL_EMPLOYEES_QUERY="select * from employee";
	public static final String CHECK_IF_EMPLOYEE_EXISTS_BY_ID_QUERY="select count(*) from employee where emp_id=?";
	public static final String UPDATE_EMPLOYEE_QUERY="update employee set first_name=?,last_name=?,email=?,phone=?,department=?,salary=?,join_date=? where emp_id=?";
	public static final String UPDATE_EMPLOYEE_DEPT_BY_ID_QUERY="update employee set department=? where emp_id=?";
}
