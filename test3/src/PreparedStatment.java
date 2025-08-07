import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
public class PreparedStatment {
	public static void main(String[] args) {
		Scanner scn =new Scanner(System.in);
		
		final String insertstudent =
				"""
				insert into student (sid,sname,course_id,fee)
				values(student_seq.nextval,?,?,?)
				""";
		
		final String getcourseid =
				"""
				select course_id,course_tomfee from course
				where course_name=?
				""";
		
		final String allcourses =
				"""
				select course_name from course order by course_id
				""";
		
		try (
				
				Connection con =DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE"
						,"hknit2pm","hari");
				PreparedStatement insertstudentpstm = con.prepareStatement(insertstudent);
				PreparedStatement getcourseidpstm = con.prepareStatement(getcourseid);
				PreparedStatement allcoursespstm = con.prepareStatement(allcourses);
			){
			int count=1;
			String option="N";
			program: do {
				System.out.println("enter "+count+++" name : ");
				insertstudentpstm.setString(1,scn.nextLine());
				
				int courseid;
				double fee;
				while(true) {
					System.out.println("enter the course name :");
					getcourseidpstm.setString(1,scn.nextLine());
					
					try(ResultSet getcourseidrs= getcourseidpstm.executeQuery()){
						if(getcourseidrs.next()) {
							courseid =getcourseidrs.getInt(1);
							fee =getcourseidrs.getDouble(2);
							break;
						}
						else {
							try(ResultSet allcoursesrs = allcoursespstm.executeQuery() ){
								ArrayList <String> courselist = new ArrayList<>();
								if(allcoursesrs.next()) {
									do {
										courselist.add("'"+allcoursesrs.getString(1)+"'");
									}while(allcoursesrs.next());
									System.out.println("select from the below courses ");
									System.out.println(" "+courselist);
								}
								else {
									System.out.println("error: no courses available ");
									System.out.println("try after courses are available ");
									
									break program;
								}
							}
							catch(Exception e) {
								e.printStackTrace();
							}
						}
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
				
				insertstudentpstm.setInt(2,courseid);
				insertstudentpstm.setDouble(3, fee);
				
				insertstudentpstm.executeUpdate();
				System.out.println("row inserted ");
				
				System.out.println("do you want to continue (Y/N) : ");
				option = scn.next(); scn.nextLine();
				
				
			}while(option.equalsIgnoreCase("Y"));
			
			System.out.println("\n ==============================thank you ================================t");
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		scn.close();
	}
}
