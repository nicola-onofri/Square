package pajc.square.appView;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import pajc.config.ColorPalette;
import pajc.config.Layout;
import pajc.square.model.Post;
import pajc.square.model.User;

public class SinglePostView extends JPanel {
	private static final long serialVersionUID = 1L;

	private JButton btnFollow;
	private JLabel lblHeart;
	private JLabel lblLikes;
	private JLabel lblPostDate;
	private JLabel lblPostPicture;
	private JLabel lblProfilePicture;
	private JLabel lblUsername;
	private JPanel pnlHeader;
	private JPanel pnlFooter;
	private JSeparator separatorHeader;
	private JSeparator separatorFooter;
	private JTextArea txtrCaption;

	public SinglePostView(JFrame container, Post post, User loggedUser) {
		setLayout(null);
		setBackground(ColorPalette.white_background);

		// Header Bar
		pnlHeader = new JPanel();
		pnlHeader.setLayout(null);
		pnlHeader.setBackground(Color.WHITE);
		pnlHeader.setBounds(0, 0, getWidth(), Layout.avatar_min_size + Layout.component_margin * 2);
		add(pnlHeader);

		// Post picture in the middle of the container
		lblPostPicture = new JLabel();
		lblPostPicture.setBackground(Color.WHITE);
		lblPostPicture.setBounds(Layout.component_margin, pnlHeader.getHeight(),
				getWidth() - Layout.component_margin * 2, lblProfilePicture.getWidth());
		lblPostPicture.setIcon(
				Layout.getScaledImage(post.getImage(), lblProfilePicture.getWidth(), lblProfilePicture.getWidth()));
		add(lblPostPicture);

		lblProfilePicture = new JLabel();
		lblProfilePicture.setBounds(Layout.component_margin, Layout.component_margin, Layout.avatar_min_size,
				Layout.avatar_min_size);
		lblProfilePicture
				.setIcon(Layout.getScaledImage(post.getImage(), Layout.avatar_min_size, Layout.avatar_min_size));
		pnlHeader.add(lblProfilePicture);

		// TODO continue layout from here
		lblUsername = new JLabel(post.getOwner().getUsername());
		lblUsername.setBounds(83, 26, 264, 15);
		pnlHeader.add(lblUsername);

		btnFollow = new JButton("Follow");
		btnFollow.setBounds(352, 21, 117, 25);
		pnlHeader.add(btnFollow);

		separatorHeader = new JSeparator();
		separatorHeader.setBounds(0, 0, 1, 2);
		pnlHeader.add(separatorHeader);

		pnlFooter = new JPanel();
		pnlFooter.setBackground(Color.WHITE);
		pnlFooter.setBounds(0, 322, 469, 112);
		add(pnlFooter);
		pnlFooter.setLayout(null);

		lblPostDate = new JLabel(post.getDate().toString());
		lblPostDate.setBounds(199, 85, 70, 15);
		pnlFooter.add(lblPostDate);

		txtrCaption = new JTextArea();
		txtrCaption.setText("Post Caption");
		txtrCaption.setBounds(12, 41, 445, 32);
		pnlFooter.add(txtrCaption);

		lblHeart = new JLabel("lblHeart");
		lblHeart.setBounds(12, 12, 25, 15);
		pnlFooter.add(lblHeart);

		lblLikes = new JLabel("likes");
		lblLikes.setBounds(49, 14, 70, 15);
		pnlFooter.add(lblLikes);

		separatorFooter = new JSeparator();
		separatorFooter.setBounds(0, 0, 1, 2);
		pnlFooter.add(separatorFooter);

	}
}
