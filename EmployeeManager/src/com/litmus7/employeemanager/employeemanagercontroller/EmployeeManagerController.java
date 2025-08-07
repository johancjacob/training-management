package com.litmus7.employeemanager.employeemanagercontroller;

import java.util.ArrayList;
import java.util.List;

import com.litmus7.employeemanager.constants.StatusCode;
import com.litmus7.employeemanager.employeemodel.Employee;
import com.litmus7.employeemanager.employeemodel.Response;
import com.litmus7.employeemanager.employeeservice.EmployeeService;
import com.litmus7.employeemanager.exception.EmployeeApplicationException;
import com.litmus7.employeemanager.util.CSVFileReader;
import com.litmus7.employeemanager.util.DataValidator;

public class EmployeeManagerController {
	EmployeeService ems=new EmployeeService();
	
	public Response<Employee> saveEmployee(Employee emp){
		try {
			if(emp.firstName==null || emp.lastName==null || emp.email==null ||emp.joinDate==null)
				return new Response(StatusCode.FAILURE,"missing values for "+emp.empId);
			int id=emp.empId;
			emp=ems.saveEmployee(emp);
			
			if(emp!=null)
				return new Response<Employee>(StatusCode.SUCCESS,MessageConstants.SUCCESSFULLY_SAVED_MESSAGE+id,emp);
			else
				return new Response<Employee>(StatusCode.FAILURE,MessageConstants.INVALID_DUPLICATE_ENTRY_MESSAGE+id,emp);
		}
		catch(EmployeeApplicationException e) {
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
		catch(Exception e) {
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
	
	public Response<List<Employee>> saveEmployeesFromCSV(String file){
		try {
			if(file.endsWith(".csv")) {
				List<Employee> totalemployees=CSVFileReader.readCSV(file);
				int countoftotalemployees=totalemployees.size();
				List<Employee> employees=ems.saveEmployeesFromCSV(totalemployees);
				if(employees.size()==countoftotalemployees)
					return new Response<List<Employee>>(StatusCode.SUCCESS,"all "+employees.size()+MessageConstants.SAVED_FROM_CSV_MESSAGE,employees,employees.size());
				else if(employees.size()==0)
					return new Response(StatusCode.FAILURE,MessageConstants.SAVED_NOTHING_FROM_CSV_MESSAGE);
				else
					return new Response<List<Employee>>(StatusCode.PARTIAL_SUCCESS,employees.size()+MessageConstants.SAVED_FROM_CSV_MESSAGE+" out of the "+countoftotalemployees+MessageConstants.TOTAL_RECORDS_CSV_MESSAGE,employees,employees.size());
			}
			else
				return new Response<List<Employee>>(StatusCode.FAILURE,MessageConstants.NOT_CSV_MESSAGE);
			}
		catch(EmployeeApplicationException e) {
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
		catch(Exception e) {
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
	
	public Response<List<Employee>> getEmployeesById(List<Integer> empIds) {
		try {
			List<Integer> allemployeeids=new ArrayList<>(empIds);
			int countoftotalemployeeids=allemployeeids.size();
			List<Employee> employees=ems.getEmployeesById(allemployeeids);
			if(employees.size()==countoftotalemployeeids)
				return new Response<List<Employee>>(StatusCode.SUCCESS,MessageConstants.FETCHED_DETAILS_OF_ALL_RECORDS_MESSAGE,employees);
			else if(employees.size()==0) {
				return new Response<List<Employee>>(StatusCode.FAILURE,MessageConstants.FETCHED_DETAILS_OF_NONE_MESSAGE);
			}
			else {
			    return new Response<List<Employee>>(StatusCode.PARTIAL_SUCCESS,"empIds for "+(countoftotalemployeeids-employees.size())+" employee(s) not found ",employees);
			}
		}
		catch(EmployeeApplicationException e) {
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
		catch(Exception e) {
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
	
	public Response updateEmployee(Employee emp){
		try {
			if(DataValidator.isValidEmpId(emp.empId)) {
				ems.updateEmployee(emp);
				return new Response(StatusCode.SUCCESS,MessageConstants.SUCCESSFULLY_UPDATED_EMPLOYEE_MESSAGE+emp.empId);
			}
			else
				return new Response(StatusCode.FAILURE,"invalid empId");
		}
		catch(EmployeeApplicationException e) {
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
		catch(Exception e) {
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
	
	public Response deleteEmployeeById(int empId){
		try {
			if(DataValidator.isValidEmpId(empId)) {
				if(ems.deleteEmployeeById(empId))
					return new Response(StatusCode.SUCCESS,MessageConstants.SUCCESSFULLY_DELETED_MESSAGE+empId);
				else
					return new Response(StatusCode.FAILURE,"empId "+empId+" doesnt exist in db");
			}
			else
				return new Response(StatusCode.FAILURE,MessageConstants.INVALID_EMPID_MESSAGE);
		}
		catch(EmployeeApplicationException e) {
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
		catch(Exception e) {
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
	
	public Response updateEmployeeSalaryById(int empId,int salary){
		try {
			if(DataValidator.isValidEmpId(empId)) {
				if(ems.updateEmployeeSalaryById(empId, salary))
					return new Response(StatusCode.SUCCESS,MessageConstants.SUCCESSFULLY_UPDATED_SALARY_MESSAGE+empId);
				else
					return new Response(StatusCode.FAILURE,"record for "+empId+" doesnt exist");
			}
			else
				return new Response(StatusCode.FAILURE,MessageConstants.INVALID_EMPID_MESSAGE);
		}
		catch(EmployeeApplicationException e) {
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
		catch(Exception e) {
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
	
	public Response<List<Employee>> getEmployeesByDept(String dept){
		try {
			if(DataValidator.isValidDepartment(dept)) {
				List<Employee> employees=ems.getEmployeesByDept(dept);
				return new Response<List<Employee>>(StatusCode.SUCCESS,"Employees in "+dept+" fetched: ",employees);
			}
			else {
				return new Response(StatusCode.FAILURE,dept+" is not a valid department");
			}
		}
		catch(EmployeeApplicationException e) {
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
		catch(Exception e) {
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
	
	public Response<List<Employee>> addEmployeesInBatch(List<Employee> employees){
		int countoftotalemployees=employees.size();
		try {
			int countofinsertedemployees=ems.addEmployeesInBatch(employees);
			if(countoftotalemployees==countofinsertedemployees)
				return new Response(StatusCode.SUCCESS,"All "+countofinsertedemployees+" employees in the batch saved",countofinsertedemployees);
			else
				return new Response(StatusCode.PARTIAL_SUCCESS,"Saved "+countofinsertedemployees+" employee(s) out of the "+countoftotalemployees+" in the batch",countofinsertedemployees);
			}
		catch(EmployeeApplicationException e) {
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
		catch(Exception e) {
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
	
	public Response<List<Employee>> transferEmployeesToDepartment(List<Integer> employeeIds,String dept){
		try {
			if(DataValidator.isValidDepartment(dept)) {
				ems.transferEmployeesToDepartment(employeeIds,dept);
				return new Response(StatusCode.SUCCESS,"Successfully transferred to "+dept);
			}
			else
				return new Response(StatusCode.FAILURE,"Invalid department");
		}
		catch(EmployeeApplicationException e) {
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
}

