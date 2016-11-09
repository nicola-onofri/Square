package pajc.square.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import pajc.config.ColorPalette;
import pajc.config.Layout;
import pajc.square.model.User;

public class UserSettings extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel lblProfilePicture;
	private JLabel lblFixedUsername;
	private JLabel lblName;
	private JLabel lblUsername;
	private JLabel lblAboutMe;
	private JLabel lblPassword;
	private JTextArea txtrOldusername;
	private JTextArea txtrOldpassword;
	private JTextArea txtrOldname;
	private JTextArea txtrOldAboutMe;
	private JButton btnDeleteAccount;
	private JButton btnSaveChanges;

	public UserSettings(JFrame container, User user) {
		setBackground(ColorPalette.white_background);
		setLayout(null);
		setSize(container.getWidth(), container.getHeight());

		int container_maxSize = Math.max((getWidth() / 2) - Layout.component_margin * 2,
				getHeight() - Layout.component_margin * 2);

		lblProfilePicture = new JLabel();
		lblProfilePicture.setBounds(Layout.component_margin, Layout.component_margin, container_maxSize,
				container_maxSize);
		lblProfilePicture.setIcon(Layout.getScaledImage(user.getProfilePicture(), lblProfilePicture.getWidth(), lblProfilePicture.getWidth()));
		add(lblProfilePicture);

		lblFixedUsername = new JLabel("Display Username");
		lblFixedUsername.setFont(new Font("Droid Sans", Font.PLAIN, 24));
		lblFixedUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblFixedUsername.setBounds(125, 60, 249, 24);
		add(lblFixedUsername);

		lblName = new JLabel("Name");
		lblName.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setBounds(12, 123, 101, 15);
		add(lblName);

		lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBounds(12, 150, 101, 15);
		add(lblUsername);

		lblAboutMe = new JLabel("About Me");
		lblAboutMe.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		lblAboutMe.setHorizontalAlignment(SwingConstants.CENTER);
		lblAboutMe.setBounds(12, 204, 101, 15);
		add(lblAboutMe);

		lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setBounds(12, 177, 101, 15);
		add(lblPassword);

		txtrOldname = new JTextArea();
		txtrOldname.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		txtrOldname.setText("old_name");
		txtrOldname.setBounds(125, 123, 156, 15);
		add(txtrOldname);

		txtrOldusername = new JTextArea();
		txtrOldusername.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		txtrOldusername.setText("old_username");
		txtrOldusername.setBounds(125, 150, 156, 15);
		add(txtrOldusername);

		txtrOldpassword = new JTextArea();
		txtrOldpassword.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		txtrOldpassword.setText("old_password");
		txtrOldpassword.setBounds(125, 177, 156, 15);
		add(txtrOldpassword);

		btnSaveChanges = new JButton("Save changes");
		btnSaveChanges.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		btnSaveChanges.setBounds(63, 279, 187, 25);
		add(btnSaveChanges);

		txtrOldAboutMe = new JTextArea();
		txtrOldAboutMe.setText("old_about Me");
		txtrOldAboutMe.setBounds(125, 204, 436, 50);
		add(txtrOldAboutMe);

		btnDeleteAccount = new JButton("Delete Account");
		btnDeleteAccount.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		btnDeleteAccount.setBounds(288, 279, 187, 25);
		add(btnDeleteAccount);
	}
}
