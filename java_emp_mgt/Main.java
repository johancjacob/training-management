package project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
//import java.sql.Statement;

public class Main {
	public static void main(String[] args) throws FileNotFoundException, IOException, SQLException{
		
		//initializing variables
		int empId=0;
		String firstName="";
		String lastName="";
		String email="";
		String phone="";
		String dept="";
		int salary=0;
		String joinDate="";
		
		
		//connection
		String url="jdbc:mysql://localhost:3306/litmus";
		String password="johancjacob_21";
		String user="root";
		Connection conn = DriverManager.getConnection(url, user, password);
		System.out.println("✅ Connected to database successfully!");
		
		String file="src/project/employee.csv";
		
		
		try(BufferedReader br=new BufferedReader(new FileReader(file))){
			
			String line;
			
			//extracting values from csv
			while((line=br.readLine())!=null) {
				line=line.replace("\"", "");
				String[] fields=line.split(",");
				
					
				empId=Integer.parseInt(fields[0]);
				firstName=fields[1];
				lastName=fields[2];
				email=fields[3];
				phone=fields[4];
				dept=fields[5];
				salary=Integer.parseInt(fields[6]);
				joinDate=fields[7];
				
				
				
				
				
				
				//inserting into the database
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
		
		
		
		
		//exception handling
		catch(IOException e) {
			System.out.println("Error"+e.getMessage());
		}
		catch(SQLIntegrityConstraintViolationException e) {
			System.out.println("This emp_id already exists...");
		}
		catch(NumberFormatException e) {
			System.out.println("Error...Insert only integer values...");
		}
		
	
		
		
	}
}

