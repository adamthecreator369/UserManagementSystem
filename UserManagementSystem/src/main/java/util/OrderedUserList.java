/* Created by Adam Jost on 07/08/2021 */

package main.java.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import main.java.entities.User;
import main.java.entities.User.UserType;

/**
 * 
 * @author adamjost
 *
 */
public class OrderedUserList {
	// reference to first node
	private Node head;

	public OrderedUserList() {
		head = null;
	}

	// Class for nodes
	static class Node {
		User user;
		Node next;

		Node(User user) {
			this.user = user;
			this.next = null;
		}

		public String fileString() {
			return user.toFileString();
		}
	}

	public void insert(User user) {
		Node newNode = new Node(user);
		Node current = head;
		Node previous = null;
		while (current != null && user.compareTo(current.user) >= 0) {
			previous = current;
			current = current.next;
		}
		// insertion at beginning of the list
		if (previous == null) {
			head = newNode;
		} else {
			previous.next = newNode;
		}
		// Applies to both cases
		newNode.next = current;
	}

	public Node remove(String username) {
		// Disallow the removal of the Administrator user.
		if (username.equalsIgnoreCase("Admin")) {
			return null;
		}
		
		if (head != null) {
			Node current = head;
			Node temp = null;
			if (current.user.getUsername().equals(username)) {
				temp = current;
				head = current.next;
				return temp;
			}
			
			while (current.next != null) {
				if (current.next.user.getUsername().equals(username)) {
					temp = current.next;
					current.next = current.next.next;
					return temp; 
				}
				current = current.next;
			}
		} 
		return null;
	}
	
	public User get(String name) {
		Node current = head;
		while (current != null) {
			if (current.user.getName().equalsIgnoreCase(name) || current.user.getUsername().equals(name)) {
				return current.user;
			}
			current = current.next;
		}
		return null;
	}
	
	
	public void edit(String username, User user) {
		Node current = head;
		while (current != null) {
			if (current.user.getUsername().equals(username)) {
				current.user.editData(user);
				return;
			}
			current = current.next;
		}
	}

	public String displayList() {
		StringBuilder sb = new StringBuilder();
		Node current = head;
		while (current != null) {
			sb.append(current.user);
			current = current.next;
		}
		return sb.toString();
	}
	
	public String displayAdmin() {
		StringBuilder sb = new StringBuilder();
		Node current = head;
		while (current != null) {
			if (current.user.getUserType() == UserType.ADMIN) {
				sb.append(current.user);
			}
			current = current.next;
		}
		return sb.toString();
	}
	
	public String displayReg() {
		StringBuilder sb = new StringBuilder();
		Node current = head;
		while (current != null) {
			if (current.user.getUserType() == UserType.REGULAR) {
				sb.append(current.user);
			}
			current = current.next;
		}
		return sb.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Node current = head;
		while (current != null) {
			sb.append(current.fileString());
			current = current.next;
		}
		return sb.toString();
	}
	
	public void print() throws IOException {
		FileOutputStream fout = new FileOutputStream("users.txt");
		PrintWriter writer = new PrintWriter(fout);
		writer.print(toString());
		writer.close();
		fout.close();
	}
}
