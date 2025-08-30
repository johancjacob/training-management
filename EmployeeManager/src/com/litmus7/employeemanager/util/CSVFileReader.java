package com.litmus7.employeemanager.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.litmus7.employeemanager.employeemodel.Employee;
import com.litmus7.employeemanager.exception.EmployeeApplicationException;

public class CSVFileReader {
	static FileReader fr;
	static BufferedReader br;
	
	
	
	public static List<Employee> readCSV(String file) throws EmployeeApplicationException {
	
	
	List<Employee> emps=new ArrayList<>();
	
	
	try{
		fr=new FileReader(file);
		br=new BufferedReader(fr);
    	
		String line;
		
		//extracting values from csv for each line
		while((line=br.readLine())!=null) {
			line=line.replace("\"", "");
			String[] fields=line.split(",");
			
			Employee emp=new Employee();
			
			emp.empId  = Integer.parseInt(fields[0]);		
			emp.firstName=fields[1];
			emp.lastName=fields[2];
			emp.email=fields[3];
			emp.phone=fields[4];
			emp.dept=fields[5];
			emp.salary=Integer.parseInt(fields[6]);
			emp.joinDate=fields[7];
			
			emps.add(emp);
		}
		
		return emps;
	}
	
	catch(IOException e) {
		throw new EmployeeApplicationException("file not found",e);
		}
	}	
}
