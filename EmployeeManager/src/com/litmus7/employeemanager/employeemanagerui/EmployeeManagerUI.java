package com.litmus7.employeemanager.employeemanagerui;

import com.litmus7.employeemanager.employeemanagercontroller.EmployeeManagerController;
import com.litmus7.employeemanager.employeemodel.Employee;
import com.litmus7.employeemanager.employeemodel.Response;
import com.litmus7.employeemanager.exception.EmployeeApplicationException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeeManagerUI {
	
	public static void main(String[] args) throws SQLException, FileNotFoundException, IOException, ClassNotFoundException, EmployeeApplicationException{
		
			String file="employee.csv";
			Response<?> res;
			EmployeeManagerController emc=new EmployeeManagerController();
			
			Employee emp1=new Employee(100,"Varun","Kumar","vk@gmail.com","8798567990","Sales",43000,"2023-09-07"); 
			res=emc.saveEmployee(emp1);								//insert just one employee
			System.out.println(res.message);
			
			res=emc.saveEmployeesFromCSV(file);   					//inserting a list of employees from csv
			System.out.println(res.message);
			
			res=emc.getEmployeesById(Arrays.asList(1090,1100,1230,112));	//lists the employee record(s) for the given existing empIds
			System.out.println(res.message+'\n'+res.data);
			
			Employee emp2=new Employee(100,"Johan","Jacob","jcj21@gmail.com","6798346771","Engineering",12000,"2021-09-02");
			res=emc.updateEmployee(emp2);  							//update the entire employee record for an existing empId	
			System.out.println(res.message);
			
			/*res=emc.deleteEmployeeById(102);						//deleting an employee by empId
			System.out.println(res.message);*/
			
			res=emc.updateEmployeeSalaryById(112,14000);  			//to update employee salary by empId
			System.out.println(res.message);
			
			res=emc.getEmployeesByDept("HR");						//lists the employee record(s) in a given dept.
			System.out.println(res.message+'\n'+res.data);
			
			Employee emp3=new Employee(120,"Varun","Kumar","vk@gmail.com","8798567990","Sales",43000,"2023-09-07");
			Employee emp4=new Employee(121,"Johan","Jacob","jcj21@gmail.com","6798346771","Engineering",12000,"2021-09-02");
			Employee emp5=new Employee(122,"Johan","Jacob","jcj21@gmail.com","6798346771","Engineering",12000,"2021-09-02");
			Employee emp6=new Employee(123,"Johan","Jacob","jcj21@gmail.com","6798346771","Engineering",12000,"2021-09-02");
			Employee emp7=new Employee(129,"Johan","Jacob","jcj21@gmail.com","6798346771","Engineering",12000,"2021-09-02");
			List<Employee> emps=new ArrayList((Arrays.asList(emp3,emp4,emp5,emp6,emp7)));
			res=emc.addEmployeesInBatch(emps);        				//insert employees as a batch
			System.out.println(res.message);
			
			List<Integer> ids=new ArrayList((Arrays.asList(100,101,103)));
			res=emc.transferEmployeesToDepartment(ids,"HR");		//transfer only if all empIds exist in db
			System.out.println(res.message);
	}
}
