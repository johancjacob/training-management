package com.litmus7.employeemanagerui;

import com.litmus7.employeeservice.EmployeeManagerService;
import com.litmus7.utils.CSVFileReader;
import com.litmus7.employeemodel.Employee;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EmployeeManagerUI {
	
	
	public static void main(String[] args) throws SQLException, FileNotFoundException, IOException{
		
		List<Employee> emps;
		String file="src/employee.csv";
		
		emps=CSVFileReader.readCSV(file);    //reads the csv file and stores the records as a list of employees
				
		EmployeeManagerService ems=new EmployeeManagerService();
		
		
		//System.out.println(ems.insertEmployee(emp));   //to insert just one employee
		System.out.println(ems.insertEmployees(emps));   //to insert a list of employees from a csv file
		
		Employee emp=new Employee(113,"Varun","Kumar","vk@gmail.com","8798567990","Sales",43000,"2023-09-07");
		System.out.println(ems.insertEmployee(emp));
		
		
		//System.out.println(ems.deleteEmployee(101));
		//System.out.println(ems.deleteEmployee(102));
		
		System.out.println(ems.updateEmployeeSalary(100,1200000));
		
		
		
}
}
