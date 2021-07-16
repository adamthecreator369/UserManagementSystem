/* Created by Adam Jost on 07/14/2021 */

package main.java.layouts;

import java.awt.Font;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.java.entities.User;
import main.java.entities.User.UserType;
import main.resources.R;


public class SubLayoutOne extends Layout {

	private JLabel titleLabel;
	private JLabel fieldLabel1, fieldLabel2, fieldLabel3, fieldLabel4;

	private JTextField field1, field2, field3; 
	private JComboBox<String> comboBox;
	private JButton topRtBtn1, topRtBtn2;

	private JComponent[] components, inputComponents;

	private JLabel[] nonTitleLabels;

	private JButton[] btns;

	public SubLayoutOne() {
		instantiateComponents();
		componentSettings();
		hide();
	}

	public JComboBox<String> getComboBox() {
		return comboBox;
	}

	private JTextField getField1() {
		return field1;
	}

	private JTextField getField2() {
		return field2;
	}

	private JTextField getField3() {
		return field3;
	}

	public JButton getTopRtBtn1() {
		return topRtBtn1;
	}

	public JButton getTopRtBtn2() {
		return topRtBtn2;
	}

	@Override
	public void instantiateComponents() {
		titleLabel = new JLabel(R.string.create_user);
		fieldLabel1 = new JLabel(R.string.full_name_label);
		fieldLabel2 = new JLabel(R.string.username_label);
		fieldLabel3 = new JLabel(R.string.password_label);
		fieldLabel4 = new JLabel(R.string.user_type_label);
		field1 = new JTextField();
		field2 = new JTextField();
		field3 = new JTextField();
		comboBox = new JComboBox<>(R.string.combo_options);
		topRtBtn1 = new JButton();
		topRtBtn2 = new JButton(R.string.reset_btn);
		nonTitleLabels = new JLabel[] { fieldLabel1, fieldLabel2, fieldLabel3, fieldLabel4 };
		inputComponents = new JComponent[] { field1, field2, field3, comboBox };
		btns = new JButton[] { topRtBtn1, topRtBtn2 };
		components = new JComponent[] { titleLabel, fieldLabel1, fieldLabel2, fieldLabel3, fieldLabel4, field1, field2,
				field3, comboBox, topRtBtn1, topRtBtn2 };
	}

	private String getField1Txt() {
		return getField1().getText();
	}

	private String getField2Txt() {
		return getField2().getText();
	}

	private String getField3Txt() {
		return getField3().getText();
	}

	private JComponent[] getInputComponents() {
		return inputComponents;
	}
	
	private String getComboSelection() {
		return getComboBox().getSelectedIndex() == 0 ? "ADMIN" : "REGULAR";
	}
	
	public JButton[] getBtns() {
		return btns;
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
			if (i == inputComponents.length - 1) {
				label_y += 5;
			}
			inputComponents[i].setBounds(R.dimens.field_x, label_y, R.dimens.field_width, R.dimens.field_height);
		}

		topRtBtn1.setBounds(R.dimens.top_btn_base_x, R.dimens.top_btn_y, R.dimens.small_btn_width,
				R.dimens.small_btn_height);
		topRtBtn2.setBounds(R.dimens.top_btn_base_x + R.dimens.view_btn_diff, R.dimens.top_btn_y,
				R.dimens.small_btn_width, R.dimens.small_btn_height);

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
		// All buttons.
		for (JButton btn : btns) {
			btn.setFont(new Font("Serif", Font.ROMAN_BASELINE, 16));
		}
	}

	public void addComponents(Frame frame) {
		for (JComponent jc : components) {
			frame.add(jc);
		}
	}

	public void show(String titleText, String btnText) {
		reset();
		show();
		titleLabel.setText(titleText);
		topRtBtn1.setText(btnText);
	}

	@Override
	public void hide() {
		// Set visibility of all components to false.
		for (JComponent jc : components) {
			jc.setVisible(false);
		}
		reset();
	}

	public void reset() {
		for (int i = 0; i < getInputComponents().length; i++) {
			if (i != getInputComponents().length - 1) {
				JTextField field = (JTextField) getInputComponents()[i];
				field.setText(null);
			} else {
				@SuppressWarnings("unchecked")
				JComboBox<String> box = (JComboBox<String>) getInputComponents()[i];
				box.setSelectedIndex(0);
			}
		}
	}

	public void displayUserInfo(User user) {
		field1.setText(user.getName());
		field2.setText(user.getUsername());
		field3.setText(user.getPassword());
		if (user.getUserType().equals(UserType.ADMIN)) {
			comboBox.setSelectedIndex(0);
		} else {
			comboBox.setSelectedIndex(1);
		}
	}

	public String[] getUserInput() {
		return new String[] { getField1Txt(), getField2Txt(), getField3Txt(), getComboSelection() };
	}

	@Override
	public void show() {
		for (JComponent jc : components) {
			jc.setVisible(true);
		}
	}

}
