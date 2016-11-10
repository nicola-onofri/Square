package pajc.config;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

public class DataValidation {
	// Email Validation
	public static boolean emailValidation(String email) {
		String email_regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Boolean valid = email.matches(email_regex);
		return valid;
	}

	// Delete Text on Focus Gain
	public static void deleteTextOnFocusGained(JTextComponent component, String defaultText) {
		component.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (component.getText().equals("")) {
					component.setText(defaultText);
					component.setForeground(Color.LIGHT_GRAY);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (component.getText().equals(defaultText))
					component.setText("");
			}
		});
	}

	// Limit text length in a JTextField
	// TODO user can easily cheat by typing a non char key! FIX
	public static void limitCharInTxtArea(JTextArea field, JLabel countLabel, int maxLength) {
		// Update label for each key typed
		field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (field.getText().length() == maxLength) {
					field.setText(new StringBuilder(field.getText()).deleteCharAt(maxLength - 1).toString());
					return;
				}
				countLabel.setText(String.valueOf(maxLength - field.getText().length()));
			}
		});
	}
}
