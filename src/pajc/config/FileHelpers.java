package pajc.config;

import java.io.File;
import java.util.concurrent.atomic.AtomicLong;

public class FileHelpers {
	
	static String extension = Vars.JPG;
	
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
	
	// Generate Unique Atomic Number
	private static final AtomicLong LAST_TIME_MS = new AtomicLong();
	
	public static long uniqueCurrentTime() {
	    long now = System.currentTimeMillis();
	    while(true) {
	        long lastTime = LAST_TIME_MS.get();
	        if (lastTime >= now)
	            now = lastTime+1;
	        if (LAST_TIME_MS.compareAndSet(lastTime, now))
	            return now;
	    }
	}
	
	// Generate Unique Name (JPG)
	public static String getUniqueFileName(){
		return uniqueCurrentTime() + extension;
	}
	
}
