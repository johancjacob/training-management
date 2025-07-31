package com.litmus7.employeemanagerui;

import com.litmus7.employeeservice.EmployeeManagerService;
import com.litmus7.utils.CSVFileReader;
import com.litmus7.databaseaccess.DatabaseAccessObject;
import com.litmus7.employeemodel.Employee;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class EmployeeManagerUI {
	
	
	public static void main(String[] args) throws SQLException, FileNotFoundException, IOException, ClassNotFoundException{
		
		List<Employee> emps;
		String file="src/employee.csv";
		
		emps=CSVFileReader.readCSV(file);    							//reads the csv file and stores the records as a list of employees
				
		EmployeeManagerService ems=new EmployeeManagerService();
		
		System.out.println(ems.insertEmployees(emps));   				//to insert a list of employees from a csv file

		Employee emp=new Employee(113,"Varun","Kumar","vk@gmail.com","8798567990","Sales",43000,"2023-09-07");
		System.out.println(ems.insertEmployee(emp));     				//to insert just one employee
		
		//System.out.println(ems.deleteEmployee(103));					//deletion
		
		System.out.println(ems.updateEmployeeSalary(100,14000));        //to update employee salary
		System.out.println(ems.updateEmployeePhone(112,"9191780991"));  //to update employee phone
		
		ems.showEmployees(Arrays.asList(100,102,103));					//lists the employee record(s) for the given empIds
		System.out.println(ems.getEmployeesByDept("Sales"));	//lists the employee record(s) in a given dept.
	
		System.out.println(ems.countOfEmployeesInDept("HR"));				//calls the db procedure for getting the count of employees in a given dept.
		
		System.out.println(ems.getEmployeesWithinSalaryRange(1,10));	//calls the db procedure for getting employees whose salaries fall within a range.
		
		//System.out.println(ems.getAllColumnsOfEmployee());
	
	}
}