import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Resultset {
	public static void main(String[] args)throws ClassNotFoundException ,SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hknit2pm","hari");
		
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
