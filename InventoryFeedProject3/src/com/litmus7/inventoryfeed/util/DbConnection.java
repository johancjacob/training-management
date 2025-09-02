package com.litmus7.inventoryfeed.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.litmus7.inventoryfeed.exception.ProductApplicationException;

import java.io.IOException;
import java.io.InputStream;

public class DbConnection {

    private static String url;
    private static String user;
    private static String password;

    static{
        try (InputStream input = DbConnection.class.getClassLoader().getResourceAsStream("dbconfig.properties")) {
            Properties props = new Properties();
            props.load(input);

            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");

        } 
        catch (IOException | NullPointerException e) {
            throw new RuntimeException(new ProductApplicationException("Couldn't find properties file for DB connection", e));
        } 
    }

    public static Connection getConnection() throws ProductApplicationException {
    	try {
    		return DriverManager.getConnection(url, user, password);
    	}
    	catch (SQLException e) {
            throw new ProductApplicationException("Couldn't connect to DB due to invalid credentials",e);
	        }
	    }
}



