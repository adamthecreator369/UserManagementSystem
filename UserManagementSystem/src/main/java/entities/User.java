/* Created by Adam Jost on 07/08/2021 */

package main.java.entities;

public class User implements Comparable<User> {
	
	/**
	 * {ADMIN} is for a User with Administrator privileges and
	 * {REGULAR} is for a User without Administrator privileges. 
	 *
	 */
	public enum UserType {
		ADMIN,
		REGULAR
	}
	
	// Data fields
	private String name;
	private String username;
	private String password;
	private UserType userType; 
	
	// Constructor
	public User(String name, String username, String password, String userType) {
		this.username = username;
		this.password = password;
		this.userType = parseType(userType);
		this.name = formatName(name);
	}
	
	// Getters
	public String getUsername() { 
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public UserType getUserType() {
		return this.userType;
	}
	
	public String getName() {
		return this.name;
	}
	
	// Setters
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setUserType(UserType userType2) {
		this.userType = userType2;
	}
	
	public void setName(String name) {
		this.name = formatName(name);
	}
	
	// Class-member methods
	
	/**
	 * Parses the argument string and return the UserType.
	 * @param type: the type to be parsed.
	 * @return: the argument parsed to UserType.
	 */
	public UserType parseType(String type) {
		if (type.equals("ADMIN")) {
			return UserType.ADMIN;
		} else if (type.equals("REGULAR")) {
			return UserType.REGULAR;
		} else {
			throw new IllegalArgumentException(type + "is not a valid user type.");
		}
	}
	
	/**
	 * Formats a Users first and last name to ensure compatibility with the 
	 * data file. The desired format is the first letter of each name part to 
	 * be capitalized and all subsequent letters to be lower case. For instance, 
	 * billy bob will be formatted to Billy Bob. 
	 * 
	 * @param name: the name to be formated.
	 * @return: the formatted name. 
	 */
	private String formatName(String name) {
		// To prevent IndexOutOfBoundExceptions first do the
		// following. 
		if (name.replaceAll("[\\s\\p{Z}]", "").equals("")) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		String[] nameSections = name.split(" ");
		for (String section : nameSections) {
			sb.append(section.substring(0,1).toUpperCase() + section.substring(1).toLowerCase()).append(' ');
		}
		return sb.toString().trim();
	}
	
	/** Validates all fields of a newly created User and returns error messages for any issues
	 *  found. 
	 * @return: An empty String if no issues are found; a String detailing all format issues if 
	 * any are found. 
	 */
	public String validate() {
		
		StringBuilder sb = new StringBuilder();
		
		boolean hasWhitespace = false, hasLowercase = false, hasUppercase = false, 
				hasDigit = false, hasSymbol = false;
		
		
		if (getName().replaceAll("[\\s\\p{Z}]", "").equals("")) {
			sb.append("Name cannot be left blank.").append("\n");
		} else if (getName().split(" ").length < 2) {
			sb.append("Name must contain both first and last name.").append("\n");
		} 
		
		if (getUsername().length() < 6 || getUsername().length() > 12 ) {
			sb.append("Username must be between 6 and 12 characters long.").append("\n");
		}
		
		for (char c : getUsername().toCharArray()) {
			if (Character.isWhitespace(c)) {
				hasWhitespace = true;
			}
		}
		
		if (hasWhitespace) {
			sb.append("Username cannot contains spaces.").append("\n");
			hasWhitespace = false;
		}
		
		if (getPassword().replaceAll("[\\s\\p{Z}]", "").equals("")) {
			sb.append("Password field cannot be blank.").append("\n");
		} else if (getPassword().length() < 6) {
			sb.append("Password must be 6 characters or more.").append("\n");
		}
		
		for (char c : getPassword().toCharArray()) {
			if (Character.isLowerCase(c)) { hasLowercase = true; }
			if (Character.isUpperCase(c)) { hasUppercase = true; }
			if (Character.isWhitespace(c)) { hasWhitespace = true; }
			if (Character.isDigit(c)) { hasDigit = true; }
			if (c == '~' || c == '@' || c == '!' || c == '#' || c == '$' ||
					c == '%' || c == '^' || c == '&' || c == '*' || c == '-' ||
					c == '+' || c == '<' || c == '>' || c == '?' || c == '=' ||
					c == '|') {
				hasSymbol = true;
			}
		}
		
		if (!hasLowercase) {
			sb.append("Password must contain at least one lower case letter.").append("\n");
		}
		
		if (!hasUppercase) {
			sb.append("Password must contain at least one  uppercase letter.").append("\n");
		}
		
		if (hasWhitespace) {
			sb.append("Password cannot contain any spaces.").append("\n");
		}
		if (!hasDigit) {
			sb.append("Password must contain at least one number.").append("\n");
		}
		if (!hasSymbol) {
			sb.append("Password must contain at least one symbol.").append("\n");
			sb.append("Valid symbols include: \"~!@#$%^&*-+=|?<>\"").append("\n");
		}
		
		return sb.toString().trim();
	}
	
	/**
	 * Sets all fields of the user to the other's field's values.
	 */
	public void editData(User other) {
		setUsername(other.getUsername());
		setPassword(other.getPassword());
		setUserType(other.getUserType());
		setName(other.getName());
	}
	
	/**
	 * Returns the user objects information in String format matching that of the user data file.
	 * @return: the formatted string.
	 */
	public String toFileString() {
		return String.format("%s/%s/%s/%s\n", getName(), getUsername(), getPassword(), getUserType());
	}
	
	@Override 
	public String toString() {
		return String.format("Employee: %s\nUsername: %s\nPassword: %s\nUser Type: %s\n\n", 
				getName(), getUsername(), getPassword(), getUserType());
	}

	@Override
	public int compareTo(User other) {
		String[] name = getName().split(" "), otherName = other.getName().split(" ");
		if (name[0].compareTo(otherName[0]) == 0 && name.length > 1 && otherName.length > 1) { 
			return name[1].compareTo(otherName[1]);
		} else {
			return name[0].compareTo(otherName[0]);
		}
	}
}
