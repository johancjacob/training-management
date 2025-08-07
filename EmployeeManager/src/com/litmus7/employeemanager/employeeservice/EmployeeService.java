package com.litmus7.employeemanager.employeeservice;

import com.litmus7.employeemanager.dao.EmployeeDao;
import com.litmus7.employeemanager.employeemodel.Employee;
import com.litmus7.employeemanager.exception.EmployeeApplicationException;
import com.litmus7.employeemanager.util.DataValidator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class EmployeeService {
		EmployeeDao dao=new EmployeeDao();
	
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
	    
	   public Employee saveEmployee(Employee emp) throws EmployeeApplicationException {
	       if(isValidEmployee(emp) && !dao.checkIfExists(emp.empId)) {
	    		dao.saveEmployee(emp);
	    		return emp;
	    	}
	       else
	    	return null;
	    }
	    		
	   public List<Employee> saveEmployeesFromCSV(List<Employee> employees) throws EmployeeApplicationException {
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
	   
	   public List<Employee> getEmployeesById(List<Integer> allemployeeids) throws EmployeeApplicationException {
	    	List<Integer> validemployeeids=new ArrayList<Integer>();
			for(Integer empId:allemployeeids) {
				if(dao.checkIfExists(empId)) {
					validemployeeids.add(empId);
				}
			}
			return dao.getEmployeesById(validemployeeids);
	  }
	   
	  public boolean updateEmployee(Employee emp) throws EmployeeApplicationException{
		    if(isValidEmployee(emp) && dao.checkIfExists(emp.empId)) {
		    	dao.updateEmployee(emp);
		    	return true;
		    }
		    else if(!isValidEmployee(emp))
		    	throw new EmployeeApplicationException("Invalid employee data");
		    else
		    	throw new EmployeeApplicationException("empId "+emp.empId+" does'nt exist in db");
	  }
	    
	  public boolean deleteEmployeeById(int empId) throws EmployeeApplicationException {
	    	if(dao.checkIfExists(empId)) {
	    		return dao.deleteEmployeeById(empId);
	    	}
	    	else
	    		return false;
	    }
	    
	  public boolean updateEmployeeSalaryById(int empId,int salary) throws EmployeeApplicationException{
	    	if(dao.checkIfExists(empId)) {
	    		return dao.updateEmployeeSalaryById(empId, salary);
	    	}
	    	else
	    		return false;
	    }
	  
	  public List<Employee> getEmployeesByDept(String dept) throws EmployeeApplicationException{
		  return dao.getEmployeesByDept(dept);
	  }
	  
	  public int addEmployeesInBatch(List<Employee> employees) throws EmployeeApplicationException{
		  areValidEmployees(employees);
		  Iterator<Employee> iterator=employees.iterator();
		  while(iterator.hasNext()){
	    		Employee employee=iterator.next();
	    		if(dao.checkIfExists(employee.empId))
	    			iterator.remove();
		  }
		  if(employees.size()>0) {
			  int[] results=dao.addEmployeesInBatch(employees);
			  int successcount=0;
			  for(int result:results) {
				  if(result>=0)
					  successcount++;
			  }
			  return successcount;
		  }
		  else 
			  throw new EmployeeApplicationException("Every employee in the batch is either invalid or their empId already exists in the db.");
	  }
	  
	  public boolean transferEmployeesToDepartment(List<Integer> employeeIds,String dept) throws EmployeeApplicationException{
		  return dao.transferEmployeesToDepartment(employeeIds, dept);
	  }
}

	

	
	