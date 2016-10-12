package pajc.square.logAuth;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import pajc.square.model.User;

public class Login {

	boolean logged_in;
	User logged_in_user;

	public Login(String username, String password) {

		try {
			Database db = new Database();
			Connection conn = db.getConn();
			Statement stmt = conn.createStatement();
			ResultSet user_check = stmt.executeQuery(
					"SELECT * FROM user WHERE username='" + username + "' AND password='" + password + "';");
			if (user_check.next()) {
				this.logged_in = true;
				User logged_in_user = new User(user_check.getString("username"), user_check.getString("email"),
						user_check.getString("password"), user_check.getString("name"), user_check.getString("about"),
						// TODO how can we store profile pic, posts list,
						// followers and followings?
						null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
				this.logged_in_user = logged_in_user;
			} else
				this.logged_in = false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean isLogged_in() {
		return logged_in;
	}

	public void setLogged_in(boolean logged_in) {
		this.logged_in = logged_in;
	}

	public User getLogged_in_user() {
		return logged_in_user;
	}

	public void setLogged_in_user(User logged_in_user) {
		this.logged_in_user = logged_in_user;
	}

}
