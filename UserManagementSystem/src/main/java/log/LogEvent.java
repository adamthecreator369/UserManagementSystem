/* Created by Adam Jost on 07/15/2021 */

package main.java.log;

import java.text.SimpleDateFormat;
import java.util.Date;

import main.java.users.User;

public class LogEvent {
	
	/**
	 * The type of LogEvent being performed. A {LOGIN} 
	 * occurs when a user logs into the system and {LOGOUT} 
	 * occurs when a user logs out of the system.
	 */
	public enum LogEventType {
		LOGIN,
		LOGOUT
	}

	// Data fields
	private String username;
	private LogEventType eventType; 
	private String timeDate;
	
	// Constructor
	public LogEvent(String username, String eventType, String timeDate) {
		this.username = username;
		this.eventType = parseEventType(eventType);
		if (timeDate.equals("current time")) {
			this.timeDate = formatDate();
		} else {
			this.timeDate = timeDate;
		}
	}
	
	/**
	 * Parses a String to LogEventType.
	 * 
	 * @param type: the String to be parsed.
	 * @return: the parsed LogEventType.
	 */
	private LogEventType parseEventType(String type) {
		if (type.equalsIgnoreCase("LOGIN")) {
			return LogEventType.LOGIN;
		} else if (type.equalsIgnoreCase("LOGOUT")) {
			return LogEventType.LOGOUT;
		}
		// Will only reach this point source file is manually edited. 
		throw new IllegalArgumentException("Unsupported event type.");
	}
	
	/** Formats a String to the desired SimpleDateFormat.
	 * 
	 * @return: the formatted date.
	 */
	private String formatDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("E, MMM dd yyyy, hh:mm:ss a");
        Date now = new Date(); 
		return sdf.format(now); 
	}
	
	/** 
	 * Returns a LogEvent in desired format for the log file.
	 * 
	 * @return: the formatted LogEvent in String format.
	 */
	public String toFileString() {
		return String.format("%s/%s/%s\n", username, eventType, timeDate);
	}
	
	@Override
	public String toString() {
		return String.format("Username: %s\nEvent: %s\nDate/Time: %s\n\n",username, eventType, timeDate);
	}

	/**
	 * Compares the username member of a LogEvent to a User's username.
	 * 
	 * @param user: the user to be compared to.
	 * @return: {true} if the usernames are equal; {false} otherwise.
	 */
	public boolean compareTo(User user) {
		return user.getUsername().compareTo(username) == 0;
	}
	
}
