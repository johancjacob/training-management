package com.litmus7.databaseaccess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.sql.ResultSetMetaData;

import com.litmus7.employeemodel.Employee;
import com.litmus7.utils.DbConnection;
import com.litmus7.databaseaccess.DbStatements;
import com.litmus7.utils.DataValidator;


public class DatabaseAccessObject {
	
	Connection conn;
	
	public DatabaseAccessObject() throws SQLException, FileNotFoundException, IOException, ClassNotFoundException{        //constructor for getting connection
		
		conn=DbConnection.getConnection();            
	}
	
	public boolean insertEmployee(Employee emp) throws SQLException {
		
		try {
		
		PreparedStatement pstmt=conn.prepareStatement(DbStatements.insertsql);
		
		pstmt.setInt(1,emp.empId);
		pstmt.setString(2,emp.firstName);
		pstmt.setString(3,emp.lastName);
		pstmt.setString(4,emp.email);
		pstmt.setString(5,emp.phone);
		pstmt.setString(6,emp.dept);
		pstmt.setInt(7,emp.salary);
		pstmt.setDate(8,Date.valueOf(emp.joinDate));
		pstmt.executeUpdate();	
		
		
		
		return true;
	   }
		
	   catch(SQLIntegrityConstraintViolationException e) {
		   System.out.println("Duplicate entry for: "+emp.empId);
		   return false;
		   
	   }
	
		
	}
	
	public boolean deleteEmployee(int empId) throws SQLException {
		
		boolean ifexists=checkIfExists(empId);
				
		if(ifexists) {
			
			PreparedStatement pstmt=conn.prepareStatement(DbStatements.deletesql);
	
			pstmt.setInt(1,empId);
			pstmt.executeUpdate();		
			return true;
		}
		
		else
			return false;
			
	}
	
	public boolean updateEmployeeSalary(int empId,int salary) throws SQLException {
		
		boolean ifexists=checkIfExists(empId);
		
		if(ifexists) {
			
			PreparedStatement pstmt=conn.prepareStatement(DbStatements.updateemployeesalarysql);
			
			pstmt.setInt(1,salary);
			pstmt.setInt(2,empId);
			pstmt.executeUpdate();			
			return true;
		}
		
		else
			return false;
	}
	
	public boolean updateEmployeePhone(int empId,String phone) throws SQLException{
		
		boolean ifexists=checkIfExists(empId);
		
		if(ifexists) {
			
			PreparedStatement pstmt=conn.prepareStatement(DbStatements.updateemployeephonesql);
			
			pstmt.setString(1,phone);
			pstmt.setInt(2, empId);
			pstmt.executeUpdate();
			return true;
		}
		
		else
			return false;
	}
	
	
	public void showEmployees(List<Integer> empIds) throws SQLException{
		
		PreparedStatement pstmt=conn.prepareStatement(DbStatements.showemployeessql);
		
		int empId=-1;
		
		try {
			for(int id:empIds) {
				
				empId=id;
				pstmt.setInt(1, empId);
				ResultSet rs=pstmt.executeQuery();
				rs.next();
				
				System.out.println(rs.getInt("emp_id")+" "+rs.getString("first_name")+rs.getString("last_name")+" "+rs.getString("email")+" "+rs.getString("phone")+" "+rs.getString("department")+" "+rs.getInt("salary")+" "+rs.getString("join_date"));
				
			}
			
		}
		
		catch(SQLException e) {
			
			System.out.println("No record found for "+empId);
		}
		
	}
	
	public void getEmployeesByDept(String dept) throws SQLException{
		
		PreparedStatement pstmt=conn.prepareStatement(DbStatements.getemployeesbydeptsql);
		
		pstmt.setString(1, dept);
		ResultSet rs=pstmt.executeQuery();
		
		
		List<Integer> empIds=new ArrayList<Integer>();
		while(rs.next()) {
			
			empIds.add(rs.getInt(1));
			
		}
		
		showEmployees(empIds);
		
	}
	
	public boolean checkIfExists(int empId) throws SQLException {					
		
		String checkifexists="select count(*) from employee where emp_id=?";
		PreparedStatement checkstmt=conn.prepareStatement(checkifexists);
		checkstmt.setInt(1, empId);
		ResultSet rs=checkstmt.executeQuery();
		rs.next();
		int exists=rs.getInt(1);
		
		if(exists==1)
			return true;
		else
			return false;
		
	}
	
	public int countOfEmployeesInDept(String dept) throws SQLException{
		
		CallableStatement stmt=conn.prepareCall("{call number_of_employees_in_dept(?,?)}");
		stmt.setString(1, dept);
		stmt.registerOutParameter(2,Types.INTEGER);
		stmt.execute();
		
		int count=stmt.getInt(2);
		
		return count;
		
		
	}
	
	public void getEmployeesWithinSalaryRange(int sal1,int sal2) throws SQLException{
		
		CallableStatement stmt=conn.prepareCall("{call employees_within_salary_range(?,?)}");
		stmt.setInt(1, sal1);
		stmt.setInt(2, sal2);
		
		ResultSet rs=stmt.executeQuery();
		
		List<Integer> empIds=new ArrayList<Integer>();
		while(rs.next()) {
			
			empIds.add(rs.getInt(1));
			
		}
		
		showEmployees(empIds);
		
	}
	
	public int getAllColumnsOfEmployee() throws SQLException {
		
		Statement stmt=conn.createStatement();
		
		ResultSet rs=stmt.executeQuery(DbStatements.showallemployeessql);
		
		ResultSetMetaData rsmetadata=rs.getMetaData();
		int count=rsmetadata.getColumnCount();
		for(int i=1;i<(count+1);i++){
			
			System.out.println(rsmetadata.getColumnName(i));
		}
	
		return count;
	}
	
}