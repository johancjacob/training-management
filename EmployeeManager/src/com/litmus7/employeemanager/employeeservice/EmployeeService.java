package com.litmus7.employeemanager.employeeservice;

import com.litmus7.employeemanager.dao.DatabaseAccessObject;
import com.litmus7.employeemanager.employeemodel.Employee;
import com.litmus7.employeemanager.util.DataValidator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;




public class EmployeeService {
	
	    public boolean isValidEmployee(Employee emp){
	    	
	    	if(DataValidator.isValidEmpId(emp.empId) && DataValidator.isValidEmail(emp.email) && DataValidator.isValidDepartment(emp.dept) && DataValidator.isValidDate(emp.joinDate)){
	    		return true;
	    	}
	    	return false;
	    }
	    public boolean areValidEmployees(List<Employee> emps) {
	    	
	    	Iterator<Employee> iterator=emps.iterator();	    	
	    	while(iterator.hasNext()){
	    		Employee emp=iterator.next();
	    		if(DataValidator.isValidEmpId(emp.empId) && DataValidator.isValidEmail(emp.email) && DataValidator.isValidDepartment(emp.dept) && DataValidator.isValidDate(emp.joinDate)) {
		    		continue;
		    	}
		    	else
		    		iterator.remove();
		    }
	    	return true;
	    }
	    
	   public Employee saveEmployee(Employee emp) throws SQLException, FileNotFoundException, IOException, ClassNotFoundException {
		   
		   DatabaseAccessObject dao=new DatabaseAccessObject();
	       if(isValidEmployee(emp) && !dao.checkIfExists(emp.empId)) {
	    		dao.saveEmployee(emp);
	    		return emp;
	    	}
	       else
	    	return null;
	    }
	    		
	   public List<Employee> saveEmployeesFromCSV(List<Employee> employees) throws SQLException, FileNotFoundException, IOException, ClassNotFoundException {
	    	
		    DatabaseAccessObject dao=new DatabaseAccessObject();
	    	areValidEmployees(employees);
	    	Iterator<Employee> iterator=employees.iterator();
	    	while(iterator.hasNext()){
	    		Employee emp=iterator.next();
	    		if(!dao.checkIfExists(emp.empId)) {
	    			dao.saveEmployee(emp);
	    		}
	    		else {
	    			iterator.remove();
	    		}
	    	}
	    	return employees;
	    }
	    
	  public boolean deleteEmployee(int empId) throws SQLException, FileNotFoundException, IOException, ClassNotFoundException {
	    	
	    	DatabaseAccessObject dao=new DatabaseAccessObject();
	    	if(dao.checkIfExists(empId)) {
	    		return dao.deleteEmployee(empId);
	    	}
	    	else
	    		return false;
	    }
	    
	  public boolean updateEmployeeSalary(int empId,int salary) throws SQLException, FileNotFoundException, IOException, ClassNotFoundException{
	    	
	    	DatabaseAccessObject dao=new DatabaseAccessObject();
	    	if(dao.checkIfExists(empId)) {
	    		return dao.updateEmployeeSalary(empId, salary);
	    	}
	    	else
	    		return false;
	    }
	    
	  public List<Employee> getEmployees(List<Integer> allemployeeids) throws SQLException, FileNotFoundException, IOException, ClassNotFoundException {
	    	
	    	DatabaseAccessObject dao=new DatabaseAccessObject();
	    	List<Integer> validemployeeids=new ArrayList<Integer>();
			for(Integer empId:allemployeeids) {
				if(dao.checkIfExists(empId)) {
					validemployeeids.add(empId);
				}
			}
			return dao.getEmployees(validemployeeids);
	  }
}

	

	
	