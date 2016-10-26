package pajc.square.logAuth;

// Include the Dropbox SDK.
import com.dropbox.core.*;
import java.io.*;
import java.util.Locale;

public class DropboxAPI {

	final String APP_KEY = "m576rqmc6o7pssr";
	final String APP_SECRET = "p8825uchprchc7r";
	final String ACCESS_TOKEN = "6ieF-1wWF-oAAAAAAAC1xWWyEWSylb4Kk4EC5otkp_rcCVYQJOKEMCE8A1kYBPiZ";
	DbxClient dbx_client;
	DbxAppInfo appInfo;

	// Init Dropbox Client
	public DropboxAPI() throws DbxException, IOException {
		DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
		DbxRequestConfig config = new DbxRequestConfig("PAJC Square/1.0", Locale.getDefault().toString());

		// Dbx Client Init
		DbxClient client = new DbxClient(config, ACCESS_TOKEN);
		this.dbx_client = client;
		this.appInfo = appInfo;
	}

	// Upload file to Dropbox
	public boolean dbx_upload_file(String filename, String filepath) throws DbxException, IOException {
		File inputFile = new File(filename);
		FileInputStream inputStream = new FileInputStream(inputFile);
		try {
			DbxEntry.File uploadedFile = dbx_client.uploadFile(filepath, DbxWriteMode.force(), inputFile.length(),
					inputStream);
			System.out.println("Uploaded: " + uploadedFile.toString());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			inputStream.close();
		}
	}

	// Delete file on Dropbox
	public boolean dbx_delete_file(String filepath) {
		try {
			dbx_client.delete(filepath);
			System.out.println("File deleted: " + filepath);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// List files in folder
	public void dbx_list_files(String folderpath) throws DbxException {

		DbxEntry.WithChildren listing = dbx_client.getMetadataWithChildren(folderpath);
		System.out.println("Files in " + folderpath + ":");
		for (DbxEntry child : listing.children) {
			System.out.println("	" + child.name + ": " + child.toString());
		}
	}

	// Download file from Dropbox
	public void dbx_download_file(String filename, String filepath) throws DbxException, IOException {
		FileOutputStream outputStream = new FileOutputStream(filename);
		try {
			DbxEntry.File downloadedFile = dbx_client.getFile(filepath, null, outputStream);
			System.out.println("Metadata: " + downloadedFile.toString());
		} finally {
			outputStream.close();
		}
	}
}