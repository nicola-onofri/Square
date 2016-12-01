	package pajc.square.logAuth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import pajc.config.Config;

public class Database {
	
	// Establish a Database Connection
	public Connection getConn(){
		Connection  conn = null;
		try {
			conn = DriverManager.getConnection(Config.db_url, Config.db_user, Config.db_pass);
			return conn;
		} catch (SQLException e) { e.printStackTrace(); }
		return conn;
	}
	
	// Get Last Insert Key from DB
	public static int getLastInsertkey(Statement stmt) throws SQLException{
		int insert_id = -1;
		ResultSet rs = stmt.getGeneratedKeys();
		if (rs.next())
			insert_id = rs.getInt(1);
		return insert_id;
	}
}
