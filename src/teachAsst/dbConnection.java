package teachAsst;

import java.sql.Connection;

import java.sql.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

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

		if (result.next()) {
			privilege = (int) result.getInt(3);
			return privilege;
		}

		return privilege;

	} // end function

	public boolean createUser(String userName, String userPW, int Privilege) {
		String new_query = "Insert into users values ('" + userName + "', '" + userPW + "', " + Privilege + ");";

		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(new_query);
			return true;
		}

		catch (SQLIntegrityConstraintViolationException e) {
			return false;
		}

		catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}

	public int editUser(String userName, String newName, String newPW, int newPriv) {

		String new_query = "update users set ID='" + newName + "', Password='" + newPW + "', Privilege=" + newPriv
				+ " where ID='" + userName + "';";

		try {
			Statement stmt = conn.createStatement();
			int rowsAffected = stmt.executeUpdate(new_query);
			if (rowsAffected == 0) {
				return 1;
			} else {
				return 0;
			}
		}

		catch (SQLIntegrityConstraintViolationException e) {
			return 2;
		}

		catch (SQLException e) {
			System.out.println(e);
			return 3;
		}
	}

} // end class