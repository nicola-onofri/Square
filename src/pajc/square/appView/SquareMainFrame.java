package pajc.square.appView;

import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import pajc.config.Config;
import pajc.config.FontSettings;
import pajc.config.Layout;
import pajc.config.Vars;
import pajc.square.model.Post;
import pajc.square.model.User;

public class SquareMainFrame {
	private JFrame frame;
	protected static HashMap<Boolean, User> loggedin = new HashMap<Boolean, User>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SquareMainFrame window = new SquareMainFrame();
					window.frame.setVisible(true);
					window.frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SquareMainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Square");
		frame.setBounds(0, 0, Layout.dim.width / 2, Layout.dim.height - 100);
		frame.setLocation(Layout.dim.width / 2 - frame.getSize().width / 2,
				Layout.dim.height / 2 - frame.getSize().height / 2);

		User loggedUser = new User("kyliejenner", "queenkylie@xoxo.com", "xoxo", "Kylie Jenner", "Simply you queen",
				new ImageIcon(Vars.post_path + "kylie_4.png"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>());

		User user = new User("wizkhalifa", "wizK@joint.com", "jolla", "Wiz Khalifa",
				"Sebastian's Dad; Mr Personality; Party Shirt Ambassador", new ImageIcon(Vars.post_path + "wiz_2.png"),
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

		// By default create a PersistentNavBar with loggedUser View
		frame.add(new PersistentNavigationBar(frame, loggedUser, user));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// App Configuration
		Config config = new Config(true);

		// Appearance Settings
		FontSettings.setUIFont(new Font("Droid Sans", Font.PLAIN, 13));
	}
}
