package pajc.square.logAuth;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import pajc.config.ColorPalette;
import pajc.config.DataValidation;
import pajc.config.InterfaceHelpers;
import pajc.config.Layout;
import pajc.config.Vars;
import pajc.square.model.User;
import pajc.square.view.UserProfile;

public class LoginForm extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField txtUsername;
	private JPasswordField pwdPassword;
	private JButton btnPasswordVisible;
	private JButton btnLogin;
	private JLabel lblHelp;
	private JLabel lblLogo;

	private final static String placeholder_username = "Username";
	private final static String placeholder_password = "Password";

	// Gradient Background
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		int w = getWidth();
		int h = getHeight();
		GradientPaint gp = new GradientPaint(0, 0, ColorPalette.light_blue_background, 0, h,
				ColorPalette.blue_background);
		g2d.setPaint(gp);
		g2d.fillRect(0, 0, w, h);
	}

	public LoginForm(JFrame container) {
		// Get User Resolution
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLayout(null);

		// Logo
		ImageIcon logo_image = new ImageIcon("res/square_logo_light.png");
		lblLogo = new JLabel(logo_image);
		lblLogo.setForeground(Color.WHITE);
		lblLogo.setFont(new Font("Open Sans", Font.PLAIN, 16));
		lblLogo.setBounds(Layout.initial_x, 25, dim.width / 6, dim.width / 6);
		add(lblLogo);

		// Username Field
		txtUsername = new JTextField();
		txtUsername.setHorizontalAlignment(SwingConstants.LEFT);
		txtUsername.setText(placeholder_username);
		txtUsername.setBounds(Layout.initial_x, Layout.initial_y, Layout.input_width, Layout.input_height);
		txtUsername.setOpaque(false);
		txtUsername.setBackground(new Color(255, 255, 255, Vars.input_opacity));
		txtUsername.setForeground(Color.white);
		add(txtUsername);

		// Password Visible
		btnPasswordVisible = new JButton();
		btnPasswordVisible.setBounds(Layout.initial_x + 160, Layout.initial_y + Layout.input_margin, 40, 40);
		ImageIcon pass_visible_icon = new ImageIcon(Vars.icon_path + "eye.png");
		btnPasswordVisible.setIcon(pass_visible_icon);
		btnPasswordVisible.setOpaque(false);
		btnPasswordVisible.setContentAreaFilled(false);
		btnPasswordVisible.setBorderPainted(false);
		add(btnPasswordVisible);

		// Password Field
		pwdPassword = new JPasswordField();
		pwdPassword.setText(placeholder_password);
		pwdPassword.setBounds(Layout.initial_x, Layout.initial_y + Layout.input_margin, Layout.input_width,
				Layout.input_height);
		pwdPassword.setOpaque(false);
		pwdPassword.setBackground(new Color(255, 255, 255, Vars.input_opacity));
		pwdPassword.setForeground(Color.white);
		add(pwdPassword);

		// Login Button
		btnLogin = new JButton("Log In");
		btnLogin.setFont(new Font("Open Sans", Font.PLAIN, 16));
		btnLogin.setBounds(Layout.initial_x + 50, Layout.initial_y + Layout.input_margin * 2, 100, Layout.input_height);
		add(btnLogin);

		// Enter on Submit
		KeyAdapter form_enter_submit = (new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					btnLogin.doClick();
			}
		});
		for (Component c : this.getComponents())
			c.addKeyListener(form_enter_submit);

		// Help Label
		lblHelp = new JLabel("Forgot your login details? Get help signin in.");
		lblHelp.setForeground(Color.WHITE);
		lblHelp.setFont(new Font("Open Sans", Font.PLAIN, 16));
		lblHelp.setBounds(129, 376, 330, 23);
		add(lblHelp);

		// Login Submit
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtUsername.getText().equals("") || String.valueOf(pwdPassword.getPassword()).equals("")
						|| txtUsername.getText().equals(placeholder_username)
						|| String.valueOf(pwdPassword.getPassword()).equals(Vars.placeholder_password))
					JOptionPane.showMessageDialog(null, "Attenzione! Compilare tutti i dati richiesti!",
							"Errore di Validazione", JOptionPane.WARNING_MESSAGE);
				else {
					Login login = new Login(txtUsername.getText(), String.valueOf(pwdPassword.getPassword()));
					if (login.isLogged_in()) {
						JOptionPane.showMessageDialog(null, "Benvenuto, " + txtUsername.getText(), "Login Success",
								JOptionPane.INFORMATION_MESSAGE);

						User logged_user = login.getLogged_in_user();

						System.out.println(logged_user.toString());

						//UserProfile profile = new UserProfile(container, null, logged_user, logged_user);

						// Profile Frame Switch
						InterfaceHelpers.closeParent(txtUsername);
						//profile.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Attenzione! Username o Password errati! Riprova!",
								"Errore di Validazione", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});

		// Placeholder Text
		DataValidation.deleteTextOnFocusGained(txtUsername, Vars.placeholder_username);
		DataValidation.deleteTextOnFocusGained(pwdPassword, Vars.placeholder_password);

		// Password Visible Switch
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