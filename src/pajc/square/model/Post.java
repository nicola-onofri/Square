package pajc.square.model;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;

public class Post {
	private User owner;
	private String caption;
	private Date date;
	private ArrayList<String> comments;
	private ArrayList<User> likes;
	private ImageIcon image;

	public Post(User owner, String description, Date date, ArrayList<String> comments, 
			ArrayList<User> likes, ImageIcon image) {
		this.owner = owner;
		this.caption = description;
		this.date = date;
		this.comments = new ArrayList<>();
		this.likes = new ArrayList<>();
		this.image = image;
	}
	
//	public boolean addPost(Post post) {
//		try {
//			Database db = new Database();
//			Connection conn = db.getConn();
//			Statement stmt = conn.createStatement();
//			String query = "INSERT INTO post VALUES (null,null,'" + user.username + "','" + user.email + "','"
//					+ user.password + "','" + user.name + "','" + user.about + "');";
//			System.out.println(query);
//			stmt.executeUpdate(query);
//			return true;
//		} catch (SQLException e) { e.printStackTrace(); }
//		return false;
//	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getDescription() {
		return caption;
	}

	public void setDescription(String description) {
		this.caption = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ArrayList<String> getComments() {
		return comments;
	}

	public void setComments(ArrayList<String> comments) {
		this.comments = comments;
	}

	public ArrayList<User> getLikes() {
		return likes;
	}

	public void setLikes(ArrayList<User> likes) {
		this.likes = likes;
	}

	public ImageIcon getImage() {
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}
}
