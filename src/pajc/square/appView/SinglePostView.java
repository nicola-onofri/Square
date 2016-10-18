package pajc.square.appView;

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

public class SinglePostView extends JPanel {
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

	public SinglePostView(Rectangle bounds, Post post, User loggedUser) {
		System.out.println(bounds.getBounds());
		setLayout(null);
		setVisible(true);
		setBackground(Color.WHITE);
		setBounds(bounds);
		setBounds(getX() + Layout.single_post_pnl_offset, getY() + Layout.component_margin,
				getWidth() - Layout.single_post_pnl_offset * 2, getHeight() - Layout.component_margin * 2);
		setBorder(BorderFactory.createMatteBorder(Layout.singlePost_border_size, Layout.singlePost_border_size,
				Layout.singlePost_border_size, Layout.singlePost_border_size, ColorPalette.light_blue_separator));

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
		int criticalSize = Math.max(getWidth(), getHeight() - separatorHeader.getX());

		// Post picture in the middle of the container
		lblPostPicture = new JLabel();
		lblPostPicture.setBounds(Layout.singlePost_border_size, separatorHeader.getY(), criticalSize, criticalSize);
		lblPostPicture
				.setIcon(Layout.getScaledImage(post.getImage(), lblPostPicture.getWidth(), lblPostPicture.getWidth()));

		lblPostPicture.addMouseListener(new MouseAdapter() {

			// OnClick show PostDetails View, the same used in the PostGrid menu
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Mouse Clicked!");
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

		// TODO: fix issue: where can I put the rest of the component?
		
		// separatorFooter = new JSeparator();
		// separatorFooter.setBounds(Layout.singlePost_border_size,
		// lblPostPicture.getY() + lblPostPicture.getHeight(),
		// getWidth() - Layout.singlePost_border_size * 2,
		// Layout.separator_height);
		// separatorFooter.setBackground(ColorPalette.light_blue_separator);
		// add(separatorFooter);

		// Footer Bar
		// pnlFooter = new JPanel();
		// pnlFooter.setLayout(null);
		// pnlFooter.setBackground(Color.BLUE);
		// pnlFooter.setBounds(Layout.singlePost_border_size,
		// separatorHeader.getY() + separatorFooter.getWidth(),
		// getWidth() - Layout.single_post_size * 2, 150);
		// add(pnlFooter);
		// lblPostDate = new JLabel(post.getDate().toString());
		// lblPostDate.setBounds(10, 10, 70, 15);
		// pnlFooter.add(lblPostDate);
		//
		// txtrCaption = new JTextArea();
		// txtrCaption.setText("Post Caption");
		// txtrCaption.setBounds(12, 41, 445, 32);
		// pnlFooter.add(txtrCaption);
		//
		// lblHeart = new JLabel("lblHeart");
		// lblHeart.setBounds(12, 12, 25, 15);
		// pnlFooter.add(lblHeart);
		//
		// lblLikes = new JLabel("likes");
		// lblLikes.setBounds(49, 14, 70, 15);
		// pnlFooter.add(lblLikes);
	}
}
