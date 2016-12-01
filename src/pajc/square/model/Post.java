package pajc.square.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;

import com.dropbox.core.DbxException;

import pajc.config.Config;
import pajc.config.FileHelpers;
import pajc.config.Utility;
import pajc.config.Vars;
import pajc.media.FileManager;
import pajc.square.logAuth.Database;

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
	
	// Add Post to Database
	public boolean addPost(Post post, User user) throws DbxException, IOException {
		try {
			Database db = new Database();
			Connection conn = db.getConn();
			Statement stmt = conn.createStatement();
			FileManager fm = new FileManager();
			
			// Get Attached User ID
			String user_id = user.getUserID(user.getUsername());
			
			// Get Media Path
			String media_path = post.getImage().getDescription();

			// Upload File to Dropbox
			String filename = FileHelpers.getUniqueFileName();
			String db_path = Vars.media_path + filename;
			String media_id = String.valueOf(fm.uploadImage(Integer.valueOf(user_id), media_path, db_path));
			
			// Update Image Icon Path
			post.image = new ImageIcon(db_path);
			
			System.out.println(post.image.getDescription());
			
			if(!media_id.equals("") && !user_id.equals("")){
				String query = "INSERT INTO post VALUES (null,'" + Utility.formatDate(post.getDate()) + "','" + media_id + "','"
						+ user_id + "','" + post.getCaption() + "');";
				stmt.executeUpdate(query);
				System.out.println("Post Created!");
				return true;
			}
		} catch (SQLException e) { e.printStackTrace(); }
		return false;
	}
	
	// Delete Post
	public boolean deletePost(Post post) throws DbxException, IOException, SQLException {
		Database db = new Database();
		Connection conn = db.getConn();
		Statement stmt = conn.createStatement();
		FileManager fm = new FileManager();
		
		// Get Attached File Media ID
		System.out.println(post.getImage().getDescription());
		
		String media_id = fm.getMediaID((post.getImage().getDescription()));
		
		System.out.println(media_id);
		
		String media_path = fm.getMediaPath(media_id);
		fm.deleteImage(media_path);
		
		try {
			ResultSet post_res = stmt.executeQuery("SELECT * FROM post WHERE id_media='" + media_id + "';");
			if (post_res.next()){
				String query = "DELETE FROM post WHERE id = '" + post_res.getString("id") + "';";
				stmt.executeUpdate(query);
				System.out.println("Post Deleted!");
			}
		} 
		catch (SQLException e) {e.printStackTrace(); }
		return false;
	}
	
	public static void main(String args[]) throws DbxException, IOException, SQLException{
		
		// Temp config init
		Config config = new Config(false);
		
		User loggedUser = new User("ed3sign", "queenkylie@xoxo.com", "xoxo", "Kylie Jenner", "Simply you queen",
				new ImageIcon(Vars.post_path + "kylie_4.png"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>());
		
		Post examplePost = new Post(loggedUser, "Descrizione di questo post", new Date(), new ArrayList<>(),
				new ArrayList<>(), new ImageIcon("/media/1480520771692.jpg"));
		
		//examplePost.addPost(examplePost, loggedUser);
		examplePost.deletePost(examplePost);
	}
	
	
	/* Getters and Setters */
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

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}
}
