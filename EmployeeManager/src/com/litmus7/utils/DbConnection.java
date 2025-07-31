package com.litmus7.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class DbConnection {
	
	public static Connection getConnection() throws SQLException,IOException,FileNotFoundException, ClassNotFoundException{    //connection method
	 Properties props=new Properties();
	 
     try(InputStream input=DbConnection.class.getClassLoader().getResourceAsStream("dbconfig.properties")) { 
    	props.load(input);
    
        String url=props.getProperty("db.url");
        String user=props.getProperty("db.user");
        String password=props.getProperty("db.password");
        
		Connection conn = DriverManager.getConnection(url, user, password);
				
		return conn;
		
     }
     catch(IOException  | NullPointerException e) {
    	 System.out.println("unable to find the config file");
    	 return null;
     }
   }
}
     
    