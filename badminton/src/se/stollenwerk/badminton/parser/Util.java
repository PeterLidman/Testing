package se.stollenwerk.badminton.parser;

import java.util.Calendar;
import java.util.TimeZone;

public class Util {
	
	/**
	 * This method takes year, month and day as integers and returns a long (time in ms).
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static long dateToLong(int year, int month, int day) {
		Calendar c = Calendar.getInstance(TimeZone.getDefault());
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month-1);
		c.set(Calendar.DAY_OF_MONTH, day);
		return c.getTimeInMillis();
	}
	
	/**
	 * This method takes a long and returnes the year, month and day as integers
	 * <p>
	 * @param time
	 * @return int[3] where int[0] = year, int[1] = month and int[2] = day
	 */
	public static int[] longToDate(long time) {
		Calendar c = Calendar.getInstance(TimeZone.getDefault());
		c.setTimeInMillis(time);
		int[] retTime = new int[3];
		retTime[0] = c.get(Calendar.YEAR);
		retTime[1] = c.get(Calendar.MONTH);
		retTime[2] = c.get(Calendar.DAY_OF_MONTH);
		return retTime;
	}
	
	/**
	 * This method parses dates in format dd/mm-yyyy
	 * <p>
	 * @param txt
	 * @return
	 */
	public static long parseYearLast(String txt) {
		int index = txt.indexOf("-");
		int year = 2000 + Integer.valueOf(txt.substring(index + 1, txt.length()));
		txt = txt.substring(0, index);
		index = txt.indexOf("/");
		int day = Integer.valueOf(txt.substring(0, index));
		int month = Integer.valueOf(txt.substring(index + 1, txt.length()));
		return Util.dateToLong(year, month, day);
	}
	
	/**
	 * This method parses dates in format yyyy-mm-dd
	 * <p>
	 * @param txt
	 * @return
	 */
	public static long parseYearFirst(String txt) {
		int index = txt.indexOf("-");
		int year = Integer.valueOf(txt.substring(0,  index));
		txt = txt.substring(index + 1, txt.length());
		index = txt.indexOf("-");
		int month = Integer.valueOf(txt.substring(0, index)) + 1;
		int day = Integer.valueOf(txt.substring(index + 1, txt.length()));
		return Util.dateToLong(year, month, day);
	}
	
	/**
	 * This method takes a date and returns a long (time in ms)
	 * <p>
	 * @param txt
	 * @return
	 */
	public static long getDate(String txt) {
		int index = txt.indexOf(" ");
		if(index != -1) {
			txt = txt.substring(0, index);
		}
		if(txt.contains("/")) {
			return Util.parseYearLast(txt);
		} else {
			return Util.parseYearFirst(txt);
		}
	}	
}
