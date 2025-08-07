import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;
public class Pstm_with_stream_api {
	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement allcoursespstm = null;
		try {
			Properties props = new Properties();
			
			props.load(new FileReader("driverinfo.properties"));
			
			final String driver =props.getProperty("DRIVER");
			final String url =props.getProperty("URL");
			final String usn =props.getProperty("USN");
			final String pwd =props.getProperty("PWD");
			
			Class.forName(driver);
			con=DriverManager.getConnection(url, usn, pwd);
			
			allcoursespstm=con.prepareStatement(
					"""
						SELECT  s.sid, s.sname, c.course_name, s.fee
						FROM    student s, course c 
						WHERE   s.course_id=c.course_id
						ORDER BY s.sid
					"""
					);
			Scanner scn = new Scanner(System.in);
			loop: while(true) {
				System.out.println("\nChoose one option");
				System.out.println(" 1. Select All Couses Students ");
				System.out.println(" 2. Select Given Course(s) Students");
				System.out.println(" 3. Exit");
				
				int option = scn.nextInt(); scn.nextLine();
				switch(option) {
				case 1:
					displayRows(allcoursespstm);
					break;
				case 2:
					System.out.println("enter the courses with | seperator : ");
					String courses = scn.nextLine();
					
					String[] allcourses = courses.split("\\2|");
					
					String placeholders = String.join(",", Arrays.stream(allcourses).map(course ->"?")
							.toArray(String[]::new));
					
					String query=
							"""
							SELECT  s.sid, s.sname, c.course_name, s.fee
								FROM    student s, course c 
								WHERE   s.course_id=c.course_id
								AND 	c.course_name IN (%s)
								ORDER BY c.course_name, s.sid
							""";
					query = query.formatted(placeholders);
					
					try(
							PreparedStatement givencoursepstm = con.prepareStatement(query);
							){
						
						for(int i =1;i<=allcourses.length;i++) {
							givencoursepstm.setString(i,allcourses[i-1].trim());
						}
						displayRows(givencoursepstm);
						
					}catch(Exception e) {
						e.printStackTrace();
					}
					break;
					
				case 3:
					break loop;
				default :
					System.out.println("invalid option ");
				}
				
			}
			scn.close();	
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(allcoursespstm != null)
					allcoursespstm.close();
			}catch(SQLException e) { }
			
			try {
				if(con != null)
					con.close();
			}catch(SQLException e) { }
		
			
		}
	
	}
	
	private static void displayRows(PreparedStatement pstm) {
		try(
				ResultSet rs=pstm.executeQuery();
				){
			
			ResultSetMetaData rsm = rs.getMetaData();
			int count=0;
			if(rs.next()) {
				
				for(int i=1;i<=rsm.getColumnCount();i++) {
					System.out.print(rsm.getColumnName(i)+"\t");
				}
				System.out.println("--------------------------------------------------------------");
				do {
					System.out.println(rs.getInt(1) + "\t"+ 
							rs.getString(2) + "\t"+ 
							rs.getString(3) + "\t"+ 
							rs.getDouble(4));
					count++;
				}while(rs.next());
			}
			System.out.println("\n"+ count + " rows selected");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
