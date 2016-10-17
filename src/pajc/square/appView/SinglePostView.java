package pajc.square.appView;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeSupport;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import pajc.config.ColorPalette;
import pajc.config.Layout;
import pajc.config.RoundedImage;
import pajc.config.Vars;
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
	private JPanel pnlHeader;
	private JPanel pnlFooter;
	private JSeparator separatorHeader;
	private JSeparator separatorFooter;
	private JTextArea txtrCaption;

	protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

	public SinglePostView(Rectangle bounds, Post post, User loggedUser) {
		setLayout(null);
		setVisible(true);
		setBackground(Color.WHITE);
		setBounds(bounds);
		setBounds(getX() + 100, getY() + Layout.component_margin, getWidth() - 200,
				getHeight() - Layout.component_margin * 2);
		setBorder(BorderFactory.createMatteBorder(Layout.singlePost_border_size, Layout.singlePost_border_size,
				Layout.singlePost_border_size, Layout.singlePost_border_size, ColorPalette.light_blue_separator));

		isFollowed = post.getOwner().getFollowers().contains(loggedUser) ? true : false;

		// Header Bar
		pnlHeader = new JPanel();
		pnlHeader.setLayout(null);
		pnlHeader.setBackground(Color.WHITE);
		pnlHeader.setBounds(Layout.singlePost_border_size, Layout.singlePost_border_size,
				getWidth() - Layout.singlePost_border_size * 2, Layout.avatar_min_size + Layout.component_margin * 2);
		add(pnlHeader);

		lblProfilePicture = new JLabel();
		lblProfilePicture.setBounds(Layout.component_margin, Layout.component_margin, Layout.avatar_min_size,
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

		if (getRoundedTimeDifference(post.getDate()).containsKey(new String(Vars.hours_tag)))
			System.out.println(
					"Diff in hours: " + getRoundedTimeDifference(post.getDate()).get(Vars.hours_tag) + " hours");
		else
			System.out.println(
					"Diff in minutes: " + getRoundedTimeDifference(post.getDate()).get(Vars.minutes_tag) + " minutes");

		// Post picture in the middle of the container
		lblPostPicture = new JLabel();
		lblPostPicture.setBackground(Color.BLACK);
		// lblPostPicture.setBounds(Layout.component_margin,
		// pnlHeader.getHeight(),
		// getWidth() - Layout.component_margin * 2, getWidth() -
		// Layout.component_margin * 2);
		lblPostPicture.setBounds(Layout.component_margin, 300, 100, 100);
		lblPostPicture
				.setIcon(Layout.getScaledImage(post.getImage(), lblPostPicture.getWidth(), lblPostPicture.getWidth()));
		add(lblPostPicture);

		// separatorFooter = new JSeparator();
		// separatorFooter.setBounds(0, 0, 1, 2);
		// pnlFooter.add(separatorFooter);
		//
		// pnlFooter = new JPanel();
		// pnlFooter.setBackground(Color.WHITE);
		// pnlFooter.setBounds(0, 322, 469, 112);
		// add(pnlFooter);
		// pnlFooter.setLayout(null);
		//
		// lblPostDate = new JLabel(post.getDate().toString());
		// lblPostDate.setBounds(199, 85, 70, 15);
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

	// Get time difference between a given date and the current time, converted
	// to hours or minutes
	public HashMap<String, Long> getRoundedTimeDifference(Date postDate) {
		HashMap<String, Long> returnValue = new HashMap<>();

		Date currentTime = new GregorianCalendar().getTime();
		long diffInMillis = currentTime.getTime() - postDate.getTime();

		// if difference in hours is less than 1 (= 0) then return the value
		// converted in minutes, otherwise return the value converted in hours
		if (TimeUnit.HOURS.convert(diffInMillis, TimeUnit.MILLISECONDS) == 0)
			returnValue.put(Vars.minutes_tag, TimeUnit.MINUTES.convert(diffInMillis, TimeUnit.MILLISECONDS));
		else
			returnValue.put(Vars.hours_tag, TimeUnit.HOURS.convert(diffInMillis, TimeUnit.MILLISECONDS));
		return returnValue;
	}
}
