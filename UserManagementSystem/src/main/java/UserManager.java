/* Created by Adam Jost on 07/15/2021 */

package main.java;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import main.java.entities.LogEvent;
import main.java.entities.User;
import main.java.frame.UserFrame;
import main.java.util.LogList;
import main.java.util.OrderedUserList;

public class UserManager {
	public static void main(String[] args) throws IOException {
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
		
		// Open the user management window.
		new UserFrame(userList, logList);
	}
}
