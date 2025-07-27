package com.litmus7.employeeservice;

import com.litmus7.utils.CSVFileReader;
import com.litmus7.utils.DataValidator;
import com.litmus7.employeemodel.Response;
import com.litmus7.utils.DbConnection;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.litmus7.databaseaccess.DatabaseAccessObject;
import com.litmus7.employeemodel.Employee;




public class EmployeeManagerService {
	
	    
	    public Response isValidEmployee(Employee emp){
	    	
	    	
	    	
	    	if(DataValidator.isValidEmpId(emp.empId) && DataValidator.isValidEmail(emp.email) && DataValidator.isValidDepartment(emp.dept) && DataValidator.isValidDate(emp.joinDate)){
	    		
	    		return new Response(true,"valid employee");
	    		
	    	}
	    
	    	
	    	return new Response(false,"invalid employee");
	    	
	    }
	    
	    public Response areValidEmployees(List<Employee> emps) {
	    	
	    	int numberofinvalidemployees=0;
	    	
	    	Iterator<Employee> iterator=emps.iterator();	    	
	    	while(iterator.hasNext()){
	    		Employee emp=iterator.next();
	    		if(DataValidator.isValidEmpId(emp.empId) && DataValidator.isValidEmail(emp.email) && DataValidator.isValidDepartment(emp.dept) && DataValidator.isValidDate(emp.joinDate)) {
		    		continue;
		    	}
		    	else {
		    		numberofinvalidemployees++;
		    		iterator.remove();
		    		}
	    		
	    	}
	    	if(numberofinvalidemployees>0) {
	    		return new Response(false,"invalid employees exist",numberofinvalidemployees);
	    		
	    	}
	    	
	    	else {
	    	return new Response(true,"all valid employees");
	    	}
	    }
	    
	    
	    public Response insertEmployee(Employee emp) throws SQLException {
	    
	    	
	    	if(isValidEmployee(emp).success) {
	    		
	    		DatabaseAccessObject dao=new DatabaseAccessObject();
	    		
	    		boolean isinserted=dao.insertEmployee(emp);
	    		if(isinserted) {
	    		
	    		return new Response(true,"successfully inserted the record for "+emp.empId);}
		    	
	    	}
	    	
	    	
	    	return new Response(false,"couldnt insert record for "+emp.empId);
	    	
	    }
	    
	    public Response insertEmployees(List<Employee> emps) throws SQLException {
	    	
	    	Response validationResponse=areValidEmployees(emps);
	    	
	    	int countofinsertedemployees=0;
	    	
	    	DatabaseAccessObject dao=new DatabaseAccessObject();
	    	
	    	for(Employee emp:emps) {
    			boolean isinserted=dao.insertEmployee(emp);
    			if(isinserted) {
    				countofinsertedemployees++;
    			}
	    	}

	    	
	    	if(!validationResponse.success) {
	    		
	    		return new Response(true,"successfully inserted "+countofinsertedemployees+" employees and couldnt add "+validationResponse.count+" invalid employees");
	    		
	    	}
	    	else {
	    	
	    		return new Response(true,"successfully inserted "+countofinsertedemployees+" employees");
	    	}
	    	
	    	
	    }
	    
	    public Response deleteEmployee(int empId) throws SQLException {
	    	
	    	DatabaseAccessObject dao=new DatabaseAccessObject();
	    	
	    	boolean isdeleted=dao.deleteEmployee(empId);
	    	
	    	if(isdeleted) {
	    		return new Response(true,"succesfully deleted record for "+empId);}
	    	
	    	else {
	    		return new Response(false,"record for "+empId+" doesnt exist");}
	    }
	    
	    public Response updateEmployeeSalary(int empId,int salary) throws SQLException{
	    	
	    	DatabaseAccessObject dao=new DatabaseAccessObject();
	    	
	    	boolean isupdated=dao.updateEmployeeSalary(empId,salary);
	    	
	    	if(isupdated) {
	    		return new Response(true,"successfully updated the salary for "+empId);}
	    	
	    	else {
	    		return new Response(false,"record for "+empId+" doesnt exist");
	    	}
	    }
	    
	    public Response updateEmployeePhone(int empId,String phone) throws SQLException{
	    	
	    	DatabaseAccessObject dao=new DatabaseAccessObject();
	    	
	    	boolean isupdated=dao.updateEmployeePhone(empId,phone);
	    	
	    	if(isupdated) {
	    		return new Response(true,"successfully updated the phone for "+empId);}
	    	
	    	else {
	    		return new Response(false,"record for "+empId+" doesnt exist");
	    	}
	    		
	    }
	    
	    public Response showEmployees(List<Integer> empIds) throws SQLException {
	    	
	    	DatabaseAccessObject dao=new DatabaseAccessObject();
	    	
	    	dao.showEmployees(empIds);
	    	
	    	return new Response(true,"List of valid employees listed.");
	    	
	    	
	    }
	    
	    public Response getEmployeesByDept(String dept) throws SQLException{
	    	
	    	
	    	if(DataValidator.VALID_DEPTS.contains(dept)) {
		    	DatabaseAccessObject dao=new DatabaseAccessObject();
		    	
		    	dao.getEmployeesByDept(dept);
		    	
		    	return new Response(true,"List of employees in "+dept+" department listed.");
	    	}
	    	
	    	else {
	    		
	    		return new Response(false,"Invalid department.");
	    	}
	    	
	    }
}

	

	
	