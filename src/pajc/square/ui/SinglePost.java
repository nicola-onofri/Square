package pajc.square.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeSupport;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import pajc.config.ColorPalette;
import pajc.config.Layout;
import pajc.config.RoundedImage;
import pajc.square.model.Post;
import pajc.square.model.User;

public class SinglePost extends JPanel {
	private static final long serialVersionUID = 1L;
	private Boolean isFollowed;
	private JButton btnFollow;
	private JLabel lblHeart;
	private JLabel lblLikes;
	private JLabel lblPostDate;
	private JLabel lblPostPicture;
	private JLabel lblProfilePicture;
	private JLabel lblUsername;
	private JFrame postDetailsFrame;
	private JPanel pnlHeader;
	private JPanel pnlFooter;
	private JSeparator separatorHeader;
	private JSeparator separatorFooter;
	private JTextArea txtrCaption;

	protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

	public SinglePost(Rectangle bounds, Post post, User loggedUser) {
		setLayout(null);
		setBackground(Color.WHITE);
		setBounds(bounds);
		
		setBounds(getX() + Layout.single_post_pnl_offset, getY() + Layout.component_margin,
				getWidth() - Layout.single_post_pnl_offset * 2, getWidth() - Layout.single_post_pnl_offset * 2);
		setBorder(BorderFactory.createMatteBorder(Layout.singlePost_border_size, Layout.singlePost_border_size,
				Layout.singlePost_border_size, Layout.singlePost_border_size, ColorPalette.light_blue_separator));
		setBackground(Color.YELLOW);

		System.out.println("SinglePostView bounds: " + getBounds());

		isFollowed = post.getOwner().getFollowers().contains(loggedUser) ? true : false;

		// Header Bar
		pnlHeader = new JPanel();
		pnlHeader.setLayout(null);
		pnlHeader.setBackground(Color.WHITE);
		pnlHeader.setBounds(Layout.singlePost_border_size, Layout.singlePost_border_size,
				getWidth() - Layout.singlePost_border_size * 2, Layout.avatar_min_size + Layout.component_margin);
		add(pnlHeader);

		lblProfilePicture = new JLabel();
		lblProfilePicture.setBounds(Layout.component_margin, Layout.component_margin / 2, Layout.avatar_min_size,
				Layout.avatar_min_size);
		lblProfilePicture.setIcon(RoundedImage.createRoundedImage(Layout
				.getScaledImage(post.getOwner().getProfilePicture(), Layout.avatar_min_size, Layout.avatar_min_size)));
		pnlHeader.add(lblProfilePicture);
		lblProfilePicture.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("MouseClicked: " + post.getDescription());
			}
		});

		lblUsername = new JLabel(post.getOwner().getUsername());
		lblUsername.setBounds(lblProfilePicture.getX() + lblProfilePicture.getWidth() + Layout.component_margin * 3,
				lblProfilePicture.getY() + lblProfilePicture.getHeight() / 2 - Layout.label_default_height / 2,
				pnlHeader.getWidth() - lblProfilePicture.getWidth() - Layout.component_margin * 6
						- Layout.button_default_width,
				Layout.label_default_height);
		lblUsername.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsername.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		pnlHeader.add(lblUsername);

		// Button follow/unfollow - code reuse
		btnFollow = new JButton();
		btnFollow.setBounds(lblUsername.getX() + lblUsername.getWidth() + Layout.component_margin,
				lblUsername.getY() - (Layout.button_default_height - Layout.label_default_height),
				Layout.button_default_width, Layout.button_default_height);
		btnFollow.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		btnFollow.setForeground(Color.WHITE);
		pnlHeader.add(btnFollow);

		if (!loggedUser.equals(post.getOwner())) {
			// initialize btn with the correct color
			if (isFollowed) {
				btnFollow.setText("Unfollow");
				btnFollow.setBackground(ColorPalette.red_button);
			} else {
				btnFollow.setText("Follow");
				btnFollow.setBackground(ColorPalette.green_button);
			}

			// Change status and color of the button only if post owner and
			// loggedUser are not the same person
			// TODO firePropertyChange and sync
			btnFollow.addMouseListener(new MouseAdapter() {
				boolean following = loggedUser.getFollowings().contains(post.getOwner());

				@Override
				public void mouseClicked(MouseEvent e) {
					if (following) {
						following = false;
						btnFollow.setText("Follow");
						btnFollow.setBackground(ColorPalette.green_button);
						// changes.firePropertyChange("User Followed", false,
						// true);
					} else {
						following = true;
						btnFollow.setText("Unfollow");
						btnFollow.setBackground(ColorPalette.red_button);
						// changes.firePropertyChange("User Unfollowed", true,
						// false);
					}
				}
			});
		} else
			btnFollow.setVisible(false);

		separatorHeader = new JSeparator();
		separatorHeader.setBounds(Layout.singlePost_border_size, pnlHeader.getHeight(), getWidth(),
				Layout.separator_height);
		separatorHeader.setBackground(ColorPalette.light_blue_separator);
		add(separatorHeader);

		// Space between header and footer
		System.out.println("ContentPane: " + getWidth() + " " + (getHeight() - separatorHeader.getX()));
		int criticalSize = Math.min(getWidth(), getHeight());

		// Post picture in the middle of the container
		lblPostPicture = new JLabel();
		lblPostPicture.setBounds(Layout.singlePost_border_size, separatorHeader.getY(), criticalSize, criticalSize);
		lblPostPicture
				.setIcon(Layout.getScaledImage(post.getImage(), lblPostPicture.getWidth(), lblPostPicture.getWidth()));

		lblPostPicture.addMouseListener(new MouseAdapter() {

			// OnClick show PostDetails View, the same used in the PostGrid menu
			@Override
			public void mouseClicked(MouseEvent e) {
				postDetailsFrame = new JFrame(post.getOwner().getUsername() + "'s post");
				postDetailsFrame.setBounds(Layout.dim.width / 4, Layout.dim.height / 4, Layout.dim.width / 2,
						Layout.dim.height / 2);
				PostDetails pd = new PostDetails(postDetailsFrame, post, loggedUser, post.getOwner());
				postDetailsFrame.getContentPane().add(pd, BorderLayout.CENTER);
				postDetailsFrame.setResizable(false);
				postDetailsFrame.setVisible(true);
				postDetailsFrame.setAlwaysOnTop(true);
				postDetailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		add(lblPostPicture);

		// Footer Bar
		// pnlFooter = new JPanel();
		// pnlFooter.setLayout(null);
		// pnlFooter.setBackground(Color.WHITE);
		// pnlFooter.setSize(getWidth() - Layout.singlePost_border_size * 2,
		// (int) (pnlHeader.getHeight() * 1.5));
		// pnlFooter.setLocation(Layout.singlePost_border_size,
		// getHeight() - pnlFooter.getHeight() - Layout.singlePost_border_size);
		// add(pnlFooter);
		//
		// separatorFooter = new JSeparator();
		// separatorFooter.setBounds(Layout.singlePost_border_size,
		// pnlFooter.getY() - Layout.separator_height / 2,
		// getWidth() - Layout.singlePost_border_size * 2,
		// Layout.separator_height);
		// separatorFooter.setBackground(ColorPalette.light_blue_separator);
		// add(separatorFooter);

		// lblHeart = new JLabel("lblHeart");
		// lblHeart.setBounds(12, 12, 25, 15);
		// pnlFooter.add(lblHeart);
		//
		// lblLikes = new JLabel("likes");
		// lblLikes.setBounds(49, 14, 70, 15);
		// pnlFooter.add(lblLikes);

		// lblPostDate = new JLabel();
		// if (Utility.getRoundedTimeDifference(post.getDate()).containsKey(new
		// String(Vars.hours_tag)))
		// lblPostDate.setText("Diff in hours: " +
		// Utility.getRoundedTimeDifference(post.getDate()).get(Vars.hours_tag)
		// + " hours");
		// else
		// lblPostDate.setText("Diff in minutes: "
		// +
		// Utility.getRoundedTimeDifference(post.getDate()).get(Vars.minutes_tag)
		// + " minutes");
		//
		// lblPostDate.setBounds(Layout.component_margin, 0, 0, 0);
		// lblPostDate.setVisible(false);
		// pnlFooter.add(lblPostDate);

		// txtrCaption = new JTextArea();
		// txtrCaption.setText("Post Caption");
		// txtrCaption.setBounds(12, 41, 445, 32);
		// pnlFooter.add(txtrCaption);
		//

	}
}
