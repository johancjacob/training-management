package com.litmus7.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	
	public static Connection getConnection() throws SQLException {            		   //connection method
        
		String url="jdbc:mysql://localhost:3306/litmus";
		String password="johancjacob_21";
		String user="root";
		Connection conn = DriverManager.getConnection(url, user, password);
				
		return conn;
}

}
