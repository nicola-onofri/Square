package pajc.square.appView;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import pajc.config.ColorPalette;
import pajc.config.Layout;
import pajc.config.RoundedImage;
import pajc.config.Vars;
import pajc.square.model.User;

public class UserProfile extends JPanel {
	private static final long serialVersionUID = 1L;
	private Boolean isFollowed;
	private JButton btnEdit;
	private JButton btnFollow;
	private JLabel lblProfilePicture;
	private JLabel lblPostsValue;
	private JLabel lblPosts;
	private JLabel lblFollowersValue;
	private JLabel lblFollowers;
	private JLabel lblFollowingsValue;
	private JLabel lblFollowings;
	private JLabel lblUsername;
	private JLabel lblName;
	private JPanel pnlProfileHeader;
	private JScrollPane scrollableGrid;
	private JTextArea txtrAbout;
	private PostGrid postGrid;
	private PropertyChangeListener listener;

	protected PropertyChangeSupport changes;

	// UserProfileView Constructor
	public UserProfile(Rectangle bounds, User loggedUser, User user) {
		setLayout(null);
		setBounds(bounds);
		setBackground(ColorPalette.white_background);
		// System.out.println(container.getSize());

		changes = new PropertyChangeSupport(this);
		isFollowed = user.getFollowers().contains(loggedUser) ? true : false;

		pnlProfileHeader = new JPanel();
		pnlProfileHeader.setLayout(null);
		pnlProfileHeader.setBounds(0, 0, getWidth(), Layout.profile_picture_size + Layout.component_margin * 2);
		pnlProfileHeader.setBackground(ColorPalette.white_background);
		add(pnlProfileHeader);

		// Profile picture
		lblProfilePicture = new JLabel();
		lblProfilePicture.setBounds(Layout.component_margin, Layout.component_margin, Layout.profile_picture_size,
				Layout.profile_picture_size);
		lblProfilePicture.setIcon(RoundedImage.createRoundedImage(Layout.getScaledImage(user.getProfilePicture(),
				lblProfilePicture.getHeight(), lblProfilePicture.getWidth())));
		pnlProfileHeader.add(lblProfilePicture);

		// Fix Frame Maximum X
		int container_max_X = getWidth() - Layout.component_margin;

		// Fix Component Alignment
		int component_alignment_X = lblProfilePicture.getX() + lblProfilePicture.getWidth()
				+ Layout.component_margin * 2;

		btnFollow = new JButton();
		btnFollow.setSize(Layout.button_default_width, Layout.button_default_height);
		btnFollow.setLocation(container_max_X - btnFollow.getWidth(), lblProfilePicture.getY());
		btnFollow.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		btnFollow.setForeground(Color.WHITE);
		pnlProfileHeader.add(btnFollow);

		lblUsername = new JLabel(user.getUsername());
		lblUsername.setFont(new Font("Droid Sans", Font.PLAIN, 24));
		lblUsername.setBounds(component_alignment_X, lblProfilePicture.getY(),
				btnFollow.getX() - component_alignment_X - Layout.component_margin, Layout.label_default_height * 2);
		pnlProfileHeader.add(lblUsername);

		lblName = new JLabel(user.getName());
		lblName.setFont(new Font("Droid Sans", Font.BOLD, 14));
		lblName.setBounds(component_alignment_X, lblUsername.getY() + lblUsername.getHeight() + Layout.component_margin,
				container_max_X - component_alignment_X, Layout.label_default_height);
		pnlProfileHeader.add(lblName);

		txtrAbout = new JTextArea(user.getAbout());
		txtrAbout.setWrapStyleWord(true);
		txtrAbout.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		txtrAbout.setEditable(false);
		txtrAbout.setLineWrap(true);
		txtrAbout.setBounds(component_alignment_X, lblName.getY() + lblName.getHeight() + Layout.component_margin,
				container_max_X - component_alignment_X, Layout.label_default_height * 3);
		txtrAbout.setBackground(ColorPalette.white_background);
		pnlProfileHeader.add(txtrAbout);

		lblFollowers = new JLabel("followers");
		lblFollowers.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		lblFollowers.setSize(70, Layout.label_default_height);
		lblFollowers.setLocation(
				component_alignment_X + (container_max_X - component_alignment_X) / 2 - lblFollowers.getWidth(),
				txtrAbout.getY() + txtrAbout.getHeight() + Layout.component_margin);
		lblFollowers.setHorizontalAlignment(SwingConstants.LEFT);
		pnlProfileHeader.add(lblFollowers);

		lblFollowersValue = new JLabel(String.valueOf(user.getFollowers().size()));
		lblFollowersValue.setFont(new Font("Droid Sans", Font.BOLD, 13));
		lblFollowersValue.setSize(45, Layout.label_default_height);
		lblFollowersValue.setLocation(lblFollowers.getX() - Layout.component_margin - lblFollowersValue.getWidth(),
				txtrAbout.getY() + txtrAbout.getHeight() + Layout.component_margin);
		lblFollowersValue.setHorizontalAlignment(SwingConstants.CENTER);
		pnlProfileHeader.add(lblFollowersValue);

		lblPosts = new JLabel("posts");
		lblPosts.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		lblPosts.setSize(50, Layout.label_default_height);
		lblPosts.setLocation(lblFollowersValue.getX() - Layout.component_margin / 2 - lblPosts.getWidth(),
				txtrAbout.getY() + txtrAbout.getHeight() + Layout.component_margin);
		lblPosts.setHorizontalAlignment(SwingConstants.LEFT);
		pnlProfileHeader.add(lblPosts);

		lblPostsValue = new JLabel(String.valueOf(user.getPosts().size()));
		lblPostsValue.setFont(new Font("Droid Sans", Font.BOLD, 13));
		lblPostsValue.setSize(45, Layout.label_default_height);
		lblPostsValue.setLocation(lblPosts.getX() - Layout.component_margin - lblPostsValue.getWidth(),
				txtrAbout.getY() + txtrAbout.getHeight() + Layout.component_margin);
		lblPostsValue.setHorizontalAlignment(SwingConstants.CENTER);
		pnlProfileHeader.add(lblPostsValue);

		lblFollowingsValue = new JLabel(String.valueOf(user.getFollowings().size()));
		lblFollowingsValue.setFont(new Font("Droid Sans", Font.BOLD, 13));
		lblFollowingsValue.setSize(45, Layout.label_default_height);
		lblFollowingsValue.setLocation(lblFollowers.getX() + Layout.component_margin + lblFollowers.getWidth(),
				txtrAbout.getY() + txtrAbout.getHeight() + Layout.component_margin);
		lblFollowingsValue.setHorizontalAlignment(SwingConstants.CENTER);
		pnlProfileHeader.add(lblFollowingsValue);

		lblFollowings = new JLabel("followings");
		lblFollowings.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		lblFollowings.setSize(70, Layout.label_default_height);
		lblFollowings.setLocation(lblFollowingsValue.getX() + Layout.component_margin + lblFollowingsValue.getWidth(),
				txtrAbout.getY() + txtrAbout.getHeight() + Layout.component_margin);
		lblFollowings.setHorizontalAlignment(SwingConstants.LEFT);
		pnlProfileHeader.add(lblFollowings);

		// Edit button - visible if user equals loggedUser
		btnEdit = new JButton("Edit your profile");
		btnEdit.setSize(150, Layout.button_default_height);
		btnEdit.setForeground(Color.BLACK);
		btnEdit.setLocation(container_max_X - btnFollow.getWidth(), lblProfilePicture.getY());
		btnEdit.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		pnlProfileHeader.add(btnEdit);

		// PropertyChange Listener (PostGrid -> this)
		listener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				switch (evt.getPropertyName()) {
				case Vars.user_followed:
					System.out.println("User Followed - UserProfile");
					// Update model
					user.getFollowers().add(loggedUser);

					// Update view
					lblFollowers.setText(String.valueOf(user.getFollowers().size()));
					btnFollow.setText("Unfollow");
					btnFollow.setBackground(ColorPalette.red_button);
					isFollowed = true;
					break;
				case Vars.user_unfollowed:
					System.out.println("User Unfollowed - UserProfile");
					// Update model
					user.getFollowers().remove(loggedUser);

					// Update view
					lblFollowers.setText(String.valueOf(user.getFollowers().size()));
					btnFollow.setText("Follow");
					btnFollow.setBackground(ColorPalette.green_button);
					isFollowed = false;
					break;
				}
			}
		};

		// Post Grid
		postGrid = new PostGrid(loggedUser, user);
		postGrid.setBackground(Color.WHITE);
		postGrid.addPropertyChangeListener(listener);

		scrollableGrid = new JScrollPane(postGrid);
		scrollableGrid.setLocation(Layout.component_margin, pnlProfileHeader.getY() + pnlProfileHeader.getHeight());
		scrollableGrid.setSize(container_max_X - lblProfilePicture.getX(),
				getHeight() - pnlProfileHeader.getHeight() - pnlProfileHeader.getY() - Layout.component_margin / 2);
		scrollableGrid.setBackground(ColorPalette.white_background);
		scrollableGrid.setBorder(null);
		add(scrollableGrid);

		btnFollow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isFollowed) {
					// Can I change this method by using propertyChangeListener
					// directly?
					isFollowed = false;
					btnFollow.setText("Follow");
					btnFollow.setBackground(ColorPalette.green_button);
				} else {
					isFollowed = true;
					btnFollow.setText("Unfollow");
					btnFollow.setBackground(ColorPalette.red_button);
				}
			}
		});

		if (loggedUser.equals(user)) {
			btnEdit.setVisible(true);
			btnFollow.setVisible(false);
		} else if (isFollowed) {
			btnEdit.setVisible(false);
			btnFollow.setVisible(true);
			btnFollow.setText("Unfollow");
			btnFollow.setBackground(ColorPalette.red_button);
		} else {
			btnEdit.setVisible(false);
			btnFollow.setVisible(true);
			btnFollow.setText("Follow");
			btnFollow.setBackground(ColorPalette.green_button);
		}
	}

	public String getProfileOwner() {
		return lblName.getText();
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changes.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changes.removePropertyChangeListener(listener);
	}

	// @Override
	// public void propertyChange(PropertyChangeEvent evt) {
	// System.out.println(evt.getPropertyName() + " - UserProfile");
	// }
}
