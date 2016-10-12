package pajc.config;

public class FileHelpers {
	
	// Get File Extension
	public static String getFileExtension(String file_name){
		String extension = "";
		int i = file_name.lastIndexOf('.');
		int p = Math.max(file_name.lastIndexOf('/'), file_name.lastIndexOf('\\'));
	
		if (i > p) {
		    extension = file_name.substring(i+1);
		}
		return extension;
	}
}
