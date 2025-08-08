package com.litmus7.employeemanager.employeeservice;

import com.litmus7.employeemanager.constants.LogMessages;
import com.litmus7.employeemanager.dao.EmployeeDao;
import com.litmus7.employeemanager.employeemanagercontroller.EmployeeManagerController;
import com.litmus7.employeemanager.employeemodel.Employee;
import com.litmus7.employeemanager.exception.EmployeeApplicationException;
import com.litmus7.employeemanager.util.DataValidator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EmployeeService {
		EmployeeDao dao=new EmployeeDao();
		private static final Logger logger=LogManager.getLogger(EmployeeService.class);
	
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
	    
	   public Employee saveEmployee(Employee emp) throws EmployeeApplicationException{
		   logger.debug(LogMessages.ENTER_METHOD_WITH_EMPID_LOG_MESSAGE,"saveEmployee()",emp.empId);
	       if(isValidEmployee(emp) && !dao.checkIfExists(emp.empId)) {
	    	    logger.info(LogMessages.VALID_AND_NON_EXISTENT_EMPLOYEE_LOG_MESSAGE,emp.empId);
	    		dao.saveEmployee(emp);
	    		logger.debug(LogMessages.EXITING_AFTER_SUCCESS_SAVE_IN_DB,emp.empId);
	    		return emp;
	    	}
	       else {
	    	   if(!isValidEmployee(emp))
	    		   logger.warn(LogMessages.VALIDATION_FAILED_LOG_MESSAGE,emp.empId);
	    	   else
	    		   logger.warn(LogMessages.ALREADY_EXISTS_IN_DB_LOG_MESSAGE,emp.empId);
	    	   logger.warn(LogMessages.EXITING_AFTER_FAILURE_SAVE_IN_DB,emp.empId);
	    	   return null;
	       }
	    }
	    		
	   public List<Employee> saveEmployeesFromCSV(List<Employee> employees) throws EmployeeApplicationException {
		    logger.debug(LogMessages.ENTER_METHOD_WITH_EMPLOYEES_LOG_MESSAGE,"saveEmployeesFromCSV()",employees.size());
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
	    	logger.info(LogMessages.VALID_AND_NON_EXISTENT_EMPLOYEES_LOG_MESSAGE,employees.size());
	    	return employees;
	    }
	   
	   public List<Employee> getEmployeesById(List<Integer> allemployeeids) throws EmployeeApplicationException {
		    logger.debug(LogMessages.ENTER_METHOD_WITH_EMPIDS_LOG_MESSAGE,"getEmployeesById",allemployeeids.size());
	    	List<Integer> validemployeeids=new ArrayList<Integer>();
			for(Integer empId:allemployeeids) {
				if(dao.checkIfExists(empId)) {
					validemployeeids.add(empId);
				}
			}
			List<Employee> employees=dao.getEmployeesById(validemployeeids);
			logger.info(LogMessages.FETCHED_DETAILS_OF_EXISTING_IDS_FROM_DB_LOG_MESSAGE,validemployeeids.size());
			return employees;
	  }
	   
	  public boolean updateEmployee(Employee emp) throws EmployeeApplicationException{
		  	logger.debug(LogMessages.ENTER_METHOD_LOG_MESSAGE,"updateEmployee()");
		    if(isValidEmployee(emp) && dao.checkIfExists(emp.empId)) {
		    	dao.updateEmployee(emp);
		    	logger.debug(LogMessages.EXITING_AFTER_SUCCESS_UPDATE_IN_DB,emp.empId);
		    	return true;
		    }
		    else if(!isValidEmployee(emp)) {
		    	logger.warn(LogMessages.VALIDATION_FAILED_LOG_MESSAGE,emp.empId);
		    	logger.warn(LogMessages.EXITING_AFTER_FAILURE_UPDATE_IN_DB,emp.empId);
		    	throw new EmployeeApplicationException("Invalid employee data");
		    }
		    else {
		    	logger.warn(LogMessages.NON_EXISTING_IN_DB_LOG_MESSAGE,emp.empId);
		    	logger.warn(LogMessages.EXITING_AFTER_FAILURE_UPDATE_IN_DB,emp.empId);
		    	throw new EmployeeApplicationException("empId "+emp.empId+" does'nt exist in db");
		    }
	  }
	    
	  public boolean deleteEmployeeById(int empId) throws EmployeeApplicationException {
		  	logger.debug(LogMessages.ENTER_METHOD_WITH_EMPID_LOG_MESSAGE,"deleteEmployeeById()",empId);
	    	if(dao.checkIfExists(empId)) {
	    		dao.deleteEmployeeById(empId);
	    		logger.debug(LogMessages.EXITING_AFTER_SUCCESS_DELETE_IN_DB,empId);
	    		return true;
	    	}
	    	else {
	    		logger.warn(LogMessages.NON_EXISTING_IN_DB_LOG_MESSAGE,empId);
	    		return false;
	    	}
	    }
	    
	  public boolean updateEmployeeSalaryById(int empId,int salary) throws EmployeeApplicationException{
		  	logger.debug(LogMessages.ENTER_METHOD_WITH_EMPID_LOG_MESSAGE,"updateEmployeeSalary()",empId);
	    	if(dao.checkIfExists(empId)) {
	    		dao.updateEmployeeSalaryById(empId, salary);
	    		logger.info(LogMessages.EXITING_AFTER_SUCCESS_UPDATE_IN_DB,empId);
	    		return true;
	    	}
	    	else {
	    		logger.warn(LogMessages.EXITING_AFTER_FAILURE_UPDATE_IN_DB,empId);
	    		return false;
	    	}
	    }
	  
	  public List<Employee> getEmployeesByDept(String dept) throws EmployeeApplicationException{
		  logger.trace(LogMessages.ENTER_METHOD_WITH_DEPT_LOG_MESSAGE,"getEmployeesByDept",dept);
		  List<Employee> employees=dao.getEmployeesByDept(dept);
		  logger.info(LogMessages.EXIT_METHOD_LOG_MESSAGE,"getEmployeesByDept()");
		  return employees;
	  }
	  
	  public int addEmployeesInBatch(List<Employee> employees) throws EmployeeApplicationException{
		  logger.debug(LogMessages.ENTER_METHOD_WITH_EMPIDS_LOG_MESSAGE,"addEmployeesInBatch()",employees.size());
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
			  logger.info(LogMessages.EXITING_AFTER_SUCCESS_SAVES_IN_DB,successcount);
			  return successcount;
		  }
		  else {
			  logger.warn(LogMessages.EXITING_AFTER_FAILURE_SAVES_IN_DB);
			  throw new EmployeeApplicationException("Every employee in the batch is either invalid or their empId already exists in the db.");
		  }
	  }
	  
	  public boolean transferEmployeesToDepartment(List<Integer> employeeIds,String dept) throws EmployeeApplicationException{
		  logger.debug(LogMessages.ENTER_METHOD_WITH_EMPIDS_LOG_MESSAGE,"transferEmployeesToDepartment",employeeIds.size());
		  dao.transferEmployeesToDepartment(employeeIds, dept);
		  logger.info(LogMessages.EXITING_AFTER_SUCCESS_UPDATES_IN_DB,employeeIds.size());
		  return true;
	  }
}

	

	
	