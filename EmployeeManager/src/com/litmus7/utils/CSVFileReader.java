package com.litmus7.utils;

import com.litmus7.employeemodel.Employee;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVFileReader {
	static FileReader fr;
	static BufferedReader br;
	
	
	
	public static List<Employee> readCSV(String file) throws FileNotFoundException {
	fr=new FileReader(file);
	br=new BufferedReader(fr);
	
	List<Employee> emps=new ArrayList<>();
	
	
	try{
    	
	
		
		
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
		System.out.println(e.getMessage());
		}
	
	return null;
	
	}	
	
	
}
