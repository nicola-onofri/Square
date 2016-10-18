package pajc.config;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

//Library for generic methods
public class Utility {

	// Get time difference between a given date and the current time, converted
	// to hours or minutes
	public HashMap<String, Long> getRoundedTimeDifference(Date postDate) {
		HashMap<String, Long> returnValue = new HashMap<>();

		Date currentTime = new GregorianCalendar().getTime();
		long diffInMillis = currentTime.getTime() - postDate.getTime();

		// if difference in hours is less than 1 (= 0) then return the value
		// converted in minutes, otherwise return the value converted in hours
		if (TimeUnit.HOURS.convert(diffInMillis, TimeUnit.MILLISECONDS) == 0)
			returnValue.put(Vars.minutes_tag, TimeUnit.MINUTES.convert(diffInMillis, TimeUnit.MILLISECONDS));
		else
			returnValue.put(Vars.hours_tag, TimeUnit.HOURS.convert(diffInMillis, TimeUnit.MILLISECONDS));
		return returnValue;
	}

}
