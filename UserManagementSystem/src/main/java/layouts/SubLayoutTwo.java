/* Created by Adam Jost on 07/14/2021 */

package main.java.layouts;

import java.awt.Font;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.resources.R;

public class SubLayoutTwo extends Layout {
	
	// TODO:: Add notice to find user by name or username.
	// Enter the name or username of the user you wish to edit. 

	private JLabel titleLabel, fieldLabel;
	private JTextField field;
	private JButton btn;
	private JComponent[] components;

	// Constructor
	public SubLayoutTwo() {
		instantiateComponents();
		componentSettings();
		hide();
	}
	
	// Getters
	public JButton getBtn() {
		return btn;
	}
	
	public String getText() {
		return field.getText();
	}

	@Override
	public void instantiateComponents() {
		titleLabel = new JLabel();
		fieldLabel = new JLabel(R.string.name_label);
		field = new JTextField();
		btn = new JButton();
		components = new JComponent[] { titleLabel, fieldLabel, field, btn };
	}

	@Override
	public void componentSettings() {
		titleLabel.setBounds(R.dimens.title_label_x, R.dimens.title_label_y, R.dimens.title_label_width,
				R.dimens.title_label_height);
		fieldLabel.setBounds(R.dimens.label_x, R.dimens.label_y, R.dimens.label_width + 50, R.dimens.label_height);
		field.setBounds(R.dimens.subLayout1_field_x, R.dimens.label_y, R.dimens.field_width, R.dimens.field_height);
		btn.setBounds(R.dimens.subLayout1_btn_x, R.dimens.subLayout_btn1_y, R.dimens.small_btn_width,
				R.dimens.small_btn_height);
		setFont();
	}

	@Override
	public void setFont() {
		// Operation title
		titleLabel.setFont(new Font("Serif", Font.ROMAN_BASELINE, 28));
		// Non-title label
		fieldLabel.setFont(new Font("Serif", Font.PLAIN, 18));
		// Button
		btn.setFont(new Font("Serif", Font.ROMAN_BASELINE, 16));
	}

	/** Adds all layout components to a Frame */
	public void addComponents(Frame frame) {
		for (JComponent jc : components) {
			frame.add(jc);
		}
	}
	

	/**
	 * Displays the layout to the GUI.
	 * 
	 * @param titleTxt: the text for the Title of the layout.
	 * @param labelTxt: the text for the label of the text field.
	 * @param btnTxt: the text for the button.
	 */
	public void show(String titleTxt, String btnTxt) {
		reset();
		show();
		titleLabel.setText(titleTxt);
		btn.setText(btnTxt);
	}

	/** Hides the the layout from the GUI */
	public void hide() {
		for (JComponent jc : components) {
			jc.setVisible(false);
		}
		reset();
	}

	/** Removes any text contained within the text field */
	public void reset() {
		field.setText(null);
	}
	
	@Override
	public void show() {
		for (JComponent jc : components) {
			jc.setVisible(true);
		} 
	}
}
