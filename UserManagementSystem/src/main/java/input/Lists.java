package main.java.input;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import main.java.log.LogEvent;
import main.java.log.LogList;
import main.java.users.OrderedUserList;
import main.java.users.User;

public class Lists {
	
	public static Object[] get() throws IOException {
		// Input streams (Used as a database of all saved users)
		FileInputStream fin = new FileInputStream("users.txt");
		Scanner scanner = new Scanner(fin);
		// List to store all users.
		OrderedUserList userList = new OrderedUserList();
		// Populate list with user data from input stream.
		while (scanner.hasNext()) {
			String[] data = scanner.nextLine().split("/");
			userList.insert(new User(data[0], data[1], data[2], data[3]));
		}
		// Close open streams.
		scanner.close();
		fin.close();
		
		// Read in all activity log data. 
		fin = new FileInputStream("log.txt");
		scanner = new Scanner(fin);
		// List containing all log activity. 
		LogList logList = new LogList();
		
		while (scanner.hasNext()) {
			String[] data = scanner.nextLine().split("/");
			logList.add(new LogEvent(data[0], data[1], data[2]));
		}
		
		// Close streams.
		scanner.close();
		fin.close();
		
		return new Object[] { userList, logList };
	}
}
