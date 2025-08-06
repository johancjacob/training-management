package com.litmus7.employeemanager.employeemanagercontroller;

import java.util.ArrayList;
import java.util.List;

import com.litmus7.employeemanager.constants.StatusCode;
import com.litmus7.employeemanager.employeemodel.Employee;
import com.litmus7.employeemanager.employeemodel.Response;
import com.litmus7.employeemanager.employeeservice.EmployeeService;
import com.litmus7.employeemanager.exception.EmployeeException;
import com.litmus7.employeemanager.util.CSVFileReader;
import com.litmus7.employeemanager.util.DataValidator;

public class EmployeeManagerController {
	
	public Response<Employee> saveEmployee(Employee emp){
		try {
			if(emp.firstName==null || emp.lastName==null || emp.email==null ||emp.joinDate==null)
				return new Response(StatusCode.FAILURE,"missing values for "+emp.empId);
			EmployeeService ems=new EmployeeService();
			int id=emp.empId;
			emp=ems.saveEmployee(emp);
			
			if(emp!=null)
				return new Response<Employee>(StatusCode.SUCCESS,"successfully saved "+id,emp);
			else
				return new Response<Employee>(StatusCode.FAILURE,"invalid values or duplicate entry for "+id,emp);
		}
		catch(EmployeeException e) {
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
	
	public Response<List<Employee>> saveEmployeesFromCSV(String file){
		try {
			if(file.endsWith(".csv")) {
				List<Employee> totalemployees=CSVFileReader.readCSV(file);
				int countoftotalemployees=totalemployees.size();
				EmployeeService ems=new EmployeeService();
				List<Employee> employees=ems.saveEmployeesFromCSV(totalemployees);
				if(employees.size()==countoftotalemployees)
					return new Response<List<Employee>>(StatusCode.SUCCESS,"all "+employees.size()+" employees in csv saved",employees,employees.size());
				else if(employees.size()==0)
					return new Response(StatusCode.FAILURE,"couldnt save any of the employees in csv");
				else
					return new Response<List<Employee>>(StatusCode.PARTIAL_SUCCESS,"saved "+employees.size()+" employee(s)"+" out of the "+countoftotalemployees+" records in the csv",employees,employees.size());
			}
			else
				return new Response<List<Employee>>(StatusCode.FAILURE,"not a csv file");
			}
		catch(EmployeeException e) {
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
	
	public Response<List<Employee>> getEmployeesById(List<Integer> empIds) {
		try {
			List<Integer> allemployeeids=new ArrayList<>(empIds);
			int countoftotalemployeeids=allemployeeids.size();
			EmployeeService ems=new EmployeeService();
			List<Employee> employees=ems.getEmployeesById(allemployeeids);
			if(employees.size()==countoftotalemployeeids)
				return new Response<List<Employee>>(StatusCode.SUCCESS,"fetched details of all records :",employees);
			else if(employees.size()==0) {
				return new Response<List<Employee>>(StatusCode.FAILURE,"couldnt fetch any of the records ");
			}
			else {
			    return new Response<List<Employee>>(StatusCode.PARTIAL_SUCCESS,"empIds for "+(countoftotalemployeeids-employees.size())+" employee(s) not found ",employees);
			}
		}
		catch(EmployeeException e) {
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
	
	public Response updateEmployee(Employee emp){
		try {
			if(DataValidator.isValidEmpId(emp.empId)) {
				EmployeeService ems=new EmployeeService();
				if(ems.updateEmployee(emp)==1)
					return new Response(StatusCode.SUCCESS,"successfully updated the employee record for "+emp.empId);
				else if(ems.updateEmployee(emp)==2)
					return new Response(StatusCode.FAILURE,"invalid employee data");
				else
					return new Response(StatusCode.FAILURE,"empId "+emp.empId+" doesnt exist in db");
			}
			else
				return new Response(StatusCode.FAILURE,"invalid empId");
		}
		catch(EmployeeException e) {
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
	
	public Response deleteEmployeeById(int empId){
		try {
			if(DataValidator.isValidEmpId(empId)) {
				EmployeeService ems=new EmployeeService();
				if(ems.deleteEmployeeById(empId))
					return new Response(StatusCode.SUCCESS,"successfully deleted the record for "+empId);
				else
					return new Response(StatusCode.FAILURE,"empId "+empId+" doesnt exist in db");
			}
			else
				return new Response(StatusCode.FAILURE,"invalid empId");
		}
		catch(EmployeeException e) {
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
	
	public Response updateEmployeeSalaryById(int empId,int salary){
		try {
			if(DataValidator.isValidEmpId(empId)) {
				EmployeeService ems=new EmployeeService();
				if(ems.updateEmployeeSalaryById(empId, salary))
					return new Response(StatusCode.SUCCESS,"successfully updated salary for "+empId);
				else
					return new Response(StatusCode.FAILURE,"record for "+empId+" doesnt exist");
			}
			else
				return new Response(StatusCode.FAILURE,"invalid empId");
		}
		catch(EmployeeException e) {
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
	
	public Response<List<Employee>> getEmployeesByDept(String dept){
		try {
			if(DataValidator.isValidDepartment(dept)) {
				EmployeeService ems=new  EmployeeService();
				List<Employee> employees=ems.getEmployeesByDept(dept);
				return new Response<List<Employee>>(StatusCode.SUCCESS,"employees in "+dept+" fetched: ",employees);
			}
			else {
				return new Response(StatusCode.FAILURE,dept+" is not a valid department");
			}
		}
		catch(EmployeeException e) {
			return new Response(StatusCode.FAILURE,e.getMessage());
		}
	}
}

