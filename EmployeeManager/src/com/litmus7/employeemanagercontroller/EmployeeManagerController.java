package com.litmus7.employeemanagercontroller;
import com.litmus7.employeemodel.Response;
import com.litmus7.employeeservice.EmployeeService;
import com.litmus7.util.CSVFileReader;
import com.litmus7.util.DataValidator;
import com.litmus7.constants.StatusCode;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.litmus7.employeemodel.Employee;

public class EmployeeManagerController {
	
	public Response<Employee> saveEmployee(Employee emp) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException {
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
	
	public Response<List<Employee>> saveEmployeesFromCSV(String file) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException {
		
		if(file.endsWith(".csv")) {
			List<Employee> totalemployees=CSVFileReader.readCSV(file);
			int countoftotalemployees=totalemployees.size();
			EmployeeService ems=new EmployeeService();
			List<Employee> employees=ems.saveEmployeesFromCSV(totalemployees);
			if(employees.size()==countoftotalemployees)
				return new Response<List<Employee>>(StatusCode.SUCCESS,"all "+employees.size()+" employees saved",employees,employees.size());
			else if(employees.size()==0)
				return new Response(StatusCode.FAILURE,"couldnt save any of the employees");
			else
				return new Response<List<Employee>>(StatusCode.PARTIAL_SUCCESS,"saved "+employees.size()+" employee(s)"+" out of "+countoftotalemployees,employees,employees.size());
		}
		else
			return new Response<List<Employee>>(StatusCode.FAILURE,"not a csv file");
	}
	
	public Response deleteEmployee(int empId) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException {
		
		if(DataValidator.isValidEmpId(empId)) {
			EmployeeService ems=new EmployeeService();
			if(ems.deleteEmployee(empId))
				return new Response(200,"successfully deleted the record for "+empId);
			else
				return new Response(400,"empId "+empId+" doesnt exist in db");
		}
		else
			return new Response(400,"invalid empId");
	}
	
	public Response updateEmployeeSalary(int empId,int salary) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException {
		
		if(DataValidator.isValidEmpId(empId)) {
			EmployeeService ems=new EmployeeService();
			if(ems.updateEmployeeSalary(empId, salary))
				return new Response(200,"successfully updated salary for "+empId);
			else
				return new Response(400,"record for "+empId+" doesnt exist");
		}
		else
			return new Response(400,"not a valid empId");
	}
	
	public Response<List<Employee>> getEmployees(List<Integer> empIds) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException {
		
		List<Integer> allemployeeids=new ArrayList<>(empIds);
		int countoftotalemployeeids=allemployeeids.size();
		EmployeeService ems=new EmployeeService();
		List<Employee> employees=ems.getEmployees(allemployeeids);
		if(employees.size()==countoftotalemployeeids)
			return new Response<List<Employee>>(200,"fetched details of all records :",employees);
		else if(employees.size()==0) {
			return new Response<List<Employee>>(400,"couldnt fetch any of the records ");
		}
		else {
		    return new Response<List<Employee>>(206,"empIds for "+(countoftotalemployeeids-employees.size())+" employee(s) not found ",employees);
		}		
	}
}

