package pajc.square.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.mysql.fabric.xmlrpc.base.Array;

import pajc.square.logAuth.Database;

public class User {
	private String username;
	private String email;
	private String password;
	private String name;
	private String about;
	private ImageIcon profilePicture;
	private ArrayList<User> followers;
	private ArrayList<User> followings;
	private ArrayList<Notification> notifications;
	private ArrayList<Post> posts;

	// User Constructor
	public User(String username, String email, String password, String name, String about, ImageIcon profilePicture,
			ArrayList<Post> posts, ArrayList<User> followers, ArrayList<User> followings,
			ArrayList<Notification> notifications) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.name = name;
		this.about = about;
		this.profilePicture = profilePicture;
		this.posts = posts;
		this.followers = new ArrayList<>();
		this.followings = new ArrayList<>();
		this.notifications = new ArrayList<>();
	}

	// Add User to Database
	// TODO add posts, followers, followings fields
	public boolean addUser(User user) {
		try {
			Database db = new Database();
			Connection conn = db.getConn();
			Statement stmt = conn.createStatement();
			ResultSet user_check = stmt.executeQuery("SELECT * FROM user WHERE username='" + user.username + "';");
			if (user_check.next())
				return false;
			else {
				System.out.println("new user");
				String query = "INSERT INTO user VALUES (null,null,'" + user.username + "','" + user.email + "','"
						+ user.password + "','" + user.name + "','" + user.about + "');";
				System.out.println(query);
				stmt.executeUpdate(query);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// Get Media ID from Path
	public String getUserID(String username){
		try {
			Database db = new Database();
			Connection conn = db.getConn();
			Statement stmt = conn.createStatement();
			ResultSet user = stmt.executeQuery("SELECT * FROM user WHERE username='" + username + "';");
						
			if (user.next())
				return user.getString("id");
		} 
		catch (SQLException e) {e.printStackTrace(); }
		return null;
	}

	// Getters and Setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getAbout() {
		return about;
	}

	public ArrayList<Post> getPosts() {
		return posts;
	}

	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}

	public ArrayList<User> getFollowers() {
		return followers;
	}

	public void setFollowers(ArrayList<User> followers) {
		this.followers = followers;
	}

	public ArrayList<User> getFollowings() {
		return followings;
	}

	public void setFollowings(ArrayList<User> followings) {
		this.followings = followings;
	}

	public ImageIcon getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(ImageIcon profilePicture) {
		this.profilePicture = profilePicture;
	}

	public ArrayList<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(ArrayList<Notification> notifications) {
		this.notifications = notifications;
	}

}
