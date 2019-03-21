package utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Simple helper class to change the date 
 * into the correct format for writing to file
 * */
public class DateParser {
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");	
	private static DateParser d = new DateParser();
	private DateParser() {}
	
	public static DateParser getInstance() {
		return d;
	}
	
	public String getDateAsString(Date timestamp) {
		return format.format(timestamp);
	}
}


