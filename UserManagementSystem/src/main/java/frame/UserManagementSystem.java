/* Created by Adam Jost on 07/15/2021 */

package main.java.frame;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;

import main.java.input.Lists;
import main.java.layouts.MainLayout;
import main.java.layouts.LoginLayout;
import main.java.layouts.SubLayoutOne;
import main.java.layouts.SubLayoutTwo;
import main.java.log.LogEvent;
import main.java.log.LogList;
import main.java.users.OrderedUserList;
import main.java.users.User;
import main.java.users.User.UserType;
import main.resources.R;

public class UserManagementSystem {
	
	// Data fields
	public static User currentUser;
	private Object[] lists;
	private OrderedUserList userList;
	private LogList logList;
	private Frame frame = new Frame();
	private MainLayout mainLayout;
	private LoginLayout loginLayout;
	private SubLayoutOne layout1;
	private SubLayoutTwo layout2;
	private User userToEdit;

	// Constructor
	public UserManagementSystem() {
		// Attempt to read in and retrieve
		// data used for all operations.
		try {
			lists = Lists.get();
			this.userList = (OrderedUserList) lists[0];
			this.logList = (LogList) lists[1];
		} catch (IOException e) {
			lists = null;
		}
		// Render the frame. 
		render();
	}

	/** Constructs and shows the Frame  */
	public void render() {
		
		// Instantiate all layouts and show those
		// to be shown at time of rendering. 
		instantiateLayouts();
		
		// Listen for click events and perform set actions for any
		// button when they are clicked by the user.
		performClickActions();

		// Frame settings
		setFrameSpecs();

		// Window exited
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				// Print the user list to the data file.
				// This saves any changes made by the user
				// while using the management system.
				try {
					userList.print();
					if (currentUser != null) {
						logList.add(new LogEvent(currentUser.getUsername(), "LOGOUT", "current time"));
					}
					logList.print();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// Destroy/close the Frame.
				frame.dispose();
			}
		});
		
		// Check if data was available and read-in. 
		// Close window after outputting an error message
		// if either of the data sources were not found. 
		checkDataAvailabilty(lists);
	}
	
	/** Instantiates all layouts used in the Frame */
	private void instantiateLayouts() {
		// Layout with components common to all other layouts.
		mainLayout = new MainLayout(userList);
		mainLayout.addComponents(frame);
		mainLayout.show();
		
		// Layout used for logging in to the system.
		loginLayout = new LoginLayout();
		loginLayout.addComponents(frame);
		loginLayout.show();

		// Layout used for Creating & Editing Users.
		layout1 = new SubLayoutOne();
		layout1.addComponents(frame);
	
		// Layout used for Deleting Users & Finding User To Edit.
		layout2 = new SubLayoutTwo();
		layout2.addComponents(frame);
	}

	/** Sets the Frame's specifications and settings. */
	private void setFrameSpecs() {
		frame.setLocation(340, 120);
		frame.setSize(R.dimens.app_width, R.dimens.app_height);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setTitle(R.string.app_name);
		frame.setBackground(R.color.app_color);
		frame.setVisible(true);
	}

	/** Pool of click listeners for all buttons contained in all layouts. */
	private void performClickActions() {
		
		// Login Layout Click Listener for the login button.
		loginLayout.getBtn().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainLayout.removeMsgIfExists();
				String username = loginLayout.getField1().getText().trim();
				String password = loginLayout.getField2().getText().trim();
				
				if (username.replaceAll("[\\s\\p{Z}]", "").equals("")) {
					mainLayout.getTxtArea().setText("* Error: Username cannot be blank.");
				}
				String areaTxt = mainLayout.getTxtArea().getText();
				if (password.replaceAll("[\\s\\p{Z}]", "").equals("")) {
					if (areaTxt.length() > 0) {
						mainLayout.getTxtArea().setText(areaTxt + "\n* Error: Password cannot be blank.");
					} else {
						mainLayout.getTxtArea().setText("* Error: Password cannot be blank.");
					}
				}
				
				if (mainLayout.msgShowing()) {
					return;
				}
				
				User user = userList.get(username);

				if (user == null) {
					mainLayout.getTxtArea().setText("* Error: User does not exist in our system.");
					return;
				}
				
				if (user.getPassword().equals(password)) {
					loginLayout.hide();
					currentUser = user;
					mainLayout.getCurrentUserLabel().setText(R.string.logged_in_as + user.getName());
					if (currentUser.getUserType().equals(UserType.ADMIN)) {
						mainLayout.enableBtns();
					} else {
						mainLayout.enableRegularUserBtns();
					}
					logList.add(new LogEvent(user.getUsername(), "LOGIN", "current time"));
				} else {
					mainLayout.getTxtArea().setText("* Error: Password does not match our records for " + user.getName());
				}
				
			}
		});

		// Common Layout Button Click Listeners (all layout buttons)
		for (JButton btn : mainLayout.getBtns()) {
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainLayout.removeMsgIfExists();
					String text = "";

					// Cast sender of event from object to JButton
					// since we know that is what it is and what we
					// need it to be.
					JButton sender = (JButton) e.getSource();

					// If it is an operation button then disable it
					// and enable the other three operation buttons
					// Operation buttons includes the Create, Edit, Delete,
					// and Search buttons.
					if (currentUser.getUserType().equals(UserType.ADMIN)) {
						mainLayout.disableCurrentOperationBtn(sender);
					}

					if (sender.equals(mainLayout.getLrgBtn1())) {
						layout1.show(R.string.create_user, R.string.create_user);
						layout2.hide();
						userToEdit = null;
					} else if (sender.equals(mainLayout.getLrgBtn2())) {
						layout1.hide();
						if (currentUser.getUserType().equals(UserType.ADMIN)) {
							layout2.show(R.string.edit_user, R.string.find_user); 
							layout1.enableComboBox();
						} else {
							layout1.show(R.string.edit_user, R.string.edit_user);
							layout1.displayUserInfo(currentUser);
							layout1.disableComboBox();
						}
						userToEdit = null;
					} else if (sender.equals(mainLayout.getLrgBtn3())) {
						layout1.hide();
						layout2.show(R.string.delete_user, R.string.delete_user); // find user
						userToEdit = null;
					} else if (sender.equals(mainLayout.getLrgBtn4())) {
						layout1.hide();
						layout2.show(R.string.search_user, R.string.search); // User search
						userToEdit = null;
					} else if (sender.equals(mainLayout.getTxtAreaBtn1())) {
						text = userList.displayList();
						if (text.equals("")) {
							mainLayout.getTxtArea().setText("* Error: The are currently no users to display.");
						}
					} else if (sender.equals(mainLayout.getTxtAreaBtn2())) {
						text = userList.displayAdmin();
						if (text.equals("")) {
							mainLayout.getTxtArea().setText("* Error: The are currently no admin users to display.");
						}
					} else if (sender.equals(mainLayout.getTxtAreaBtn3())) {
						text = userList.displayReg();
						if (text.equals("")) {
							mainLayout.getTxtArea().setText("* Error: The are currently no regular users to display.");
						}
					} else if (sender.equals(mainLayout.getTxtAreaBtn4())) {
						if (currentUser.getUserType().equals(UserType.ADMIN)) {
							text = logList.displayList();
							if (text.equals("")) {
								mainLayout.getTxtArea().setText("* Error: Nothing to display. Data file is empty.");
							}
						} else {
							text = logList.getUserActivity(currentUser);
							if (text.equals("")) {
								mainLayout.getTxtArea().setText("* Error: Nothing to display. You have no recorded log activity.");
							}
						}
					} else {
						layout1.hide();
						layout2.hide();
						mainLayout.logout();
						loginLayout.show();
						logList.add(new LogEvent(currentUser.getUsername(), "LOGOUT", "current time"));
						currentUser = null;
					}

					if (!text.equals(""))
						mainLayout.getTxtArea().setText(text);
				}
			});
		}

		// Create and Edit User Layout Button Click Listeners (two top right buttons)
		for (JButton btn : layout1.getBtns()) {
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainLayout.removeMsgIfExists();
					// Cast sender of event from object to JButton
					// since we know that is what it is and what we
					// need it to be.
					JButton sender = (JButton) e.getSource();

					if (sender.equals(layout1.getTopRtBtn1())) {
						String[] arr = layout1.getUserInput();
						User newUser = new User(arr[0], arr[1], arr[2], arr[3]);

						if (newUser.validate().equals("")) {
							if (userToEdit != null) {
								userList.remove(userToEdit.getUsername());
							} else {
								userList.remove(currentUser.getUsername());
							}
							if (sender.getText().equals(R.string.edit_user)) {
								mainLayout.getTxtArea()
										.setText("* Success: User has been edited successfully.\n\n" + newUser);
								layout1.hide();
								if (currentUser.getUserType().equals(UserType.ADMIN)) {
									layout2.show(R.string.edit_user, R.string.find_user);
								}
								// If a user has edited their own information then force a log out
								// and prompt them to log back in. This ensures current user and log information is
								// accurate after the edit has been completed. 
								if (userToEdit == null || userToEdit != null && currentUser.compareTo(userToEdit) == 0) {
									layout1.hide();
									layout2.hide();
									mainLayout.logout();
									loginLayout.show();
									logList.add(new LogEvent(currentUser.getUsername(), "LOGOUT", "current time"));
									currentUser = null;
									mainLayout.getTxtArea().setText("* Success: Your user information has been successfully saved.\n"
											+ "* Please log back in to continue.");
								}
							} else {
								if (userList.get(newUser.getName()) != null
										|| userList.get(newUser.getUsername()) != null) {
									mainLayout.getTxtArea()
											.setText("" + "* Error: Duplicate users cannot be added to the system.\n"
													+ newUser.validate());
									return;
								}
								mainLayout.getTxtArea()
										.setText("" + "* Success: User has been added successfully.\n\n" + newUser);
								layout1.reset();
							}
						} else {
							mainLayout.getTxtArea()
									.setText("" + "* Error: Fix the following error(s).\n" + newUser.validate());
							return;
						}
						userList.insert(newUser);
						
						userToEdit = null;

					} else if (sender.equals(layout1.getTopRtBtn2())) { // Reset button
						if (layout1.getTopRtBtn1().getText().equals(R.string.create_user)) {
							layout1.reset();
						} else if (layout1.getTopRtBtn1().getText().equals(R.string.edit_user)) {
							if (currentUser.getUserType().equals(UserType.ADMIN)) {
								layout1.displayUserInfo(userToEdit);
							} else {
								layout1.displayUserInfo(currentUser);
							}
						}
						mainLayout.removeMsgIfExists();
					}
				}
			});
		}

		// Layout 2 Click Listeners  (Find User for Editing, Delete User, and User Search)
		layout2.getBtn().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainLayout.removeMsgIfExists();

				String name = layout2.getText().trim();
				if (name.replaceAll("[\\s\\p{Z}]", "").equals("")) {
					mainLayout.getTxtArea().setText("* Error: Name field cannot be left blank.\n"
							+ "* Enter the person's full name or username to continue.");
					return;
				} else if (name.replaceAll("[\\s\\p{Z}]", "").equalsIgnoreCase("Admin")
						|| name.replaceAll("[\\s\\p{Z}]", "").equalsIgnoreCase("Administrator")) {
					if (layout2.getBtn().getText().equals(R.string.delete_user)) {
						mainLayout.getTxtArea().setText("* Error: The generic Administrator user cannot be deleted.");
						return;
					} else if (layout2.getBtn().getText().equals(R.string.find_user)) {
						mainLayout.getTxtArea().setText("* Error: The generic Administrator user cannot be edited.");
						return;
					}
					
				}

				userToEdit = userList.get(name);

				if (userToEdit == null) {
					mainLayout.getTxtArea().setText("* Error: That user does not exist in our system.");
					return;
				}

				if (layout2.getBtn().getText().equals(R.string.find_user)) {
					layout2.hide();
					layout1.show(R.string.edit_user, R.string.edit_user);
					layout1.displayUserInfo(userToEdit);
				} else if (layout2.getBtn().getText().equals(R.string.delete_user)) {
					userList.remove(name);
					layout2.reset();
					mainLayout.getTxtArea().setText("* Success: User " + name + " has been deleted.");
				} else if (layout2.getBtn().getText().equals(R.string.search)) {
					layout2.reset();
					String userActivity = logList.getUserActivity(userToEdit);
					if (userActivity.equals("")) {
						mainLayout.getTxtArea().setText("--------------\nUser Information\n--------------\n\n" 
								+ userToEdit.toString() + "\n----------\nActivity Log\n----------\n\n" 
								+ userToEdit.getName() + " has no recorded activity.");
					} else {
						mainLayout.getTxtArea().setText("--------------\nUser Information\n--------------\n\n" 
								+ userToEdit.toString() + "\n----------\nActivity Log\n----------\n\n" 
								+ userActivity);
					}
				}
			}
		});
		
	}
	
	/** Check the availability of the input data. If either input data source was 
	 * missing the system will print an error message to the text area and then 
	 * proceed to automatically dispose of the Frame after 10 seconds.
	 * 
	 * @param lists: The retrieved lists that will contain all user and log data if
	 * they were read-in successfully from the input files. If input files were not
	 * found then list will be set to null. 
	 */
	private void checkDataAvailabilty(Object[] lists) {
		// If one of the data files are missing or cannot be found. 
		if (lists == null) {
			// Do nothing and leave error message in place.
			mainLayout.getTxtArea().setText("* Error: System is down. Missing data records.\n\n"
					+ "*** System will close in 10 seconds ***");
			// Pause for 10 seconds.
			try {
				Thread.sleep(10000); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			// Close the frame.
			frame.dispose();
		}
	}
}
