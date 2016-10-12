package pajc.square.logAuth;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TabLogin extends JPanel {
	private static final long serialVersionUID = 1L;
	private LoginForm login;
	private SignUpForm sign_up;

	public TabLogin(JFrame container) {
		// New Tab Menu
		JTabbedPane tabMenu = new JTabbedPane();
		tabMenu.setFont(new Font("Open Sans", Font.PLAIN, 16));

		// Login
		login = new LoginForm(container);
		// ImageIcon feedIcon = new ImageIcon(icon_path + "login.png");
		tabMenu.addTab("Login", login);
		tabMenu.setSelectedIndex(0);

		// Sign Up
		sign_up = new SignUpForm();
		// ImageIcon exploreIcon = new ImageIcon(icon_path + "form.png");
		tabMenu.addTab("Sign Up", sign_up);

		// Add the Tabbed Pane to this panel.
		setLayout(new GridLayout(1, 1));
		add(tabMenu);
	}

	protected JPanel createInnerPanel(JFrame container, String text) {
		JPanel jplPanel = new LoginForm(container);
		jplPanel.setLayout(new GridLayout(1, 1));

		LoginForm loginForm = new LoginForm(container);
		jplPanel.add(loginForm);
		return jplPanel;
	}
}
