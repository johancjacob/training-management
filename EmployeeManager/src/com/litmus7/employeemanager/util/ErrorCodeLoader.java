package com.litmus7.employeemanager.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import com.litmus7.employeemanager.exception.EmployeeApplicationException;

public class ErrorCodeLoader {
	private static final Properties properties=new Properties();
	static {
		try(InputStream input=EmployeeApplicationException.class.getClassLoader().getResourceAsStream("errorcodes.properties")){
			if(input==null)
				throw new RuntimeException("Properties file not found");
			properties.load(input);
		}
		catch(IOException e) {
			throw new RuntimeException("Failed to load error codes");
		}
	}
	public static String getMessage(String key) {
		return properties.getProperty(key);
	}
	public static String getMessage(String key,Object obj) {
		return MessageFormat.format(properties.getProperty(key),obj);
	}
}
