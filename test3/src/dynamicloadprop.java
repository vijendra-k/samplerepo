import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;


public class dynamicloadprop {
	public static void main(String[] args)throws ClassNotFoundException ,SQLException ,
	FileNotFoundException,IOException{
		
		Properties prop =new Properties();
		prop.load(new FileReader("driverinfo.properties"));
		
		final String driver =prop.getProperty("DRIVER");
		final String url =prop.getProperty("URL");
		final String usn =prop.getProperty("USN");
		final String pwd =prop.getProperty("PWD");
		Class.forName(driver);
		
		Connection con=DriverManager.getConnection(url,usn,pwd);
		
		Statement stm=con.createStatement();
		
		ResultSet rs=stm.executeQuery("select * from course");
		
		ResultSetMetaData rsm=rs.getMetaData();
		
		System.out.println(rsm.getColumnName(1) +"\t" +rsm.getColumnName(2) +"\t" +rsm.getColumnName(3));
		while(rs.next()) {
			System.out.println(
					rs.getInt(1) +"\t\t" +rs.getString(2) +"\t" +rs.getDouble(3)
					);
		}
		
		rs.close();
		stm.close();
		con.close();
		
	}

}
