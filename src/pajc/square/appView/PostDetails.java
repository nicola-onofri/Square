package pajc.square.appView;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import pajc.config.ColorPalette;
import pajc.config.DataValidation;
import pajc.config.Layout;
import pajc.config.RoundedImage;
import pajc.config.Vars;
import pajc.square.model.Post;
import pajc.square.model.User;

public class PostDetails extends JPanel implements PropertyChangeListener {
	private static final long serialVersionUID = 1L;
	private Boolean isLiked;
	private Boolean isFollowed;
	private JScrollPane scrollPane;
	private JButton btnFollow;
	private JLabel lblLikes;
	private JLabel lblHeart;
	private JPanel pnlComments;
	private JLabel lblCaption;
	private JLabel lblImage;
	private JLabel lblComments;
	private JLabel lblProfileThumbnail;
	private JLabel lblOwner;
	private JLabel lblCountChar;
	private JTextArea txtrNewComment;

	protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

	public PostDetails(JFrame container, Post post, User loggedUser, User user) {
		setBackground(ColorPalette.white_background);
		setLayout(null);
		setSize(container.getWidth(), container.getHeight());

		// Boolean flags initialization
		isLiked = post.getLikes().contains(loggedUser) ? true : false;
		isFollowed = user.getFollowers().contains(loggedUser) ? true : false;

		// Image Label
		lblImage = new JLabel();
		lblImage.setBounds(Layout.component_margin, Layout.component_margin, getWidth() / 2 - Layout.component_margin,
				getWidth() / 2 - Layout.component_margin);
		lblImage.setIcon(Layout.getScaledImage(post.getImage(), lblImage.getWidth(), lblImage.getWidth()));
		add(lblImage);

		// Fix Frame Maximum X
		int container_max_X = container.getWidth() - Layout.component_margin;

		// Fix Component Alignment
		int component_alignment_X = lblImage.getX() + lblImage.getWidth() + (Layout.component_margin * 2);

		// Post Owner profile image
		lblProfileThumbnail = new JLabel();
		lblProfileThumbnail.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		lblProfileThumbnail.setBounds(component_alignment_X, lblImage.getY(), Layout.avatar_min_size,
				Layout.avatar_min_size);
		lblProfileThumbnail.setIcon(RoundedImage.createRoundedImage(Layout
				.getScaledImage(post.getOwner().getProfilePicture(), Layout.avatar_min_size, Layout.avatar_min_size)));
		add(lblProfileThumbnail);

		// Post Owner username
		lblOwner = new JLabel(post.getOwner().getUsername());
		lblOwner.setFont(new Font("Droid Sans", Font.BOLD, 13));
		lblOwner.setBounds(component_alignment_X,
				lblProfileThumbnail.getY() + lblProfileThumbnail.getHeight() + Layout.component_margin,
				container_max_X - component_alignment_X, Layout.label_default_height);
		add(lblOwner);

		// Like Button
		lblHeart = new JLabel();
		lblHeart.setBounds(component_alignment_X, lblOwner.getY() + lblOwner.getHeight() + Layout.component_margin,
				Layout.avatar_thumbnail_size, Layout.avatar_thumbnail_size);
		lblHeart.setIcon(new ImageIcon(Vars.icon_path + "heart_void.png"));

		if (!loggedUser.equals(user)) {
			lblHeart.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if (!isLiked) {
						isLiked = true;
						lblHeart.setIcon(new ImageIcon(Vars.icon_path + "heart_black.png"));
						changes.firePropertyChange("Post Liked", post.getLikes().size(), post.getLikes().size() + 1);
					} else {
						isLiked = false;
						lblHeart.setIcon(new ImageIcon(Vars.icon_path + "heart_void.png"));
						changes.firePropertyChange("Post Unliked", post.getLikes().size(), post.getLikes().size() - 1);
					}
				}
			});
		}
		add(lblHeart);

		// Post Likes
		lblLikes = new JLabel(post.getLikes().size() + " likes");
		lblLikes.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		lblLikes.setBounds(lblHeart.getX() + lblHeart.getWidth(), lblHeart.getY() + lblHeart.getHeight() / 4,
				container_max_X - lblHeart.getX() + lblHeart.getWidth() + Layout.component_margin,
				Layout.label_default_height);
		add(lblLikes);

		// Post description
		lblCaption = new JLabel(post.getDescription());
		lblCaption.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		lblCaption.setVerticalAlignment(SwingConstants.TOP);
		lblCaption.setBounds(component_alignment_X, lblHeart.getY() + lblHeart.getHeight() + Layout.component_margin,
				container_max_X - component_alignment_X, Layout.label_default_height * 2);
		add(lblCaption);

		// ScrollPane contains comments
		scrollPane = new JScrollPane();
		scrollPane.setBounds(component_alignment_X,
				lblCaption.getY() + lblCaption.getHeight() + Layout.component_margin,
				container_max_X - component_alignment_X, Layout.label_default_height * 5);
		add(scrollPane);

		pnlComments = new JPanel();
		pnlComments.setBackground(Color.WHITE);
		scrollPane.setViewportView(pnlComments);
		pnlComments.setLayout(null);

		// TODO think about an elegant way to show each comment, with the
		// relative user
		lblComments = new JLabel("Comments here");
		lblComments.setBounds(12, 12, 260, 15);
		pnlComments.add(lblComments);

		// Follow/Unfollow Button
		btnFollow = new JButton();
		btnFollow.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		btnFollow.setForeground(Color.WHITE);
		btnFollow.setSize(Layout.button_default_width, Layout.button_default_height);
		btnFollow.setLocation(container_max_X - btnFollow.getWidth(), Layout.component_margin);

		// TODO initialize the button with the correct color, based on the
		// loggedUser info
		if (loggedUser.getFollowings().contains(post.getOwner())) {
			btnFollow.setText("Unfollow");
			btnFollow.setBackground(ColorPalette.red_button);
		} else {
			btnFollow.setText("Follow");
			btnFollow.setBackground(ColorPalette.green_button);
		}

		// Change status and color of the button
		btnFollow.addMouseListener(new MouseAdapter() {
			boolean following = loggedUser.getFollowings().contains(post.getOwner());

			@Override
			public void mouseClicked(MouseEvent e) {
				if (following) {
					following = false;
					btnFollow.setText("Follow");
					btnFollow.setBackground(ColorPalette.green_button);
					changes.firePropertyChange("User Followed", false, true);
				} else {
					following = true;
					btnFollow.setText("Unfollow");
					btnFollow.setBackground(ColorPalette.red_button);
					changes.firePropertyChange("User Unfollowed", true, false);
				}
			}
		});
		add(btnFollow);

		// TODO confirmation key/button
		// Add a new comment
		txtrNewComment = new JTextArea("Write a comment...");
		txtrNewComment.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		txtrNewComment.setColumns(10);
		txtrNewComment.setEditable(true);
		txtrNewComment.setLineWrap(true);
		txtrNewComment.setWrapStyleWord(true);
		txtrNewComment.setLocation(component_alignment_X,
				scrollPane.getY() + scrollPane.getHeight() + Layout.component_margin);
		txtrNewComment.setSize(container_max_X - component_alignment_X, lblImage.getY() + lblImage.getHeight()
				- (scrollPane.getY() + scrollPane.getHeight() + Layout.component_margin));
		add(txtrNewComment);
		DataValidation.deleteTextOnFocusGained(txtrNewComment, Vars.placeholder_newComment);

		// Characters in comment
		lblCountChar = new JLabel(String.valueOf(Vars.max_length_comment));
		lblCountChar.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCountChar.setBackground(Color.BLACK);
		lblCountChar.setSize(100, Layout.label_default_height);
		lblCountChar.setLocation(component_alignment_X + txtrNewComment.getWidth() - lblCountChar.getWidth(),
				txtrNewComment.getY() + txtrNewComment.getHeight() - lblCountChar.getHeight());
		add(lblCountChar);

		DataValidation.limitCharInTxtArea(txtrNewComment, lblCountChar, Vars.max_length_comment);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changes.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changes.removePropertyChangeListener(listener);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
		case Vars.user_followed:
			break;
		case Vars.user_unfollowed:
			break;
		case Vars.post_liked:
			break;
		case Vars.post_unliked:
			break;
		}
	}
}
