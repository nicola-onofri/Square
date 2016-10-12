package pajc.media;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicLong;

import com.dropbox.core.DbxException;

import pajc.config.Config;
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
	
	// Unique Timestamp Generator
	private static final AtomicLong LAST_TIME_MS = new AtomicLong();
	public static long uniqueCurrentTimeMS() {
	    long now = System.currentTimeMillis();
	    while(true) {
	        long lastTime = LAST_TIME_MS.get();
	        if (lastTime >= now)
	            now = lastTime+1;
	        if (LAST_TIME_MS.compareAndSet(lastTime, now))
	            return now;
	    }
	}
	
	// Upload Image (Dropbox + Database)
	public void uploadImage(String filename, String upload_path) throws SQLException, DbxException, IOException{
		// Upload file to dropbox
		boolean dbx_up = dbx_api.dbx_upload_file(filename, upload_path);
		
		// If Upload is successful insert query to db
		if(dbx_up){
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO media VALUES (null,'" + filename + "','" + upload_path +"');";
			stmt.executeUpdate(query);
		}
	}
	
	// Delete Image (Dropbox + Database)
	public void deleteImage(String filepath) throws SQLException, DbxException, IOException{
		// Delete file to dropbox
		//boolean dbx_delete = dbx_api.dbx_delete_file(filepath);
		
		boolean dbx_delete = true;
		
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
	
	public static void main(String[] args) throws IOException, DbxException, SQLException {
		
		// Temp config init
		Config config = new Config(false);
		
		FileManager fm = new FileManager();
		String filename = String.valueOf(uniqueCurrentTimeMS());
		//fm.uploadImage("media/2.jpg", "/media/"+filename+".jpg");
		
		fm.deleteImage("/media/1475832125309.jpg");
			
	}
	
}
