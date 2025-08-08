package com.litmus7.employeemanager.employeemanagercontroller;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.litmus7.employeemanager.constants.StatusCode;
import com.litmus7.employeemanager.constants.LogMessages;
import com.litmus7.employeemanager.constants.MessageConstants;
import com.litmus7.employeemanager.employeemodel.Employee;
import com.litmus7.employeemanager.employeemodel.Response;
import com.litmus7.employeemanager.employeeservice.EmployeeService;
import com.litmus7.employeemanager.exception.EmployeeApplicationException;
import com.litmus7.employeemanager.util.CSVFileReader;
import com.litmus7.employeemanager.util.DataValidator;

public class EmployeeManagerController {
	EmployeeService ems=new EmployeeService();
	private static final Logger logger=LogManager.getLogger(EmployeeManagerController.class);
	
	public Response<Employee> saveEmployee(Employee emp){
		logger.trace(LogMessages.ENTER_METHOD_LOG_MESSAGE,"saveEmployee()");
		logger.debug(LogMessages.RECEIVED_EMPLOYEE_DATA_LOG_MESSAGE,emp.empId,emp.firstName,emp.lastName,emp.email,emp.phone,emp.dept,emp.salary,emp.joinDate);
		try {
			if(emp.firstName==null || emp.lastName==null || emp.email==null ||emp.joinDate==null) {
				logger.warn("Missing values for empId={}",emp.empId);
				logger.trace(LogMessages.EXIT_METHOD_LOG_MESSAGE,"saveEmployee()");
				return new Response(StatusCode.FAILURE,"missing values for "+emp.empId);
			}
			int id=emp.empId;
			Employee employee=ems.saveEmployee(emp);
			
			if(employee!=null) {
				logger.info(LogMessages.SUCCESSFULLY_SAVED_LOG_MESSAGE+emp.empId);
				logger.trace(LogMessages.EXIT_METHOD_LOG_MESSAGE,"saveEmployee()");
				return new Response<Employee>(StatusCode.SUCCESS,MessageConstants.SUCCESSFULLY_SAVED_MESSAGE+id,emp);
			}
			else {
				logger.error(LogMessages.SAVE_FAILURE_LOG_MESSAGE+id);
				logger.trace(LogMessages.EXIT_METHOD_LOG_MESSAGE,"saveEmployee()");
				return new Response<Employee>(StatusCode.FAILURE,MessageConstants.INVALID_DUPLICATE_ENTRY_MESSAGE+id,emp);
			}
		}
		catch(EmployeeApplicationException e) {
			logger.error(LogMessages.BUSINESS_EXCEPTION_LOG_MESSAGE);
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
		catch(Exception e) {
			logger.fatal(LogMessages.UNEXPECTED_ERROR_LOG_MESSAGE);
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
	
	public Response<List<Employee>> saveEmployeesFromCSV(String file){
		logger.trace(LogMessages.ENTER_METHOD_LOG_MESSAGE,"saveEmployeesFromCSV()");
		try {
			if(file.endsWith(".csv")) {
				List<Employee> totalemployees=CSVFileReader.readCSV(file);
				int countoftotalemployees=totalemployees.size();
				logger.debug("Received {} records from csv",totalemployees);
				List<Employee> employees=ems.saveEmployeesFromCSV(totalemployees);
				if(employees.size()==countoftotalemployees) {
					logger.info("Succesfully saved all employees from csv");
					logger.trace(LogMessages.EXIT_METHOD_LOG_MESSAGE,"saveEmployeesFromCSV()");
					return new Response<List<Employee>>(StatusCode.SUCCESS,"all "+employees.size()+" employee(s) in csv saved",employees,employees.size());
				}
				else if(employees.size()==0) {
					logger.error(LogMessages.SAVE_FAILURE_LOG_MESSAGE+"all records");
					logger.trace(LogMessages.EXIT_METHOD_LOG_MESSAGE,"saveEmployeesFromCSV()");
					return new Response(StatusCode.FAILURE,MessageConstants.SAVED_NOTHING_FROM_CSV_MESSAGE);
				}
				else {
					logger.info("saved ",employees.size()+" from csv");
					logger.trace(LogMessages.EXIT_METHOD_LOG_MESSAGE,"saveEmployeesFromCSV()");
					return new Response<List<Employee>>(StatusCode.PARTIAL_SUCCESS,employees.size()+" employee(s) in csv saved out of the "+countoftotalemployees+" total records in csv",employees,employees.size());
				}
			}
			else {
				logger.fatal(LogMessages.NOT_CSV_LOG_MESSAGE);
				logger.trace(LogMessages.EXIT_METHOD_LOG_MESSAGE,"saveEmployeesFromCSV()");
				return new Response<List<Employee>>(StatusCode.FAILURE,MessageConstants.NOT_CSV_MESSAGE);
			}
		}
		catch(EmployeeApplicationException e) {
			logger.error(LogMessages.BUSINESS_EXCEPTION_LOG_MESSAGE);
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
		catch(Exception e) {
			logger.fatal(LogMessages.UNEXPECTED_ERROR_LOG_MESSAGE);
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
	
	public Response<List<Employee>> getEmployeesById(List<Integer> empIds) {
		logger.trace(LogMessages.ENTER_METHOD_WITH_EMPIDS_LOG_MESSAGE,"getEmployeesById()",empIds.size());
		try {
			List<Integer> allemployeeids=new ArrayList<>(empIds);
			int countoftotalemployeeids=allemployeeids.size();
			List<Employee> employees=ems.getEmployeesById(allemployeeids);
			logger.debug("Received "+employees.size()+" valid employees that correspond to the given empIds");
			if(employees.size()==countoftotalemployeeids) {
				logger.info("All given empIds were existing");
				logger.trace(LogMessages.EXIT_METHOD_LOG_MESSAGE,"getEmployeesById");
				return new Response<List<Employee>>(StatusCode.SUCCESS,"Fetched details of all records :",employees);
			}
			else if(employees.size()==0) {
				logger.warn("No employees fetched");
				logger.trace(LogMessages.EXIT_METHOD_LOG_MESSAGE,"getEmployeesById()");
				return new Response<List<Employee>>(StatusCode.FAILURE,"Could'nt fetch any of the records");
			}
			else {
				logger.warn("Only some employees could be fetched");
				logger.trace(LogMessages.EXIT_METHOD_LOG_MESSAGE,"getEmployeesById()");
			    return new Response<List<Employee>>(StatusCode.PARTIAL_SUCCESS,"empIds for "+(countoftotalemployeeids-employees.size())+" employee(s) not found ",employees);
			}
		}
		catch(EmployeeApplicationException e) {
			logger.error(LogMessages.BUSINESS_EXCEPTION_LOG_MESSAGE);
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
		catch(Exception e) {
			logger.fatal(LogMessages.UNEXPECTED_ERROR_LOG_MESSAGE);
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
	
	public Response updateEmployee(Employee emp){
		logger.trace(LogMessages.ENTER_METHOD_LOG_MESSAGE,"updateEmployee()");
		logger.debug(LogMessages.RECEIVED_EMPLOYEE_DATA_LOG_MESSAGE,emp.empId,emp.firstName,emp.lastName,emp.email,emp.phone,emp.dept,emp.salary,emp.joinDate);
		try {
			if(DataValidator.isValidEmpId(emp.empId)) {
				ems.updateEmployee(emp);
				logger.info("Successfully updated");
				logger.trace(LogMessages.EXIT_METHOD_LOG_MESSAGE,"updateEmployee()");
				return new Response(StatusCode.SUCCESS,"Successfully updated the employee record for "+emp.empId);
			}
			else
				logger.warn(MessageConstants.INVALID_EMPID_MESSAGE);
				logger.trace(LogMessages.EXIT_METHOD_LOG_MESSAGE,"updateEmployee()");
				return new Response(StatusCode.FAILURE,MessageConstants.INVALID_EMPID_MESSAGE);
		}
		catch(EmployeeApplicationException e) {
			logger.error(LogMessages.BUSINESS_EXCEPTION_LOG_MESSAGE);
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
		catch(Exception e) {
			logger.fatal(LogMessages.UNEXPECTED_ERROR_LOG_MESSAGE);
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
	
	public Response deleteEmployeeById(int empId){
		logger.trace(LogMessages.ENTER_METHOD_WITH_EMPID_LOG_MESSAGE,"deleteEmployeeById()",empId);
		try {
			if(DataValidator.isValidEmpId(empId)) {
				if(ems.deleteEmployeeById(empId)) {
					logger.info("Deletion successful");
					logger.trace(LogMessages.EXIT_METHOD_LOG_MESSAGE,"deleteEmployeeById");
					return new Response(StatusCode.SUCCESS,"Successfully deleted the employee record for: "+empId);
				}
				else {
					logger.warn("Deletion unsuccessful");
				    logger.trace(LogMessages.EXIT_METHOD_LOG_MESSAGE,"deleteEmployeeById");
				    return new Response(StatusCode.FAILURE,"empId "+empId+" doesnt exist in db");
				}
			}
			else {
				logger.warn(MessageConstants.INVALID_EMPID_MESSAGE);
				logger.trace(LogMessages.EXIT_METHOD_LOG_MESSAGE,"deleteEmployeeById");
				return new Response(StatusCode.FAILURE,MessageConstants.INVALID_EMPID_MESSAGE);
			}
		}
		catch(EmployeeApplicationException e) {
			logger.error(LogMessages.BUSINESS_EXCEPTION_LOG_MESSAGE);
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
		catch(Exception e) {
			logger.fatal(LogMessages.UNEXPECTED_ERROR_LOG_MESSAGE);
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
	
	public Response updateEmployeeSalaryById(int empId,int salary){
		logger.debug(LogMessages.ENTER_METHOD_WITH_EMPID_LOG_MESSAGE,"updateEmployeeSalaryById",empId);
		try {
			if(DataValidator.isValidEmpId(empId)) {
				if(ems.updateEmployeeSalaryById(empId, salary)) {
					logger.info("Sucessfully updated the salary");
					logger.trace(LogMessages.EXIT_METHOD_LOG_MESSAGE,"updateEmployeeSalaryById");
					return new Response(StatusCode.SUCCESS,"Successfully updated salary for "+empId);
				}
				else {
					logger.warn("Updation failed");
					logger.trace(LogMessages.EXIT_METHOD_LOG_MESSAGE,"updateEmployeeSalaryById");
					return new Response(StatusCode.FAILURE,"record for "+empId+" doesnt exist");
				}
			}
			else {
				logger.warn(MessageConstants.INVALID_EMPID_MESSAGE);
				logger.trace(LogMessages.EXIT_METHOD_LOG_MESSAGE,"updateEmployeeSalaryById");
				return new Response(StatusCode.FAILURE,MessageConstants.INVALID_EMPID_MESSAGE);
			}
		}
		catch(EmployeeApplicationException e) {
			logger.error(LogMessages.BUSINESS_EXCEPTION_LOG_MESSAGE);
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
		catch(Exception e) {
			logger.fatal(LogMessages.UNEXPECTED_ERROR_LOG_MESSAGE);
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
	
	public Response<List<Employee>> getEmployeesByDept(String dept){
		logger.debug(LogMessages.ENTER_METHOD_WITH_DEPT_LOG_MESSAGE,"getEmployeesByDept",dept);
		try {
			if(DataValidator.isValidDepartment(dept)) {
				List<Employee> employees=ems.getEmployeesByDept(dept);
				logger.info("Fetched successfully");
				logger.trace(LogMessages.EXIT_METHOD_LOG_MESSAGE,"getEmployeesByDept()");
				return new Response<List<Employee>>(StatusCode.SUCCESS,"Employees in "+dept+" fetched: ",employees);
			}
			else {
				logger.warn("Could'nt fetch");
				logger.trace(LogMessages.EXIT_METHOD_LOG_MESSAGE,"getEmployeesByDept()");
				return new Response(StatusCode.FAILURE,dept+" is not a valid department");
			}
		}
		catch(EmployeeApplicationException e) {
			logger.error(LogMessages.BUSINESS_EXCEPTION_LOG_MESSAGE);
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
		catch(Exception e) {
			logger.fatal(LogMessages.UNEXPECTED_ERROR_LOG_MESSAGE);
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
	
	public Response<List<Employee>> addEmployeesInBatch(List<Employee> employees){
		logger.debug(LogMessages.ENTER_METHOD_WITH_EMPLOYEES_LOG_MESSAGE,"addEmployeesInBatch()",employees.size());
		int countoftotalemployees=employees.size();
		try {
			int countofinsertedemployees=ems.addEmployeesInBatch(employees);
			logger.debug("Saved "+countofinsertedemployees+" employees");
			if(countoftotalemployees==countofinsertedemployees) {
				logger.info("All employees saved");
				logger.trace(LogMessages.EXIT_METHOD_LOG_MESSAGE,"addEmployeesInBatch()");
				return new Response(StatusCode.SUCCESS,"All "+countofinsertedemployees+" employees in the batch saved",countofinsertedemployees);
			}
			else {
				logger.warn("Could save only "+countofinsertedemployees+" employees");
				logger.trace(LogMessages.EXIT_METHOD_LOG_MESSAGE,"addEmployeesInBatch()");
				return new Response(StatusCode.PARTIAL_SUCCESS,"Saved "+countofinsertedemployees+" employee(s) out of the "+countoftotalemployees+" in the batch",countofinsertedemployees);
			}
		}
		catch(EmployeeApplicationException e) {
			logger.error(LogMessages.BUSINESS_EXCEPTION_LOG_MESSAGE);
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
		catch(Exception e) {
			logger.fatal(LogMessages.UNEXPECTED_ERROR_LOG_MESSAGE);
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
	
	public Response<List<Employee>> transferEmployeesToDepartment(List<Integer> employeeIds,String dept){
		logger.debug(LogMessages.ENTER_METHOD_WITH_EMPIDS_LOG_MESSAGE,"transferEmployeesToDepartment()",employeeIds.size());
		try {
			if(DataValidator.isValidDepartment(dept)) {
				ems.transferEmployeesToDepartment(employeeIds,dept);
				logger.info("Successfully transferred all to "+dept);
				logger.trace(LogMessages.EXIT_METHOD_LOG_MESSAGE,"transferEmployeesToDepartment");
				return new Response(StatusCode.SUCCESS,"Successfully transferred all the "+employeeIds.size() +" employees to "+dept);
			}
			else {
				logger.info("Transfer to "+dept+" failed");
				logger.trace(LogMessages.EXIT_METHOD_LOG_MESSAGE,"transferEmployeesToDepartment");
				return new Response(StatusCode.FAILURE,"Invalid department");
			}
		}
		catch(EmployeeApplicationException e) {
			logger.error(LogMessages.BUSINESS_EXCEPTION_LOG_MESSAGE);
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
		catch(Exception e) {
			logger.fatal(LogMessages.UNEXPECTED_ERROR_LOG_MESSAGE);
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
}

