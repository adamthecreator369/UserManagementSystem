package main.java.log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import main.java.users.User;

public class LogList {
	
	// Data field
	private ArrayList<LogEvent> logEvents;
	
	// Constructor
	public LogList() {
		logEvents = new ArrayList<>();
	}
	
	/** Adds an item to the LogList */
	public void add(LogEvent event) {
		logEvents.add(event);
	}
	
	/**
	 * Gets and returns all log activity for a particular user.
	 * @param user: the user whose activity is being retrieved.
	 * @return: the users log activity.
	 */
	public String getUserActivity(User user) {
		StringBuilder sb = new StringBuilder();
		for (LogEvent event : logEvents) {
			if (event.compareTo(user)) {
				sb.append(event);
			}
		}
		return sb.toString();
	}
	
	/** 
	 * Creates and returns the LogList in String format.
	 * @return: the LogList in String format.
	 */
	public String displayList() {
		StringBuilder sb = new StringBuilder();
		for (LogEvent event : logEvents) {
			sb.append(event);
		}
		return sb.toString();
	}
	
	@Override 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (LogEvent event : logEvents) {
			sb.append(event.toFileString());
		}
		return sb.toString();
	}
	
	/** Prints the LogList in its current state to an output file */
	public void print() throws IOException {
		FileOutputStream fout = new FileOutputStream("log.txt");
		PrintWriter writer = new PrintWriter(fout);
		writer.print(toString());
		writer.close();
		fout.close();
	}
}
