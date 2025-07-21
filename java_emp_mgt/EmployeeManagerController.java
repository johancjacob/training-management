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

public class EmployeeManagerController {
	
	boolean filenotread=true;
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
			
		if(filenotread) {									 
			
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
		}
		
		}
		
		catch(SQLIntegrityConstraintViolationException e) {
			System.out.println("The emp_id "+empId+ " already exists...");
			//filenotread=false;
		}
	}
	
	
	public void writeDataToDb() throws SQLException, FileNotFoundException, IOException {
		
		getConnection();
		
		while(filenotread){     //reads each line and writes it to db until the file is empty or a duplicate entry is encountered
			readCSV();
			writeToDb();
			}
		
	}
	
}

