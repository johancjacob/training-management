package com.litmus7.employeemanager.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.litmus7.employeemanager.exception.EmployeeException;

import java.io.IOException;
import java.io.InputStream;

public class DbConnection {
	
	public static Connection getConnection() throws EmployeeException{    //connection method
	 Properties props=new Properties();
	 
     try(InputStream input=DbConnection.class.getClassLoader().getResourceAsStream("dbconfig.properties")) { 
    	props.load(input);
    
        String url=props.getProperty("db.url");
        String user=props.getProperty("db.user");
        String password=props.getProperty("db.password");
        
		Connection conn = DriverManager.getConnection(url, user, password);
				
		return conn;
		
     }
     catch(IOException | NullPointerException e) {
    	 throw new EmployeeException("couldnt find properties file for Db connection",e);
     }
     catch(SQLException e) {
    	 throw new EmployeeException("couldnt connect to db due to invalid credentials",e);
     }
   }
}
     
    