package com.litmus7.utils;

import java.util.Arrays;
import java.util.List;

public class DataValidator {
	
	public static final List<String> VALID_DEPTS=Arrays.asList("HR","Engineering","Marketing","Sales","Logistics");
	
	public static boolean isValidEmpId(int id) {
		return ((id>=100)&&(id<=999));
	}
	
	public static boolean isValidEmail(String mail) {
		return mail!=null && mail.endsWith("@gmail.com");
	}
	
	public static boolean isValidDepartment(String dep) {
		return dep!=null && VALID_DEPTS.contains(dep);
	}
	
	public static boolean isValidDate(String date) {
		String regex="^\\d{4}-(0[1-9]|1[0-2])-([0][1-9]|[12][0-9]|3[01])$";
		if(date.matches(regex)) {
			return true;
		}
		else
			return false;
	}

}