package pajc.square.appView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import pajc.square.model.User;

public class Profile extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel profile_header;
	private JPanel profile_body;
	private JLabel lblProfileUsername;

	public Profile(User user) {
		// Get User Resolution
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		// Profile Wrap
		getContentPane().setLayout(null);
		getContentPane().setLayout(new BorderLayout(dim.width, dim.height));
		setBounds(0, 0, dim.width / 2, dim.height - 100);
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		profile_header = new JPanel();
		getContentPane().add(profile_header, BorderLayout.NORTH);

		lblProfileUsername = new JLabel("Profile Username");
		profile_header.add(lblProfileUsername);

		profile_body = new JPanel(new GridLayout(4, 4, 3, 3));
		for (int i = 0; i < 16; i++) {
			JLabel l = new JLabel("" + i, JLabel.CENTER);
			// JLabel l = new JLabel(new ImageIcon("image_file.png"),
			// JLabel.CENTER);
			l.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			l.setFont(l.getFont().deriveFont(20f));
			profile_body.add(l);
		}

		getContentPane().add(profile_body, BorderLayout.CENTER);

	}

}
