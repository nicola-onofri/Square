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
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(Config.db_url, Config.db_user, Config.db_pass);
			return conn;
		} catch (SQLException e) { e.printStackTrace(); }
		return conn;
	}

	public static void main(String[] args) {
		//System.out.println(db_url);
		try{
			Connection conn = DriverManager.getConnection(Config.db_url, Config.db_user, Config.db_pass);
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM user");
			
			while(res.next()){
				System.out.println(res.getString("username"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
