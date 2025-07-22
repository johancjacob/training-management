package project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.List;

public class EmployeeManagerController {
	
	boolean filenotread=true;
	boolean validentries=true;
	BufferedReader br;
	FileReader fr;
	Connection conn;
	int empId=0;
	String firstName="";
	String lastName="";
	String email="";
	String phone="";
	String dept="";
	int salary=0;
	String joinDate="";
	
	
	EmployeeManagerController(String file) throws FileNotFoundException{   //constructor for opening the file
		fr=new FileReader(file);
		br=new BufferedReader(fr);
	}
	

	
	public static class ValidationUtils{      //nested class for validation
		private static final List<String> VALID_DEPTS=Arrays.asList("HR","Engineering","Marketing","Sales","Logistics");
		
				public static boolean isValidEmpId(int id) {
					return ((id>=100)&&(id<=999));
				}
				
				public static boolean isValidEmail(String mail) {
					return mail!=null && mail.endsWith("@gmail.com");
				}
				
				public static boolean isValidDepartment(String dep) {
					return dep!=null && VALID_DEPTS.contains(dep);
				}
				
	}
	
	public String getConnection() throws SQLException {            		   //connection method
	        
			String url="jdbc:mysql://localhost:3306/litmus";
			String password="johancjacob_21";
			String user="root";
			conn = DriverManager.getConnection(url, user, password);
			return ("✅ Connected to database successfully!");
	}
	
	
	public void readCSV() {													//readcsv method
			
			
		
		    try{
		    	
			
			String line;
			
			//extracting values from csv for each line
			if((line=br.readLine())!=null) {
				line=line.replace("\"", "");
				String[] fields=line.split(",");
				
			
				empId  = Integer.parseInt(fields[0]);		
				firstName=fields[1];
				lastName=fields[2];
				email=fields[3];
				phone=fields[4];
				dept=fields[5];
				salary=Integer.parseInt(fields[6]);
				joinDate=fields[7];
				
				
				if(!ValidationUtils.isValidEmpId(empId)) {
					System.out.println("Invalid emp_id for:"+empId);
					validentries=false;
				}
				else if(!ValidationUtils.isValidEmail(email)) {
					System.out.println("Invalid email for:"+empId);
					validentries=false;
				}
				else if(!ValidationUtils.isValidDepartment(dept)) {
					System.out.println("Invalid department for:"+empId);
					validentries=false;
					
				}
					
				
				
	         }
			
			else {
					filenotread=false;			
					br.close();
				 }
			
		    }
		    
		    catch(IOException e) {
				System.out.println("Error"+e.getMessage());}
			
			
	}
	
	
	public void writeToDb() throws SQLException{							//db insertion method
		
		try {
			
		if(filenotread && validentries) {									 
			
			String sql="insert into employee(emp_id,first_name,last_name,email,phone,department,salary,join_date) values(?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,empId);
			pstmt.setString(2,firstName);
			pstmt.setString(3,lastName);
			pstmt.setString(4,email);
			pstmt.setString(5,phone);
			pstmt.setString(6,dept);
			pstmt.setInt(7,salary);
			pstmt.setDate(8,Date.valueOf(joinDate));
			pstmt.executeUpdate();	
			
			

			
			
			System.out.println("Successfully inserted:");
			System.out.println(empId+" "+firstName+" "+lastName+" "+email+" "+phone+" "+dept+" "+salary+" "+joinDate);
			
			return;
			
			
			
		}
		
		validentries=true;
				
		
		}
		
		catch(SQLIntegrityConstraintViolationException e) {
			System.out.println("The emp_id "+empId+ " already exists...");
			//filenotread=false;
		}
	
	}
	
	
	public void writeDataToDb() throws SQLException, FileNotFoundException, IOException {
		
		getConnection();
		
		while(filenotread){     //reads each line and writes it to db until the file is empty
			readCSV();
			writeToDb();
			}
		
	}
	
}
