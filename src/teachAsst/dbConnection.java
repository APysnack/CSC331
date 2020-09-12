package teachAsst;

import java.sql.Connection;

import java.sql.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
	String name;
	String password;
	String url;
	String query;
	Connection conn;

	dbConnection() throws SQLException {
		this.name = "root";
		this.password = "SQLPassword";
		this.url = "jdbc:mysql://localhost:3306/teachasst";

		Connection conn = DriverManager.getConnection(url, name, password);
		this.conn = conn;
	}

	public int connectUser(String userName, String userPW) throws SQLException {
		int privilege = 0;
		
		String new_query = "Select * from users where ID = '" + userName + "' AND Password = '" + userPW + "'";
		
		Statement stmt = conn.createStatement();
		ResultSet result = stmt.executeQuery(new_query);
		
		if(result.next()) {
			privilege = (int)result.getInt(3);
			return privilege;
			}
		
		return privilege;
		
		} // end function

} // end class