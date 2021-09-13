package com.pct.parser.utils;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Messages {

	public static Logger RECEIVED_REPORTS_LOGGER = Logger.getLogger("receivedReports");
	public static Logger LISTENER_EVENTS_LOGGER = Logger.getLogger("listenerEvents");
	public static Logger DB_REPORT_INSERTS_LOGGER = Logger.getLogger("dbReportInserts");
	public static Logger DB_RESPONSE_INSERTS_LOGGER = Logger.getLogger("dbResponseInserts");
	public static Logger LISTENER_ERRORS_LOGGER = Logger.getLogger("listenerErrors");
	public static Logger LISTENER_INFO_MESSAGES_LOGGER = Logger.getLogger("listenerInfo");
	public static Logger REDIS_DEVICE_UDP_ACCESS_LOGGER = Logger.getLogger("redisDeviceUDPAccess");
	public static Logger REDIS_DEVICE_STATUS_ACCESS_LOGGER = Logger.getLogger("redisDeviceStatusAccess");
	public static Logger FORWARDED_REPORTS_LOGGER = Logger.getLogger("forwardedReports");

	public static final String NEW_LINE = System.getProperty("line.separator").toString();
	public static Logger CONSOLE_LOGGER = Logger.getLogger("Zixy Logger");
	public static DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");

	static {
		String pattern = "%d{yyyy-MM-dd HH:mm:ss}";
		ConsoleAppender appender = new ConsoleAppender(new PatternLayout(pattern));
		CONSOLE_LOGGER.addAppender(appender);
		CONSOLE_LOGGER.setLevel(Level.ALL);
	}

	/**
	 * Redirects System.err and sends all data to a file.
	 *
	 */
	public static void RedirectSystemErr() {

		try {
			System.setErr(new PrintStream(new FileOutputStream("./logs/error.log")));
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Redirects System.err and sends all data to a file.
	 *
	 */
	public static void RedirectSystemOutput() {

		try {
			System.setOut(new PrintStream(new FileOutputStream("./logs/output.log")));
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Log message into a log4j file
	 * 
	 * @param str - String to log
	 */
	public static void LogReceivedRawReports(String str) {

		RECEIVED_REPORTS_LOGGER.log(Level.INFO, str);
	}

	/**
	 * Log message into a log4j file
	 * 
	 * @param str - String to log
	 */
	public static void LogListenerEventsToFile(String str) {

		Throwable throwable = new Throwable();
		StackTraceElement[] elements = throwable.getStackTrace();
		String className = ((StackTraceElement) elements[1]).getClassName();
		String methodName = ((StackTraceElement) elements[1]).getMethodName();
		int lineNumber = ((StackTraceElement) elements[1]).getLineNumber();
		String currentThread = Thread.currentThread().getName();
		if (str == null) {
			str = "";
		}
		String msg = " In: " + className + "." + methodName + " " + str;

		LISTENER_EVENTS_LOGGER.log(Level.INFO,
				com.pct.parser.utils.Messages.DATE_FORMAT.format(new Date()) + " " + str);
	}

	/**
	 * Log message into a log4j file
	 * 
	 * @param str - String to log
	 */
	public static void LogDBReportInsertsToFile(String str) {

		DB_REPORT_INSERTS_LOGGER.log(Level.INFO,
				com.pct.parser.utils.Messages.DATE_FORMAT.format(new Date()) + " " + str);
	}

	/**
	 * Log message into a log4j file
	 * 
	 * @param str - String to log
	 */
	public static void LogDBResponseInsertsToFile(String str) {

		DB_RESPONSE_INSERTS_LOGGER.log(Level.INFO,
				com.pct.parser.utils.Messages.DATE_FORMAT.format(new Date()) + " " + str);
	}

	/**
	 * Log message into a log4j file
	 * 
	 * @param str - String to log
	 */
	public static void LogListenerErrorsToFile(String str) {

		Throwable throwable = new Throwable();
		StackTraceElement[] elements = throwable.getStackTrace();
		String className = ((StackTraceElement) elements[1]).getClassName();
		String methodName = ((StackTraceElement) elements[1]).getMethodName();
		int lineNumber = ((StackTraceElement) elements[1]).getLineNumber();
		String currentThread = Thread.currentThread().getName();
		if (str == null) {
			str = "";
		}

		String msg = com.pct.parser.utils.Messages.DATE_FORMAT.format(new Date()) + " In: " + className + "."
				+ methodName + " " + str;

		LISTENER_ERRORS_LOGGER.log(Level.ERROR, msg);
	}

	/**
	 * Log message into a log4j file
	 * 
	 * @param str - String to log
	 */
	public static void LogListenerInfoMessagesToFile(String str) {

		LISTENER_INFO_MESSAGES_LOGGER.log(Level.INFO,
				com.pct.parser.utils.Messages.DATE_FORMAT.format(new Date()) + " " + str);
	}

	/**
	 * Log message into a log4j file
	 * 
	 * @param str - String to log
	 */
	public static void LogRedisDeviceUDPAccessToFile(String str) {

		REDIS_DEVICE_UDP_ACCESS_LOGGER.log(Level.INFO,
				com.pct.parser.utils.Messages.DATE_FORMAT.format(new Date()) + " " + str);
	}

	/**
	 * Log message into a log4j file
	 * 
	 * @param str - String to log
	 */
	public static void LogRedisDeviceStatusAccessToFile(String str) {

		REDIS_DEVICE_STATUS_ACCESS_LOGGER.log(Level.INFO,
				com.pct.parser.utils.Messages.DATE_FORMAT.format(new Date()) + " " + str);
	}

	/**
	 * Log message into a log4j file
	 * 
	 * @param str - String to log
	 */
	public static void LogForwardedRawReports(String str) {

		FORWARDED_REPORTS_LOGGER.log(Level.INFO, str);
	}

	/**
	 * Retrieve the exception info
	 * 
	 * @param e - Exception object
	 * @return - String with the exception info
	 */
	public static String GetExceptionInfo(Exception e) {
		try {
			return getExceptionInfo(e);
		} catch (Exception e1) {

		}
		StackTraceElement[] stack = e.getStackTrace();
		StringBuilder theTrace = new StringBuilder("Trace = ");
		for (StackTraceElement line : stack) {
			theTrace.append(line.toString());
		}
		return theTrace.toString();
	}

	public static String getExceptionInfo(Exception e) {
		String stack = ExceptionUtils.getStackTrace(e);
		StringBuilder theTrace = new StringBuilder("Trace = ");
		theTrace.append(stack);
		return theTrace.toString();
	}
}
