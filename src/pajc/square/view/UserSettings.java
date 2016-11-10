package pajc.square.view;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import pajc.config.ColorPalette;
import pajc.config.DataValidation;
import pajc.config.Layout;
import pajc.config.RoundedImage;
import pajc.config.Vars;
import pajc.square.model.User;

public class UserSettings extends JPanel {
	private static final long serialVersionUID = 1L;
	private Font titleFont;
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

		titleFont = new Font("Droid Sans", Font.PLAIN, 24);

		int container_maxSize = Math.max((getWidth() / 2) - Layout.component_margin * 2,
				getHeight() - Layout.component_margin * 2);

		// TODO onClick -> open SharePost-like view
		lblProfilePicture = new JLabel();
		lblProfilePicture.setBounds(Layout.component_margin, Layout.component_margin, container_maxSize,
				container_maxSize);
		lblProfilePicture.setIcon(RoundedImage.createRoundedImage(Layout.getScaledImage(user.getProfilePicture(),
				lblProfilePicture.getWidth(), lblProfilePicture.getWidth())));
		add(lblProfilePicture);

		// Fix Frame Maximum X
		int container_max_X = container.getWidth() - Layout.component_margin;

		// Fix Component Alignment
		int component_alignment_X = lblProfilePicture.getX() + lblProfilePicture.getWidth() + (Layout.component_margin);

		lblFixedUsername = new JLabel(user.getUsername());
		lblFixedUsername.setFont(titleFont);
		lblFixedUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblFixedUsername.setBounds(component_alignment_X, lblProfilePicture.getY(),
				container_max_X - component_alignment_X, titleFont.getSize());
		add(lblFixedUsername);

		lblName = new JLabel("Name");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setBounds(component_alignment_X,
				lblFixedUsername.getY() + lblFixedUsername.getHeight() + Layout.component_margin,
				Layout.label_default_width, Layout.label_default_height);
		add(lblName);

		// Field: change name
		txtrOldname = new JTextArea();
		txtrOldname.setBounds(lblName.getX() + lblName.getWidth() + Layout.component_margin, lblName.getY(), 200,
				Layout.label_default_height);
		add(txtrOldname);

		lblUsername = new JLabel("Username");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBounds(component_alignment_X, lblName.getY() + lblName.getHeight() + Layout.component_margin,
				Layout.label_default_width, Layout.label_default_height);
		add(lblUsername);

		// Field: change username
		txtrOldusername = new JTextArea();
		DataValidation.deleteTextOnFocusGained(txtrOldusername, user.getName());
		txtrOldusername.setBounds(lblUsername.getX() + lblUsername.getWidth() + Layout.component_margin,
				lblUsername.getY(), 200, Layout.label_default_height);
		add(txtrOldusername);

		lblAboutMe = new JLabel("About Me");
		lblAboutMe.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		lblAboutMe.setHorizontalAlignment(SwingConstants.CENTER);
		lblAboutMe.setBounds(component_alignment_X,
				lblUsername.getY() + lblUsername.getHeight() + Layout.component_margin, Layout.label_default_width,
				Layout.label_default_height);
		add(lblAboutMe);

		// Field: change about_me description
		txtrOldAboutMe = new JTextArea();
		DataValidation.deleteTextOnFocusGained(txtrOldAboutMe, user.getAbout());
		txtrOldAboutMe.setBounds(lblAboutMe.getX() + lblAboutMe.getWidth() + Layout.component_margin, lblAboutMe.getY(),
				300, Layout.label_default_height);
		add(txtrOldAboutMe);

		lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setBounds(component_alignment_X,
				lblAboutMe.getY() + lblAboutMe.getHeight() + Layout.component_margin, Layout.label_default_width,
				Layout.label_default_height);
		add(lblPassword);

		// Field: change password
		// TODO add button to toggle show/hide password
		txtrOldpassword = new JTextArea();
		DataValidation.deleteTextOnFocusGained(txtrOldpassword, user.getPassword());
		txtrOldpassword.setBounds(lblPassword.getX() + lblPassword.getWidth() + Layout.component_margin,
				lblPassword.getY(), 100, Layout.label_default_height);
		add(txtrOldpassword);

		// Apply changes: send to DB + fireChanges
		btnSaveChanges = new JButton("Save changes");
		btnSaveChanges.setBounds(component_alignment_X,
				getHeight() - Layout.component_margin * 3 - Layout.button_default_height,
				(getWidth() - component_alignment_X) / 2 - Layout.component_margin, Layout.button_default_height);
		add(btnSaveChanges);

		btnSaveChanges.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO do something
			}
		});

		// Delete: confirmation + delete from DB + fireChanges + logOut
		btnDeleteAccount = new JButton("Delete Account");
		btnDeleteAccount.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		btnDeleteAccount.setBackground(ColorPalette.red_button);
		btnDeleteAccount.setBounds(btnSaveChanges.getX() + btnSaveChanges.getWidth() + Layout.component_margin,
				getHeight() - Layout.component_margin * 3 - Layout.button_default_height,
				(getWidth() - component_alignment_X) / 2 - Layout.component_margin, Layout.button_default_height);
		add(btnDeleteAccount);

		btnDeleteAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String input = JOptionPane.showInputDialog(null, Vars.authentication_required);

				while (!input.equals(user.getPassword())) {
					try {
						input = JOptionPane.showInputDialog(null, Vars.authentication_failed);
					} catch (NullPointerException npe) {
						// Avoid annoying exceptions
					}
				}
			}
		});
	}
}
