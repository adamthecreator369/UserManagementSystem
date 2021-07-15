package main.java.layouts;

import java.awt.Font;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import main.java.util.OrderedUserList;
import main.resources.R;

public class MainLayout extends Layout {
	
	// Data fields
	private JButton lrgBtn1, lrgBtn2, lrgBtn3, lrgBtn4;
	private JButton txtAreaBtn1, txtAreaBtn2, txtAreaBtn3, txtAreaBtn4;
	private JButton bottomRtBtn;
	private JTextArea txtArea;
	private JScrollPane scrollPane;
	private JComponent[] components;
	private JButton[] btns, lrgBtns, txtAreaBtns;
	private JLabel currentUserLabel;

	// Constructor
	public MainLayout(OrderedUserList userList) {
		instantiateComponents();
		componentSettings();
		disableBtns();
	}

	// Getters 
	public JButton getLrgBtn1() { return lrgBtn1; }
	public JButton getLrgBtn2() { return lrgBtn2; }
	public JButton getLrgBtn3() { return lrgBtn3; }
	public JButton getLrgBtn4() { return lrgBtn4; }
	public JButton getTxtAreaBtn1() { return txtAreaBtn1; }
	public JButton getTxtAreaBtn2() { return txtAreaBtn2; }
	public JButton getTxtAreaBtn3() { return txtAreaBtn3; }
	public JButton getTxtAreaBtn4() { return txtAreaBtn4; }
	public JButton getBottomRtBtn() { return bottomRtBtn; }
	public JTextArea getTxtArea() { return txtArea; }
	public JButton[] getBtns() { return btns; }
	public JLabel getCurrentUserLabel() { return currentUserLabel; }

	/** Disables all buttons of the layout */
	private void disableBtns() {
		for (JButton btn : btns) {
			btn.setEnabled(false);
		}
	}
	
	/** Enables only the buttons allowed to be accessed by a 
	 * user that is of the UserType.REGULAR type.
	 */
	public void enableRegularUserBtns() {
		getTxtAreaBtn4().setEnabled(true);
		bottomRtBtn.setEnabled(true);
		getLrgBtn2().setEnabled(true);
	}
	
	/** Enables all text area buttons */
	public void enableTxtAreaBtns() {
		for (JButton btn : txtAreaBtns) {
			btn.setEnabled(true);
		}
	}

	/** Enables all buttons contained in the layout. */
	public void enableBtns() {
		for (JButton btn : btns) {
			btn.setEnabled(true);
		}
	}
	
	/**
	 * Disables an operation button when clicked and enables all other 
	 * large operation buttons. 
	 * 
	 * @param sender: the button that was clicked and is to be disabled.
	 */
	public void disableCurrentOperationBtn(JButton sender) {
		// Do nothing if the sender was a text area button.
		for (JButton btn : txtAreaBtns) {
			if (btn == sender) {
				return;
			}
		}
		// Otherwise enable all large buttons except for the
		// sender which we need to disable.
		for (JButton btn : lrgBtns) {
			if (btn == sender) {
				btn.setEnabled(false);
			} else {
				btn.setEnabled(true);
			}
		}
	}

	/**
	 * Checks if a message is currently showing in the text area.
	 * 
	 * @return: {true} if a message is found, {false} otherwise.
	 */
	public boolean msgShowing() {
		try {
			if (getTxtArea().getText().charAt(0) == '*') {
				return true;
			}
		} catch (StringIndexOutOfBoundsException e) {
			return false;
		}
		return false;
	}

	/** Removes an error or success message from the text area if one is found */
	public void removeMsgIfExists() {
		if (msgShowing()) { getTxtArea().setText(null); } 
	}
	
	/** Calls activity to be done when a user logout occurs. */
	public void logout() {
		disableBtns();
		txtArea.setText(null);
		currentUserLabel.setText(R.string.not_logged_in);
	}
	
	/** Calls activity to be done when a user login occurs */
	public void login() {
		enableBtns();
	}

	@Override
	public void instantiateComponents() {
		lrgBtn1 = new JButton(R.string.create_btn);
		lrgBtn2 = new JButton(R.string.edit_btn);
		lrgBtn3 = new JButton(R.string.delete_btn);
		lrgBtn4 = new JButton(R.string.search_btn);
		txtAreaBtn1 = new JButton(R.string.view_all_users);
		txtAreaBtn2 = new JButton(R.string.view_admin_users);
		txtAreaBtn3 = new JButton(R.string.view_reg_users);
		txtAreaBtn4 = new JButton(R.string.activity_log);
		bottomRtBtn = new JButton(R.string.logout_btn);
		txtArea = new JTextArea();
		txtArea.setBounds(R.dimens.txt_area_x, R.dimens.txt_area_y, R.dimens.txt_area_width, R.dimens.txt_area_height);
		scrollPane = new JScrollPane(txtArea);
		currentUserLabel = new JLabel(R.string.not_logged_in);
		btns = new JButton[] { lrgBtn1, lrgBtn2, lrgBtn3, lrgBtn4, txtAreaBtn1, txtAreaBtn2, txtAreaBtn3, txtAreaBtn4,
				bottomRtBtn };
		lrgBtns = new JButton[] { lrgBtn1, lrgBtn2, lrgBtn3, lrgBtn4 };
		txtAreaBtns = new JButton[] { txtAreaBtn1, txtAreaBtn2, txtAreaBtn3, txtAreaBtn4 };
		components = new JComponent[] { lrgBtn1, lrgBtn2, lrgBtn3, lrgBtn4, txtAreaBtn1, txtAreaBtn2, txtAreaBtn3,
				txtAreaBtn4, bottomRtBtn, scrollPane, currentUserLabel };
	}
	
	@Override
	public void setFont() {
		for (JButton btn : btns) {
			btn.setFont(new Font("Serif", Font.ROMAN_BASELINE, 16));
		}
		currentUserLabel.setFont(new Font("Serif", Font.ROMAN_BASELINE, 19));
	}
	
	@Override
	public void addComponents(Frame frame) {
		for (JComponent jc : components) {
			frame.add(jc);
		}
	}

	@Override
	public void show() {
		for (JComponent jc : components) {
			jc.setVisible(true);
		}
		getTxtArea().setVisible(true);

	}

	@Override
	public void hide() {
		for (JComponent jc : components) {
			jc.setVisible(false);
		}
	}

	@Override
	public void componentSettings() {
		int lrgBtnY = R.dimens.lrg_btn_base_y;
		
		for (int i=0; i < lrgBtns.length; i++, lrgBtnY = i==0 ? lrgBtnY : lrgBtnY + R.dimens.lBtnDiffY) {
			lrgBtns[i].setBounds(R.dimens.lrg_btn_x, lrgBtnY, R.dimens.lrg_btn_width, R.dimens.lrg_btn_height);
		}
		
		int viewBtnX = R.dimens.view_btn_x_base;
		
		for (int i=0; i<txtAreaBtns.length; i++, viewBtnX = i==0 ? viewBtnX : viewBtnX + R.dimens.view_btn_diff) {
			txtAreaBtns[i].setBounds(viewBtnX, R.dimens.view_btn_y, R.dimens.small_btn_width,
					R.dimens.small_btn_height);
		}
		
		bottomRtBtn.setBounds(R.dimens.btm_rt_btn_x, R.dimens.btm_rt_btn_y, R.dimens.small_btn_width,
				R.dimens.small_btn_height);

		

		txtArea.setEditable(false);

		scrollPane.setBounds(R.dimens.txt_area_x, R.dimens.txt_area_y, R.dimens.txt_area_width,
				R.dimens.txt_area_height);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		currentUserLabel.setBounds(R.dimens.current_user_x, R.dimens.current_user_y, R.dimens.current_user_width,
				R.dimens.current_user_height);
		
		currentUserLabel.setForeground(R.color.current_user_label);
		setFont();
	}

	

}
