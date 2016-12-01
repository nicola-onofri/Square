package pajc.media;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicLong;

import com.dropbox.core.DbxException;

import pajc.config.Config;
import pajc.config.Utility;
import pajc.square.logAuth.Database;
import pajc.square.logAuth.DropboxAPI;

public class FileManager {
	
	DropboxAPI dbx_api;
	Database db;
	Connection conn;
	
	// Constructor
	public FileManager() throws DbxException, IOException{
		Database db = new Database(); this.db = db;
		Connection conn = db.getConn(); this.conn = conn;
		DropboxAPI dbx_api = new DropboxAPI();  this.dbx_api = dbx_api;
	}
	
	// Upload Image (Dropbox + Database)
	public int uploadImage(int user_id, String filename, String upload_path) throws SQLException, DbxException, IOException{
		
		// Upload file to dropbox
		boolean dbx_up = dbx_api.dbx_upload_file(filename, upload_path);
		int insert_id = -1;
		
		// If Upload is successful insert query to db
		if(dbx_up){
			try {
				Statement stmt = conn.createStatement();
				String query = "INSERT INTO media VALUES (null,'" + user_id + "','" + upload_path +"');";
				stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
				
				// Get Last Generated Key
				insert_id = Database.getLastInsertkey(stmt);
			}
			catch (SQLException e) {e.printStackTrace(); }	
		}
		
		return insert_id;
	}

	// Delete Image (Dropbox + Database)
	public void deleteImage(String filepath) throws SQLException, DbxException, IOException{
		// Delete file to dropbox
		boolean dbx_delete = dbx_api.dbx_delete_file(filepath);
		//boolean dbx_delete = true;
		
		// Database Row Deletion
		if(dbx_delete){
			try {
				Statement stmt = conn.createStatement();
				ResultSet media = stmt.executeQuery("SELECT * FROM media WHERE path='" + filepath + "';");
							
				if (media.next()){
					String query = "DELETE FROM media WHERE id = '" + media.getString("id") + "';";
					System.out.println(query);
					stmt.executeUpdate(query);
				}
			} 
			catch (SQLException e) {e.printStackTrace(); }
		}
	}
	
	// Get Media ID from Path
	public String getMediaID(String filepath){
		try {
			Statement stmt = conn.createStatement();
			ResultSet media = stmt.executeQuery("SELECT * FROM media WHERE path='" + filepath + "';");
						
			if (media.next())
				return media.getString("id");
		} 
		catch (SQLException e) {e.printStackTrace(); }
		return null;
	}
	
	// Get Media Path from ID
	public String getMediaPath(String media_id) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet media = stmt.executeQuery("SELECT * FROM media WHERE id='" + media_id + "';");
						
			if (media.next()){
				return media.getString("path");
			}
		} 
		catch (SQLException e) {e.printStackTrace(); }
		return null;
	}
	
	public static void main(String[] args) throws IOException, DbxException, SQLException {
		
		// Temp config init
		Config config = new Config(false);
		
		FileManager fm = new FileManager();
//		String filename = 
//		fm.uploadImage(1,"media/2.jpg", "/media/"+filename+".jpg");
//		fm.deleteImage("/media/"+filename+".jpg");
	}
	
}
