package main.java.layouts;

import java.awt.Font;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

import javax.swing.JTextField;

import main.resources.R;

/**
 * Creates and displays a sub-layout.
 * 
 * @author: Adam Jost
 * @category: Layout
 */
public class LoginLayout extends Layout {

	// Data fields
	private JLabel titleLabel;
	private JLabel fieldLabel1, fieldLabel2;

	private JTextField field1, field2;

	private JButton btn;

	private JComponent[] components, inputComponents;

	private JLabel[] nonTitleLabels;

	// Constructor
	public LoginLayout() {
		instantiateComponents();
		componentSettings();
	}

	// Getters 
	public JTextField getField1() {
		return field1;
	}

	public JTextField getField2() {
		return field2;
	}

	public JButton getBtn() {
		return btn;
	}

	private JComponent[] getInputComponents() {
		return inputComponents;
	}

	@Override
	public void instantiateComponents() {
		titleLabel = new JLabel(R.string.login_btn);
		fieldLabel1 = new JLabel(R.string.username_label);
		fieldLabel2 = new JLabel(R.string.password_label);
		field1 = new JTextField();
		field2 = new JTextField();
		btn = new JButton(R.string.login_btn);
		nonTitleLabels = new JLabel[] { fieldLabel1, fieldLabel2 };
		inputComponents = new JComponent[] { field1, field2 };
		components = new JComponent[] { titleLabel, fieldLabel1, fieldLabel2, field1, field2, btn };
	}

	
	@Override
	public void componentSettings() {
		titleLabel.setBounds(R.dimens.title_label_x, R.dimens.title_label_y, R.dimens.title_label_width,
				R.dimens.title_label_height);
		int label_y = R.dimens.label_y;
		for (int i = 0; i < nonTitleLabels.length; i++, label_y = i == 0 ? label_y : label_y + R.dimens.label_y_diff) {
			nonTitleLabels[i].setBounds(R.dimens.label_x, label_y, R.dimens.label_width, R.dimens.label_height);
		}
		label_y = R.dimens.label_y;
		for (int i = 0; i < inputComponents.length; i++, label_y = i == 0 ? label_y : label_y + R.dimens.label_y_diff) {
			inputComponents[i].setBounds(R.dimens.field_x, label_y, R.dimens.field_width, R.dimens.field_height);
		}

		btn.setBounds(R.dimens.login_btn_x, R.dimens.login_btn_y, R.dimens.small_btn_width,
				R.dimens.small_btn_height);

		setFont();
	}

	@Override
	public void setFont() {
		// Operation title.
		titleLabel.setFont(new Font("Serif", Font.ROMAN_BASELINE, 28));
		// All non-title labels
		for (JLabel component : nonTitleLabels) {
			component.setFont(new Font("Serif", Font.PLAIN, 18));
		}
		btn.setFont(new Font("Serif", Font.ROMAN_BASELINE, 16));

	}
	
	@Override
	public void addComponents(Frame frame) {
		for (JComponent jc : components) {
			frame.add(jc);
		}
	}
	
	@Override
	public void show() {
		// Set visibility of all component to true;
		for (JComponent jc : components) {
			jc.setVisible(true);
		}
	}
	
	
	@Override
	public void hide() {
		// Set visibility of all components to false.
		for (JComponent jc : components) {
			jc.setVisible(false);
		}
		reset();
	}

	/** Removes any text contained within the LoginLayout's text fields. */
	public void reset() {
		for (int i = 0; i < getInputComponents().length; i++) {
			JTextField field = (JTextField) getInputComponents()[i];
			field.setText(null);

		}
	}

}
