package com.litmus7.employeemanager.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.litmus7.employeemanager.constants.LogMessages;
import com.litmus7.employeemanager.employeemodel.Employee;
import com.litmus7.employeemanager.exception.EmployeeApplicationException;
import com.litmus7.employeemanager.util.DbConnection;



public class EmployeeDao {
	private static final Logger logger=LogManager.getLogger(EmployeeDao.class);
	public boolean saveEmployee(Employee employee) throws EmployeeApplicationException {
		logger.trace(LogMessages.ENTER_METHOD_LOG_MESSAGE,"saveEmployee()");
		try(Connection conn=DbConnection.getConnection()) {
			logger.info(LogMessages.DB_CONNECTION_SUCCESS);
			PreparedStatement pstmt=conn.prepareStatement(SqlConstants.INSERT_EMPLOYEE_QUERY);
			
			pstmt.setInt(1,employee.empId);
			pstmt.setString(2,employee.firstName);
			pstmt.setString(3,employee.lastName);
			pstmt.setString(4,employee.email);
			pstmt.setString(5,employee.phone);
			pstmt.setString(6,employee.dept);
			pstmt.setInt(7,employee.salary);
			pstmt.setDate(8,Date.valueOf(employee.joinDate));
			pstmt.executeUpdate();	
			logger.info(LogMessages.EXIT_METHOD_LOG_MESSAGE,"saveEmployee");
			return true;
	   }
		catch(SQLException e) {
			logger.error(LogMessages.UNEXPECTED_ERROR_LOG_MESSAGE);
			throw new EmployeeApplicationException("error in sql operation",e);
		}
	}
	
	public List<Employee> getEmployeesById(List<Integer> employeeids) throws EmployeeApplicationException{
		logger.trace(LogMessages.ENTER_METHOD_LOG_MESSAGE,"getEmployeesById()");
		try(Connection conn=DbConnection.getConnection()){
			logger.info(LogMessages.DB_CONNECTION_SUCCESS);
			List<Employee> employees=new ArrayList<>();
			PreparedStatement pstmt=conn.prepareStatement(SqlConstants.GET_EMPLOYEE_BY_ID_QUERY);
			for(int id:employeeids) {
					pstmt.setInt(1, id);
					ResultSet rs=pstmt.executeQuery();
					if(rs.next()) {
						Employee employee=new Employee(rs.getInt("emp_id"),rs.getString("first_name"),rs.getString("last_name"),rs.getString("email"),rs.getString("phone"),rs.getString("department"),rs.getInt("salary"),rs.getString("join_date"));
						employees.add(employee);
					}
			}
				logger.info(LogMessages.EXIT_METHOD_LOG_MESSAGE,"getEmployeesById()");
				return employees;
		}
		catch(SQLException e) {
			logger.error(LogMessages.UNEXPECTED_ERROR_LOG_MESSAGE);
			throw new EmployeeApplicationException("error in sql operation",e);
		}
    }
	
	public boolean updateEmployee(Employee emp) throws EmployeeApplicationException {
		logger.trace(LogMessages.ENTER_METHOD_LOG_MESSAGE,"updateEmployee()");
		try(Connection conn=DbConnection.getConnection()){
			logger.info(LogMessages.DB_CONNECTION_SUCCESS);
			PreparedStatement pstmt=conn.prepareStatement(SqlConstants.UPDATE_EMPLOYEE_QUERY);
			
			pstmt.setString(1,emp.firstName);
			pstmt.setString(2,emp.lastName);
			pstmt.setString(3,emp.email);
			pstmt.setString(4,emp.phone);
			pstmt.setString(5,emp.dept);
			pstmt.setInt(6,emp.salary);
			pstmt.setString(7,emp.joinDate);
			pstmt.setInt(8,emp.empId);
			pstmt.executeUpdate();
			logger.info(LogMessages.EXIT_METHOD_LOG_MESSAGE,"updateEmployee()");
			return true;
			
		}
		catch(SQLException e) {
			logger.error(LogMessages.UNEXPECTED_ERROR_LOG_MESSAGE);
			throw new EmployeeApplicationException("error in sql operation",e);
		}
	}
	
	public boolean deleteEmployeeById(int empId) throws EmployeeApplicationException{
		logger.trace(LogMessages.ENTER_METHOD_LOG_MESSAGE,"deleteEmployeeById()");
		try(Connection conn=DbConnection.getConnection()){
			logger.info(LogMessages.DB_CONNECTION_SUCCESS);
			PreparedStatement pstmt=conn.prepareStatement(SqlConstants.DELETE_EMPLOYEE_BY_ID_QUERY);
	
			pstmt.setInt(1,empId);
			pstmt.executeUpdate();
			logger.info(LogMessages.EXIT_METHOD_LOG_MESSAGE,"deleteEmployeeById()");
			return true;
		}
		catch(SQLException e) {
			logger.error(LogMessages.UNEXPECTED_ERROR_LOG_MESSAGE);
			throw new EmployeeApplicationException("error in sql operation",e);
		}
	}
	
	public boolean updateEmployeeSalaryById(int empId,int salary) throws EmployeeApplicationException {
		logger.trace(LogMessages.ENTER_METHOD_LOG_MESSAGE,"updateEmployeeSalaryById()");
		try(Connection conn=DbConnection.getConnection()){
			logger.info(LogMessages.DB_CONNECTION_SUCCESS);
			PreparedStatement pstmt=conn.prepareStatement(SqlConstants.UPDATE_EMPLOYEE_SALARY_BY_ID_QUERY);
			
			pstmt.setInt(1,salary);
			pstmt.setInt(2,empId);
			pstmt.executeUpdate();
			logger.info(LogMessages.EXIT_METHOD_LOG_MESSAGE,"updateEmployeeSalaryById()");
			return true;
		}
		catch(SQLException e) {
			logger.error(LogMessages.UNEXPECTED_ERROR_LOG_MESSAGE);
			throw new EmployeeApplicationException("error in sql operation",e);
		}
	}
	
	public List<Employee> getEmployeesByDept(String dept) throws EmployeeApplicationException{
		logger.trace(LogMessages.ENTER_METHOD_LOG_MESSAGE,"getEmployeesByDept()");
		try(Connection conn=DbConnection.getConnection()){
			logger.info(LogMessages.DB_CONNECTION_SUCCESS);
			List<Employee> employees=new ArrayList<>();
			CallableStatement cstmt=conn.prepareCall("{call get_employees_by_dept(?,?)}");
			cstmt.setString(1, dept);
			ResultSet rs=cstmt.executeQuery();
			while(rs.next()) {
				Employee employee=new Employee(rs.getInt("emp_id"),rs.getString("first_name"),rs.getString("last_name"),rs.getString("email"),rs.getString("phone"),rs.getString("department"),rs.getInt("salary"),rs.getString("join_date"));
			    employees.add(employee);
			}
			logger.info(LogMessages.EXIT_METHOD_LOG_MESSAGE,"getEmployeesById()");
			return employees;
		}
		catch(SQLException e) {
			logger.error(LogMessages.UNEXPECTED_ERROR_LOG_MESSAGE);
			throw new EmployeeApplicationException("error in sql operation",e);
		}
	}
	
	public boolean checkIfExists(int empId) throws EmployeeApplicationException {					
		try(Connection conn=DbConnection.getConnection()){
			PreparedStatement checkstmt=conn.prepareStatement(SqlConstants.CHECK_IF_EMPLOYEE_EXISTS_BY_ID_QUERY);
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
			throw new EmployeeApplicationException("error in sql operation",e);
		}
	}
	
	public int[] addEmployeesInBatch(List<Employee> employees) throws EmployeeApplicationException{
		logger.trace(LogMessages.ENTER_METHOD_LOG_MESSAGE,"addEmployeesInBatch()");
		try(Connection conn=DbConnection.getConnection()){
			logger.info(LogMessages.DB_CONNECTION_SUCCESS);
			PreparedStatement pstmt=conn.prepareStatement(SqlConstants.INSERT_EMPLOYEE_QUERY);
			for(Employee employee:employees) {
				pstmt.setInt(1,employee.empId);
				pstmt.setString(2,employee.firstName);
				pstmt.setString(3,employee.lastName);
				pstmt.setString(4,employee.email);
				pstmt.setString(5,employee.phone);
				pstmt.setString(6,employee.dept);
				pstmt.setInt(7,employee.salary);
				pstmt.setString(8,employee.joinDate);
				pstmt.addBatch();
			}
			int[] results=pstmt.executeBatch();
			logger.info(LogMessages.EXIT_METHOD_LOG_MESSAGE,"addEmployeesInBatch()");
			return results;
		}
		catch(SQLException e) {
			logger.error(LogMessages.UNEXPECTED_ERROR_LOG_MESSAGE);
			throw new EmployeeApplicationException("error in sql operation",e);
		}
	}
	
	public boolean transferEmployeesToDepartment(List<Integer> employeeIds,String dept) throws EmployeeApplicationException{
		logger.trace(LogMessages.ENTER_METHOD_LOG_MESSAGE,"transferEmployeesToDepartment()");
		Connection conn=null;
		try{
			conn=DbConnection.getConnection();
			logger.info(LogMessages.DB_CONNECTION_SUCCESS);
		    conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement(SqlConstants.UPDATE_EMPLOYEE_DEPT_BY_ID_QUERY);
			for(Integer empId:employeeIds) {
				pstmt.setString(1,dept);
				pstmt.setInt(2,empId);
				int updated=pstmt.executeUpdate();
				if(updated==0) {
					conn.rollback();
					throw new EmployeeApplicationException("Transferred none of the employees.");
				}
			}
			conn.commit();
			logger.info(LogMessages.EXIT_METHOD_LOG_MESSAGE,"transferEmployeesToDepartment()");
			return true;
		}
		catch(SQLException e) {
			logger.error(LogMessages.UNEXPECTED_ERROR_LOG_MESSAGE);
			try {
				conn.rollback();
			}
			catch(SQLException rollbackexception) {
				throw new EmployeeApplicationException("error in rollback operation",rollbackexception);
			}
			throw new EmployeeApplicationException("error in sql operation",e);
		}
	}
}
	
	