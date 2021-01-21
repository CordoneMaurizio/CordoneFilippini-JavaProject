package wvUtilità;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.time.Instant;
import java.util.Date;

public class UnixConverter {
	
	
	
	public Long timeToUnix(String date) {
		long dataLeggibile = 0L;
		try {
			DateFormat d = DateFormat.getDateInstance(DateFormat.SHORT);
			Date dd = d.parse(date);
			dataLeggibile = (Long) dd.getTime()/1000L;
	    }catch(ParseException e) {
	    	e.printStackTrace();
	    }
		return dataLeggibile;
	}
	
	public String unixToDate(Long date) {
		long unixSeconds = 1372339860;
		Date unixDate = new Date(unixSeconds); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		return sdf.format(unixDate);
		
	}
}
