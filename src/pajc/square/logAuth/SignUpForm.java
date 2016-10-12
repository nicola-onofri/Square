package pajc.square.logAuth;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import pajc.config.ColorPalette;
import pajc.config.DataValidation;
import pajc.config.Layout;
import pajc.config.Vars;
import pajc.square.appView.AvatarUpload;
import pajc.square.model.User;

public class SignUpForm extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField txtName;
	private JTextField txtUsername;
	private JTextField txtEmail;
	private JPasswordField pwdPassword;
	private JLabel lblSignUp;
	private JTextArea txtrAboutYou;
	private JButton btnPasswordVisible;
	private JButton btnSignup;
	private JLabel lblCountChar;

	// Gradient Background
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		int w = getWidth();
		int h = getHeight();
		GradientPaint gp = new GradientPaint(0, 0, ColorPalette.light_blue_background, 0, h, ColorPalette.blue_background);
		g2d.setPaint(gp);
		g2d.fillRect(0, 0, w, h);
	}

	public SignUpForm() {

		setLayout(null);

		// Logo
		ImageIcon logo_image = new ImageIcon("res/square_logo_light.png");
		JLabel lbl_logo = new JLabel(logo_image);
		lbl_logo.setBounds(180, 27, 220, 130);
		add(lbl_logo);

		// Sign Up Label
		lblSignUp = new JLabel("Sign Up to see photos and videos from your friends");
		lblSignUp.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignUp.setForeground(Color.WHITE);
		lblSignUp.setBounds(70, 150, 460, 30);
		add(lblSignUp);

		// 1a Col
		// Name Field
		txtName = new JTextField(Vars.placeholder_name);
		txtName.setForeground(Color.WHITE);
		txtName.setOpaque(false);
		txtName.setBackground(new Color(255, 255, 255, Vars.input_opacity));
		txtName.setBounds(Layout.col1_initial_x, Layout.initial_y, Layout.input_width, Layout.input_height);
		add(txtName);

		// Username Field
		txtUsername = new JTextField(Vars.placeholder_username);
		txtUsername.setForeground(Color.WHITE);
		txtUsername.setOpaque(false);
		txtUsername.setBackground(new Color(255, 255, 255, Vars.input_opacity));
		txtUsername.setBounds(Layout.col1_initial_x, Layout.initial_y + Layout.input_margin, Layout.input_width,
				Layout.input_height);
		add(txtUsername);

		// 2a Col
		// Email Field
		txtEmail = new JTextField(Vars.placeholder_email);
		txtEmail.setForeground(Color.WHITE);
		txtName.setForeground(Color.WHITE);
		txtEmail.setOpaque(false);
		txtEmail.setBackground(new Color(255, 255, 255, Vars.input_opacity));
		txtEmail.setBounds(Layout.col2_initial_x, Layout.initial_y, Layout.input_width, Layout.input_height);
		add(txtEmail);

		// Password Visible
		btnPasswordVisible = new JButton();
		btnPasswordVisible.setBounds(Layout.col2_initial_x + (Layout.input_width - 40),
				Layout.initial_y + Layout.input_margin, 40, 40);
		btnPasswordVisible.setOpaque(false);
		btnPasswordVisible.setContentAreaFilled(false);
		btnPasswordVisible.setBorderPainted(false);
		ImageIcon pass_visible_icon = new ImageIcon(Vars.icon_path + "eye.png");
		btnPasswordVisible.setIcon(pass_visible_icon);
		add(btnPasswordVisible);

		// Password Field
		pwdPassword = new JPasswordField(Vars.placeholder_password);
		pwdPassword.setForeground(Color.WHITE);
		pwdPassword.setColumns(10);
		pwdPassword.setOpaque(false);
		pwdPassword.setBackground(new Color(255, 255, 255, Vars.input_opacity));
		pwdPassword.setBounds(Layout.col2_initial_x, Layout.initial_y + Layout.input_margin, Layout.input_width,
				Layout.input_height);
		add(pwdPassword);

		// Show char left
		lblCountChar = new JLabel(String.valueOf(Vars.max_length_about_you));
		lblCountChar.setForeground(Color.WHITE);
		lblCountChar.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCountChar.setBounds(416, 384, 84, 16);
		add(lblCountChar);

		// About Me Field
		txtrAboutYou = new JTextArea(Vars.placeholder_about);
		txtrAboutYou.setRows(5);
		txtrAboutYou.setForeground(Color.WHITE);
		txtrAboutYou.setFont(new Font("Open Sans", Font.PLAIN, 16));
		txtrAboutYou.setOpaque(false);
		txtrAboutYou.setBackground(new Color(255, 255, 255, Vars.input_opacity));
		txtrAboutYou.setColumns(2);
		txtrAboutYou.setBounds(Layout.initial_x + Layout.textarea_offset, Layout.initial_y + Layout.input_margin * 2,
				Layout.textarea_width, Layout.input_height);
		txtrAboutYou.setBackground(new Color(255, 255, 255, Vars.input_opacity));
		txtrAboutYou.setLineWrap(true);
		add(txtrAboutYou);
		DataValidation.limitCharInTxtArea(txtrAboutYou, lblCountChar, Vars.max_length_about_you);

		// Sign Up Button
		btnSignup = new JButton("Sign Up");
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Validate Empty Fields
				if (txtName.getText().equals("") || txtEmail.getText().equals("") || txtUsername.getText().equals("")
						|| String.valueOf(pwdPassword.getPassword()).equals("") || txtrAboutYou.getText().equals("")
						|| txtName.getText().equals(Vars.placeholder_name)
						|| txtEmail.getText().equals(Vars.placeholder_email)
						|| txtUsername.getText().equals(Vars.placeholder_username)
						|| String.valueOf(pwdPassword.getPassword()).equals(Vars.placeholder_password)
						|| txtrAboutYou.getText().equals(Vars.placeholder_about))
					JOptionPane.showMessageDialog(null, "Warning! You must fill in all the required fields",
							"Validation Error", JOptionPane.WARNING_MESSAGE);

				// Email Validation
				else if (!DataValidation.emailValidation(txtEmail.getText())) {
					JOptionPane.showMessageDialog(null, "Warning! Insert a valid email address!", "Validation Error",
							JOptionPane.WARNING_MESSAGE);
				} else {
					String username = txtUsername.getText();
					String email = txtEmail.getText();
					String password = String.valueOf(pwdPassword.getPassword());
					String name = txtName.getText();
					String about_me = txtrAboutYou.getText();

					User new_user = new User(username, email, password, name, about_me, null, null, null, null, null);
					if (new_user.addUser(new_user))
						JOptionPane.showMessageDialog(null,
								"Sign up successful, welcome to Square, " + new_user.getName(), "Welcome to Square",
								JOptionPane.OK_OPTION);
					else
						JOptionPane.showMessageDialog(null,
								"Username already exists, please try another one, " + new_user.getName(), "Warning",
								JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnSignup.setFont(new Font("Open Sans", Font.PLAIN, 16));
		btnSignup.setBounds(Layout.col1_initial_x + 150, Layout.initial_y + Layout.input_margin * 4, 100,
				Layout.input_height);
		btnSignup.setBounds(251, 420, 100, Layout.input_height);
		add(btnSignup);

		// Enter on Submit
		KeyAdapter form_enter_submit = (new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					btnSignup.doClick();
			}
		});
		for (Component c : this.getComponents())
			c.addKeyListener(form_enter_submit);

		// Placeholders
		DataValidation.deleteTextOnFocusGained(txtUsername, Vars.placeholder_username);
		DataValidation.deleteTextOnFocusGained(txtName, Vars.placeholder_name);
		DataValidation.deleteTextOnFocusGained(txtEmail, Vars.placeholder_email);
		DataValidation.deleteTextOnFocusGained(txtrAboutYou, Vars.placeholder_about);
		DataValidation.deleteTextOnFocusGained(pwdPassword, Vars.placeholder_password);

		// Avatar
		AvatarUpload avatarUpload = null;
		try {
			avatarUpload = new AvatarUpload();
		} catch (IOException e) {
			e.printStackTrace();
		}
		avatarUpload.setBounds(Layout.col1_initial_x, Layout.initial_y + Layout.input_margin * 2,
				Layout.avatar_upload_size, Layout.avatar_upload_size);
		add(avatarUpload);

		// Listeners - Controllers
		// Set Password Visible
		btnPasswordVisible.addMouseListener(new MouseAdapter() {
			char echo = pwdPassword.getEchoChar();

			@Override
			public void mouseClicked(MouseEvent e) {
				if (pwdPassword.echoCharIsSet())
					pwdPassword.setEchoChar((char) 0);
				else
					pwdPassword.setEchoChar(echo);
			}
		});

	}
}
