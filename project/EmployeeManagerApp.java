package project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class EmployeeManagerApp {
	
	
	public static void main(String[] args) throws SQLException, FileNotFoundException, IOException{
		String file="src/project/employee.csv";
		
		EmployeeManagerController emc=new EmployeeManagerController(file);
		emc.writeDataToDb();
}
}
