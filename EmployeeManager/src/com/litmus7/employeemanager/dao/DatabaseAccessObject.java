package com.litmus7.employeemanager.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.litmus7.employeemanager.employeemodel.Employee;
import com.litmus7.employeemanager.exception.EmployeeException;
import com.litmus7.employeemanager.util.DbConnection;



public class DatabaseAccessObject {
	
	public boolean saveEmployee(Employee employee) throws EmployeeException {
		
		try(Connection conn=DbConnection.getConnection()) {
			PreparedStatement pstmt=conn.prepareStatement(DbStatements.insertsql);
			
			pstmt.setInt(1,employee.empId);
			pstmt.setString(2,employee.firstName);
			pstmt.setString(3,employee.lastName);
			pstmt.setString(4,employee.email);
			pstmt.setString(5,employee.phone);
			pstmt.setString(6,employee.dept);
			pstmt.setInt(7,employee.salary);
			pstmt.setDate(8,Date.valueOf(employee.joinDate));
			pstmt.executeUpdate();	
			
			return true;
	   }
		catch(SQLException e) {
			throw new EmployeeException("error in sql operation",e);
		}
	}
	
	public List<Employee> getEmployeesById(List<Integer> validemployeeids) throws EmployeeException{
		try(Connection conn=DbConnection.getConnection()){
			List<Employee> employees=new ArrayList<>();
			PreparedStatement pstmt=conn.prepareStatement(DbStatements.showemployeessql);
			int empId=-1;
			for(int id:validemployeeids) {
					empId=id;
					pstmt.setInt(1, empId);
					ResultSet rs=pstmt.executeQuery();
					rs.next();
					Employee employee=new Employee(rs.getInt("emp_id"),rs.getString("first_name"),rs.getString("last_name"),rs.getString("email"),rs.getString("phone"),rs.getString("department"),rs.getInt("salary"),rs.getString("join_date"));
					employees.add(employee);
			}
				return employees;
		}
		catch(SQLException e) {
			throw new EmployeeException("error in sql operation",e);
		}
    }
	
	public boolean updateEmployee(Employee emp) throws EmployeeException {
		try(Connection conn=DbConnection.getConnection()){
			PreparedStatement pstmt=conn.prepareStatement(DbStatements.updateemployeesql);
			
			pstmt.setString(1,emp.firstName);
			pstmt.setString(2,emp.lastName);
			pstmt.setString(3,emp.email);
			pstmt.setString(4,emp.phone);
			pstmt.setString(5,emp.dept);
			pstmt.setInt(6,emp.salary);
			pstmt.setString(7,emp.joinDate);
			pstmt.setInt(8,emp.empId);
			pstmt.executeUpdate();
			return true;
			
		}
		catch(SQLException e) {
			throw new EmployeeException("error in sql operation",e);
		}
	}
	
	public boolean deleteEmployeeById(int empId) throws EmployeeException{
		try(Connection conn=DbConnection.getConnection()){
			PreparedStatement pstmt=conn.prepareStatement(DbStatements.deletesql);
	
			pstmt.setInt(1,empId);
			pstmt.executeUpdate();		
			return true;
		}
		catch(SQLException e) {
			throw new EmployeeException("error in sql operation",e);
		}
	}
	
	public boolean updateEmployeeSalaryById(int empId,int salary) throws EmployeeException {
		try(Connection conn=DbConnection.getConnection()){
			PreparedStatement pstmt=conn.prepareStatement(DbStatements.updateemployeesalarysql);
			
			pstmt.setInt(1,salary);
			pstmt.setInt(2,empId);
			pstmt.executeUpdate();			
			return true;
		}
		catch(SQLException e) {
			throw new EmployeeException("error in sql operation",e);
		}
	}
	
	public List<Employee> getEmployeesByDept(String dept) throws EmployeeException{
		try(Connection conn=DbConnection.getConnection()){
			List<Employee> employees=new ArrayList<>();
			CallableStatement cstmt=conn.prepareCall("{call get_employees_by_dept(?,?)}");
			cstmt.setString(1, dept);
			ResultSet rs=cstmt.executeQuery();
			while(rs.next()) {
				Employee employee=new Employee(rs.getInt("emp_id"),rs.getString("first_name"),rs.getString("last_name"),rs.getString("email"),rs.getString("phone"),rs.getString("department"),rs.getInt("salary"),rs.getString("join_date"));
			    employees.add(employee);
			}
			return employees;
		}
		catch(SQLException e) {
			throw new EmployeeException("error in sql operation",e);
		}
	}
	
	public boolean checkIfExists(int empId) throws EmployeeException {					
		try(Connection conn=DbConnection.getConnection()){
			PreparedStatement checkstmt=conn.prepareStatement(DbStatements.checkifexists);
			checkstmt.setInt(1, empId);
			ResultSet rs=checkstmt.executeQuery();
			rs.next();
			int exists=rs.getInt(1);
			
			if(exists==1)
				return true;
			else
				return false;
		}
		catch(SQLException e) {
			throw new EmployeeException("error in sql operation",e);
		}
	}
}
	
	