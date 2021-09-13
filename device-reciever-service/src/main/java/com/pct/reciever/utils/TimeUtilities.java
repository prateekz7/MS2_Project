package com.pct.reciever.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TimeUtilities {

	public static final String UTC = "UTC";
	public static final String EARLIEST_DATE = "01/01/1970";
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	public static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	public static final int ERROR_YEAR = 1972;
	public static final int ERROR_MONTH = 1;
	public static final int ERROR_DAY = 2;
	public static final int MIN_YEAR = 1971;
	public static final int MIN_MONTH = 1;
	public static final int MIN_DAY = 2;
	public static final int NULL_YEAR = 1970;
	public static final int NULL_MONTH = 1;
	public static final int NULL_DAY = 2;
	public static final SimpleDateFormat simpleDateFormatPST = new SimpleDateFormat("MM/dd/yyyy");
	public static final SimpleDateFormat simpleTimeFormatPST = new SimpleDateFormat("HH:mm:ss");
	public static final SimpleDateFormat simpleDateTimeFormatPST = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	public static final SimpleDateFormat simpleDateTimeFormatUTC = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	public static final TimeZone timeZonePST = TimeZone.getTimeZone("America/Los_Angeles");
	public static TimeZone timeZone = TimeZone.getTimeZone("PST");
	public static TimeZone timeZoneUTC = TimeZone.getTimeZone("UTC");
	static {
		simpleTimeFormatPST.setTimeZone(TimeUtilities.timeZonePST);
		simpleDateFormatPST.setTimeZone(TimeUtilities.timeZonePST);
		simpleDateTimeFormatUTC.setTimeZone(timeZoneUTC);
	}

	/**
	 * Helper method creating java.sql.Date object from a date String and a time
	 * String
	 * 
	 * @param dateStr - String containing date only
	 * @param timeStr - String containing time only
	 * @return - java.sql.Date object
	 * @throws ParseException
	 */
	public static java.sql.Timestamp GetTimestamp(String dateStr, String timeStr) throws ParseException {

		if (((dateStr != null) && (!dateStr.isEmpty())) || ((timeStr != null) && (!timeStr.isEmpty()))) {

			java.util.Date date;
			if ((dateStr != null) && (!dateStr.isEmpty())) {
				date = dateFormat.parse(dateStr);
			} else {
				date = new Date();
			}
			Calendar calendar1 = GregorianCalendar.getInstance();
			calendar1.setTime(date);

			if ((timeStr != null) && (!timeStr.isEmpty())) {
				java.util.Date time = timeFormat.parse(timeStr);

				Calendar calendar2 = GregorianCalendar.getInstance();
				calendar2.setTime(time);
				int hours = calendar2.get(Calendar.HOUR_OF_DAY);
				int minutes = calendar2.get(Calendar.MINUTE);

				calendar1.set(Calendar.HOUR_OF_DAY, hours);
				calendar1.set(Calendar.MINUTE, minutes);
				calendar1.set(Calendar.SECOND, 0);
			}
			java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar1.getTime().getTime());
			timestamp.setNanos(0);
			return timestamp;
		} else {
			return null;
		}
	}

	public static class DateTimeStrings {

		public String date;
		public String time;

		public DateTimeStrings(Timestamp timestamp) {
			if (timestamp != null) {
				java.util.Date d = new java.util.Date(timestamp.getTime());
				date = dateFormat.format(d);
				time = timeFormat.format(d);
			} else {
				date = null;
				time = null;
			}
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}
	}

	/**
	 *
	 * @param dateTimeInfo - DateTimeInfo object containing year,month,etc... If the
	 *                     input is null a "null" date is created.
	 * @return java.util.Date with the created date
	 */
	public static java.util.Date CreateDate(DateTimeInfo dateTimeInfo) {
		if (dateTimeInfo == null) {
			dateTimeInfo = new DateTimeInfo(); // with Null year...
		}
		return CreateDate(dateTimeInfo.year, dateTimeInfo.month, dateTimeInfo.day, dateTimeInfo.hour,
				dateTimeInfo.minutes, dateTimeInfo.seconds);
	}

	/**
	 * Helper method creating date from input with restrictions related to the
	 * current date and time and the "beginning date and time (1970...)
	 * 
	 * @param year    - int for the year
	 * @param month   - int for the month
	 * @param day     - int for the day
	 * @param hour    - int for the hour
	 * @param minutes - int for the minutes
	 * @param seconds - int for the seconds
	 * @return - java.utilDate object
	 */
	public static java.util.Date CreateDate(int year, int month, int day, int hour, int minutes, int seconds) {

		Calendar calendar = GregorianCalendar.getInstance();
		Date now = new Date();
		calendar.setTime(now);
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth = calendar.get(Calendar.MONTH) + 1; // Java month starts at zero not one
		int nowDay = calendar.get(Calendar.DAY_OF_MONTH);

		if ((year <= NULL_YEAR)) {
//            this.GPSTimestampPST = null;
			// can't have null for Timestamp in MySQL because of the JDBC driver used by
			// Amazon
			// MySQL RDS which has problem with NULL.
			year = NULL_YEAR;
			month = NULL_MONTH;
			day = NULL_DAY;
			hour = 0;
			minutes = 0;
			seconds = 0;
		} else if ((year <= MIN_YEAR) || (year > nowYear) || (month <= 0) || (month > nowMonth) || (day <= 0)
				|| (day > (nowDay + 1))) {
//            this.GPSTimestampPST = null;
			// can't have null for Timestamp in MySQL because of the JDBC driver used by
			// Amazon
			// MySQL RDS which has problem with NULL.
			year = MIN_YEAR;
			month = MIN_MONTH;
			day = MIN_DAY;
			hour = 0;
			minutes = 0;
			seconds = 0;
		} else {
			calendar.set(year, month - 1, day);
			int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			if (day > daysInMonth) {
				year = ERROR_YEAR;
				month = ERROR_MONTH;
				day = ERROR_DAY;
				hour = 0;
				minutes = 0;
				seconds = 0;
			}
		}

		month--; // Java calendar counts month from zero!!! January = 0

		calendar = GregorianCalendar.getInstance();
		calendar.set(year, month, day, hour, minutes, seconds);
		calendar.set(Calendar.MILLISECOND, 0);
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		calendar.setTimeZone(timeZone);
		Date date = calendar.getTime();

		return date;
	}

	/**
	 * Helper method creating date from input with restrictions related to the
	 * current date and time and the "beginning date and time (1970...)
	 * 
	 * @param year    - int for the year
	 * @param month   - int for the month
	 * @param day     - int for the day
	 * @param hour    - int for the hour
	 * @param minutes - int for the minutes
	 * @param seconds - int for the seconds
	 * @return - java.utilDate object
	 */
	public static java.util.Date CreateDate(int year, int month, int day, int hour, int minutes, int seconds,
			String tZone) {

		Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone(tZone));
		Date now = new Date();
		calendar.setTime(now);
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth = calendar.get(Calendar.MONTH) + 1; // Java month starts at zero not one
		int nowDay = calendar.get(Calendar.DAY_OF_MONTH);

		if ((year <= NULL_YEAR)) {
//            this.GPSTimestampPST = null;
			// can't have null for Timestamp in MySQL because of the JDBC driver used by
			// Amazon
			// MySQL RDS which has problem with NULL.
			year = NULL_YEAR;
			month = NULL_MONTH;
			day = NULL_DAY;
			hour = 0;
			minutes = 0;
			seconds = 0;
		} else if ((year <= MIN_YEAR) || (year > nowYear) || (month <= 0) || ((month > nowMonth) && (year >= nowYear))
				|| (day <= 0) || ((day > (nowDay + 1)) && (month >= nowMonth && (year >= nowYear)))) {
//            this.GPSTimestampPST = null;
			// can't have null for Timestamp in MySQL because of the JDBC driver used by
			// Amazon
			// MySQL RDS which has problem with NULL.
			year = MIN_YEAR;
			month = MIN_MONTH;
			day = MIN_DAY;
			hour = 0;
			minutes = 0;
			seconds = 0;
		} else {
			calendar.set(year, month - 1, day);
			int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			if (day > daysInMonth) {
				year = ERROR_YEAR;
				month = ERROR_MONTH;
				day = ERROR_DAY;
				hour = 0;
				minutes = 0;
				seconds = 0;
			}
		}

		month--; // Java calendar counts month from zero!!! January = 0

		calendar = GregorianCalendar.getInstance();
		calendar.set(year, month, day, hour, minutes, seconds);
		calendar.set(Calendar.MILLISECOND, 0);
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		calendar.setTimeZone(timeZone);
		Date date = calendar.getTime();

		return date;
	}

	public static Timestamp GetNullTimestamp() {

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(NULL_YEAR, NULL_MONTH - 1, NULL_DAY, 0, 0, 0);
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		calendar.setTimeZone(timeZone);
		Date date = calendar.getTime();
		return new Timestamp(date.getTime());
	}

	public static String GetNullTimestampSTR() {

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(NULL_YEAR, NULL_MONTH - 1, NULL_DAY, 0, 0, 0);
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		calendar.setTimeZone(timeZone);
		Date date = calendar.getTime();
		return simpleDateTimeFormatPST.format(date);
	}

	public static Timestamp GetMinTimestamp() {

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(MIN_YEAR, MIN_MONTH - 1, MIN_DAY, 0, 0, 0);
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		calendar.setTimeZone(timeZone);
		Date date = calendar.getTime();
		return new Timestamp(date.getTime());
	}

	public static String GetMinTimestampSTR() {

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(MIN_YEAR, MIN_MONTH - 1, MIN_DAY, 0, 0, 0);
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		calendar.setTimeZone(timeZone);
		Date date = calendar.getTime();
		return simpleDateTimeFormatPST.format(date);
	}

	public static Timestamp GetErrorTimestamp() {

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(ERROR_YEAR, ERROR_MONTH - 1, ERROR_DAY, 0, 0, 0);
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		calendar.setTimeZone(timeZone);
		Date date = calendar.getTime();
		return new Timestamp(date.getTime());
	}

	public static String GetErrorTimestampSTR() {

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(ERROR_YEAR, ERROR_MONTH - 1, ERROR_DAY, 0, 0, 0);
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		calendar.setTimeZone(timeZone);
		Date date = calendar.getTime();
		return simpleDateTimeFormatPST.format(date);
	}

	public static Timestamp GetZeroTimestamp() {

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(2000, 0, 1, 0, 0, 0);
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		calendar.setTimeZone(timeZone);
		Date date = calendar.getTime();
		return new Timestamp(date.getTime());
	}

	public static String GetZeroTimestampSTR() {

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(2000, 0, 1, 0, 0, 0);
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		calendar.setTimeZone(timeZone);
		Date date = calendar.getTime();
		return simpleDateTimeFormatPST.format(date);
	}

	/**
	 * Class for encapsulating time range
	 */
	public static class TimeRange {

		java.sql.Timestamp earliestTime;
		java.sql.Timestamp latestTime;

		public TimeRange(java.sql.Timestamp earliestTime, java.sql.Timestamp latestTime) {
			this.earliestTime = earliestTime;
			this.latestTime = latestTime;
		}

		public Timestamp getEarliestTime() {
			return earliestTime;
		}

		public void setEarliestTime(Timestamp earliestTime) {
			this.earliestTime = earliestTime;
		}

		public Timestamp getLatestTime() {
			return latestTime;
		}

		public void setLatestTime(Timestamp latestTime) {
			this.latestTime = latestTime;
		}
	}

	/**
	 * Helper method creating a time range base on timestamp input and days and/or
	 * hours before or after.
	 * 
	 * @param numHours  - int for number of hours
	 * @param numDays   - int for number of days
	 * @param before    - boolean: true if time range before the givien timestamp
	 *                  otherwise false
	 * @param timestamp - java.sql.Timestamp object with the start or end time point
	 * @return - TimeRange object
	 */
	public static TimeRange GetTimeRange(int numHours, int numDays, boolean before, java.sql.Timestamp timestamp) {

		java.util.Date date;
		java.sql.Timestamp timestamp1;
		if (timestamp == null) {
			date = new java.util.Date();
			timestamp1 = new java.sql.Timestamp(date.getTime());
		} else {
			timestamp1 = timestamp;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(timestamp1);

		int subtractHours = Math.abs(numHours);
		int subtractDays = Math.abs(numDays);

		// subtractHours = Math.min(subtractHours, 24);
		if (before) {
			subtractHours = -subtractHours;
			subtractDays = -subtractDays;
		}
		calendar.add(Calendar.HOUR_OF_DAY, subtractHours);
		calendar.add(Calendar.DAY_OF_MONTH, subtractDays);

		java.sql.Timestamp timestamp2 = new Timestamp(calendar.getTime().getTime());

		TimeRange tr;

		if (before) {
			tr = new TimeRange(timestamp2, timestamp1);
		} else {
			tr = new TimeRange(timestamp1, timestamp2);
		}

		return tr;
	}

	/**
	 * Helper method providing a SQL date object created from a date String or
	 * "NULL" dtae
	 * 
	 * @param dateSTR  - String containing date
	 * @param earliest - boolean indicating whether to retun NULL date in case of
	 *                 ann error with the date String
	 * @return - java.sql.Date object
	 */
	public static java.sql.Date GetSQLDate(String dateSTR, boolean earliest) {

		java.util.Date date = null;
		java.sql.Date sqlDate;
		try {
			date = dateFormat.parse(dateSTR);
		} catch (Exception e) {
			if (earliest) {
				try {
					date = dateFormat.parse(EARLIEST_DATE);
				} catch (Exception e1) {
				}
			} else {
				date = new java.util.Date();
			}
		}
		sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}

	/**
	 * Class encapsulated dat-time info: year, month, day, hour, minutes and seconds
	 */
	public static class DateTimeInfo {

		public int year = NULL_YEAR;
		public int month = NULL_MONTH;
		public int day = NULL_DAY;
		public int hour = 0;
		public int minutes = 0;
		public int seconds = 0;

		public int getYear() {
			return year;
		}

		public void setYear(int year) {
			this.year = year;
		}

		public int getMonth() {
			return month;
		}

		public void setMonth(int month) {
			this.month = month;
		}

		public int getDay() {
			return day;
		}

		public void setDay(int day) {
			this.day = day;
		}

		public int getHour() {
			return hour;
		}

		public void setHour(int hour) {
			this.hour = hour;
		}

		public int getMinutes() {
			return minutes;
		}

		public void setMinutes(int minutes) {
			this.minutes = minutes;
		}

		public int getSeconds() {
			return seconds;
		}

		public void setSeconds(int seconds) {
			this.seconds = seconds;
		}
	}

	public static String toISO8601UTC(Date date) {
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		df.setTimeZone(tz);
		return df.format(date);
	}

	public static String toISO8601UTCWithFormat(Date date) {
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(tz);
		return df.format(date) + ".000000000";
	}

	public static Date fromISO8601UTC(String dateStr) {
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		df.setTimeZone(tz);

		try {
			return df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

}
