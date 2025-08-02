package com.litmus7.employeemanager.employeemanagerui;

import com.litmus7.employeemanager.employeemanagercontroller.EmployeeManagerController;
import com.litmus7.employeemanager.employeemodel.Employee;
import com.litmus7.employeemanager.employeemodel.Response;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;


public class EmployeeManagerUI {
	
	public static void main(String[] args) throws SQLException, FileNotFoundException, IOException, ClassNotFoundException{
		String file="employee.csv";
		Response<?> res;
		EmployeeManagerController emc=new EmployeeManagerController();
		
		res=emc.saveEmployeesFromCSV(file);   				//inserting a list of employees
		System.out.println(res.message);
		
		Employee emp=new Employee(113,"Varun","Kumar","vk@gmail.com","8798567990","Sales",43000,"2023-09-07"); 
		res=emc.saveEmployee(emp);						//insert just one employee
		System.out.println(res.message);
		
		//res=emc.deleteEmployee(103);						//deleting an employee
		//System.out.println(res.message);
		
		res=emc.updateEmployeeSalary(112,14000);  		//to update employee salary
		System.out.println(res.message);
		
		res=emc.getEmployees(Arrays.asList(1090,1100,123,1233,105,108));	//lists the employee record(s) for the given existing empIds
		System.out.println(res.message+'\n'+res.data);
	}
}