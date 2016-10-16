package pajc.square.appView;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import pajc.config.ColorPalette;
import pajc.config.DataValidation;
import pajc.config.Layout;
import pajc.config.RoundedImage;
import pajc.config.Vars;
import pajc.square.model.Notification;
import pajc.square.model.Post;
import pajc.square.model.User;

public class PersistentNavigationBar extends JComponent implements PropertyChangeListener {
	private static final long serialVersionUID = 1L;
	private static final int displayed_items_searchBar = 5;
	private JButton focusGrabber;
	private JLabel lblSquarelogo;
	private JLabel lblNotifications;
	private JLabel lblLikes;
	private JLabel lblExplore;
	private JLabel lblProfile;
	private JLabel lblNewPost;
	private JLabel lblLoginInfo;
	private JSeparator jsNavBar;
	private JSeparator jsMenu;
	private JPanel backgroundNavBar;
	private JPanel backgroundMenu;
	private JPanel contentPane;
	private JPopupMenu popupMenuSearch;
	private JPopupMenu popupMenuLikes;
	private JTextField txtSearchUser;
	private UserProfile userView;
	private SinglePostView singlePostView;

	protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

	public PersistentNavigationBar(JFrame container, User loggedUser, User user) {
		setBounds(0, 0, container.getWidth(), container.getHeight());

		contentPane = new JPanel();
		contentPane.setBounds(getX(),
				Layout.persistentNavBar_height + Layout.component_margin * 2 + Layout.separator_height / 2, getWidth(),
				getHeight() - Layout.persistentMenu_height - Layout.persistentNavBar_height
						- Layout.component_margin * 3 + Layout.separator_height);
		contentPane.setBackground(Color.WHITE);
		contentPane.setVisible(false);
		add(contentPane);

		// By default the first view is the loggedUser's ProfileView
		userView = new UserProfile(contentPane.getBounds(), loggedUser, user);
		add(userView);

		// Logo
		lblSquarelogo = new JLabel();
		lblSquarelogo.setIcon(new ImageIcon(Vars.icon_path + "square_logo_small.png"));
		lblSquarelogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblSquarelogo.setBounds(Layout.component_margin, Layout.component_margin / 2,
				Layout.persistentNavBar_height * 3, Layout.persistentNavBar_height + Layout.component_margin);
		add(lblSquarelogo);

		// Fix Vertical Margin
		int navBarVerticalAlignment = lblSquarelogo.getY() + lblSquarelogo.getHeight() / 2;

		// Logged User Profile
		lblProfile = new JLabel();
		lblProfile.setIcon(new ImageIcon(Vars.icon_path + "profile_small.png"));
		lblProfile.setBounds(container.getWidth() - Layout.component_margin * 2 - Layout.tab_icon_size,
				navBarVerticalAlignment - Layout.tab_icon_size / 2, Layout.tab_icon_size, Layout.tab_icon_size);
		add(lblProfile);

		// Go to loggedUser's ProfileView
		// lblProfile.addMouseListener(new MouseAdapter() {
		// @Override
		// public void mouseClicked(MouseEvent e) {
		// if (Arrays.asList(getComponents()).contains(userView) && userView !=
		// null) {
		// userView.setVisible(false);
		// remove(userView);
		// userView = new UserProfile(container, contentPane.getBounds(),
		// loggedUser, loggedUser);
		// add(userView);
		// }
		// }
		// });

		// Go to loggedUser's ProfileView
		lblProfile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// for (int i = 0; i < getComponents().length; i++) {
				// System.out.println(getComponents()[i].getClass());
				// }

				if (Arrays.asList(getComponents()).stream()
						.anyMatch(c -> (c instanceof UserProfile || c instanceof SinglePostView) && c != null)) {
					Arrays.asList(getComponents()).stream()
							.filter(c -> (c instanceof UserProfile || c instanceof SinglePostView) && c != null)
							.forEach(c -> {
								c.setVisible(false);
								remove(c);
								c = new UserProfile(contentPane.getBounds(), loggedUser, loggedUser);
								add(c);
							});
				}
			}
		});

		// Notification - Likes
		lblLikes = new JLabel();
		lblLikes.setIcon(new ImageIcon(Vars.icon_path + "empty_heart_small.png"));
		lblLikes.setBounds(lblProfile.getX() - Layout.component_margin * 2 - Layout.tab_icon_size,
				navBarVerticalAlignment - Layout.tab_icon_size / 2, Layout.tab_icon_size, Layout.tab_icon_size);
		add(lblLikes);

		// Show Likes that other users put to loggedUser posts
		lblLikes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				popupMenuLikes = new JPopupMenu();
				popupMenuLikes.setBackground(Color.WHITE);
				popupMenuLikes.setBounds(-50, lblLikes.getHeight() + Layout.component_margin, txtSearchUser.getWidth(),
						Layout.avatar_min_size * 5);

				for (int i = 0; i < 5; i++) {
					JComponent itemComponent = createLikesItem(popupMenuLikes,
							new Notification(user, loggedUser,
									new Post(loggedUser, "test", new GregorianCalendar().getTime(), new ArrayList<>(),
											new ArrayList<>(), new ImageIcon(Vars.post_path + "kendall1.png")),
									false, false, new GregorianCalendar().getTime()),
							i);

					popupMenuLikes.add(itemComponent);
				}

				popupMenuLikes.show(lblLikes, popupMenuLikes.getX(), popupMenuLikes.getY());
			}

		});

		// Explore - Feed
		lblExplore = new JLabel();
		lblExplore.setIcon(new ImageIcon(Vars.icon_path + "explore_small.png"));
		lblExplore.setBounds(lblLikes.getX() - Layout.component_margin * 2 - Layout.tab_icon_size,
				navBarVerticalAlignment - Layout.tab_icon_size / 2, Layout.tab_icon_size, Layout.tab_icon_size);
		add(lblExplore);

		// Go to loggedUser's ProfileView
		lblExplore.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (Arrays.asList(getComponents()).stream()
						.anyMatch(c -> (c instanceof UserProfile || c instanceof SinglePostView) && c != null)) {
					Arrays.asList(getComponents()).stream()
							.filter(c -> (c instanceof UserProfile || c instanceof SinglePostView) && c != null)
							.forEach(c -> {
								c.setVisible(false);
								remove(c);
								c = new SinglePostView(contentPane.getBounds(),
										new Post(loggedUser, "TEST", new GregorianCalendar().getTime(),
												new ArrayList<>(), new ArrayList<>(),
												new ImageIcon(Vars.avatar_path + "kendall.png")),
										loggedUser);
								add(c);
							});
				}
			}
		});

		// Notifications - Inbox, New Follower
		lblNotifications = new JLabel();
		lblNotifications.setIcon(new ImageIcon(Vars.icon_path + "inbox_small.png"));
		lblNotifications.setBounds(lblExplore.getX() - Layout.component_margin * 2 - Layout.tab_icon_size,
				navBarVerticalAlignment - Layout.tab_icon_size / 2, Layout.tab_icon_size, Layout.tab_icon_size);

		add(lblNotifications);

		// Search Users
		txtSearchUser = new JTextField();
		txtSearchUser.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		txtSearchUser.setText("Search User");
		txtSearchUser.setHorizontalAlignment(SwingConstants.CENTER);
		txtSearchUser.setBackground(ColorPalette.white_background);
		txtSearchUser.setLocation(lblSquarelogo.getX() + lblSquarelogo.getWidth() + Layout.component_margin,
				navBarVerticalAlignment - (Layout.label_default_height + 4) / 2);
		txtSearchUser.setSize(lblNotifications.getX() - Layout.component_margin * 3 - txtSearchUser.getX(),
				Layout.label_default_height + 4);

		txtSearchUser.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					popupMenuSearch = new JPopupMenu();
					popupMenuSearch.setBackground(Color.WHITE);
					popupMenuSearch.setBounds(0, txtSearchUser.getHeight(), txtSearchUser.getWidth(),
							Layout.avatar_min_size * 10);

					// Just testing functionalities, still need to get datas
					// form DB
					// Max results -> 7, the 8th will give you a bad surprise
					for (int i = 0; i < displayed_items_searchBar; i++) {
						JComponent itemComponent = createSearchResultItem(loggedUser, txtSearchUser, i);

						itemComponent.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseExited(MouseEvent e) {
								itemComponent.setBackground(Color.WHITE);
							}

							@Override
							public void mouseEntered(MouseEvent e) {
								itemComponent.setBackground(ColorPalette.light_gray_selected_item);
							}

							@Override
							public void mouseClicked(MouseEvent e) {
								if (Arrays.asList(getComponents()).contains(userView) && userView != null) {
									userView.setVisible(false);
									remove(userView);
									userView = new UserProfile(contentPane.getBounds(), loggedUser, loggedUser);
									add(userView);
								}
							}
						});
						popupMenuSearch.add(itemComponent);
					}

					// TODO find a way to align this lbl to the center of the
					// PopupMenu
					JLabel lblShowMore = new JLabel("Show More");
					lblShowMore.setForeground(Color.BLUE);
					lblShowMore.setHorizontalAlignment(JLabel.CENTER);
					lblShowMore.setVerticalAlignment(JLabel.CENTER);
					lblShowMore.setSize(txtSearchUser.getWidth(), Layout.label_default_height);

					lblShowMore.addMouseListener(new MouseAdapter() {
						Font original;

						@Override
						public void mouseExited(MouseEvent e) {
							// Reset default font
							e.getComponent().setFont(original);
						}

						@SuppressWarnings({ "rawtypes", "unchecked" })
						@Override
						public void mouseEntered(MouseEvent e) {
							// Derive default font, apply underline and set it
							// to the label
							original = e.getComponent().getFont();
							Map attributes = original.getAttributes();
							attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
							e.getComponent().setFont(original.deriveFont(attributes));
						}

						@Override
						public void mouseClicked(MouseEvent e) {
							// TODO create small frame where the user can see
							// other results
						}
					});
					popupMenuSearch.add(lblShowMore);

					add(popupMenuSearch);
					popupMenuSearch.show(txtSearchUser, popupMenuSearch.getX(), popupMenuSearch.getY());
				}
			}
		});

		add(txtSearchUser);
		DataValidation.deleteTextOnFocusGained(txtSearchUser, Vars.placeholder_search);

		// Separator
		jsNavBar = new JSeparator(SwingConstants.HORIZONTAL);
		jsNavBar.setLocation(0, Layout.persistentNavBar_height + Layout.component_margin * 2);
		jsNavBar.setSize(container.getWidth(), 2);
		jsNavBar.setBackground(ColorPalette.light_blue_separator);
		add(jsNavBar);

		// Background
		backgroundNavBar = new JPanel();
		backgroundNavBar.setBounds(getX(), getY(), getWidth(),
				Layout.persistentNavBar_height + Layout.component_margin * 2);
		backgroundNavBar.setBackground(Color.WHITE);
		add(backgroundNavBar);

		// -------------------------------------------------------
		// Persistent Menu
		// -------------------------------------------------------
		int menuBarY = getWidth() - Layout.persistentMenu_height - Layout.component_margin * 2;

		// New post - SharePost
		lblNewPost = new JLabel();
		lblNewPost.setIcon(new ImageIcon(Vars.icon_path + "camera_small.png"));
		lblNewPost.setSize(Layout.tab_icon_size, Layout.tab_icon_size);
		lblNewPost.setLocation(

				getWidth() / 2 - lblNewPost.getWidth() / 2, menuBarY + Layout.component_margin);
		add(lblNewPost);

		lblNewPost.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SharePost sp = new SharePost(loggedUser);
				sp.setVisible(true);
			}
		});

		// Logged user info
		lblLoginInfo = new JLabel(
				"Logged in as " + loggedUser.getUsername() + " at " + new GregorianCalendar().getTime());
		lblLoginInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginInfo.setFont(new Font("Droid Sans", Font.PLAIN, 11));
		lblLoginInfo.setSize(getWidth(), Layout.label_default_height);
		lblLoginInfo.setLocation(0, lblNewPost.getY() + lblNewPost.getHeight() + Layout.component_margin / 2);
		add(lblLoginInfo);

		// Separator
		jsMenu = new JSeparator(SwingConstants.HORIZONTAL);
		jsMenu.setLocation(getX(), menuBarY);
		jsMenu.setSize(container.getWidth(), 2);
		jsMenu.setBackground(ColorPalette.light_blue_separator);
		add(jsMenu);

		// Background
		backgroundMenu = new JPanel();
		backgroundMenu.setLayout(null);
		backgroundMenu.setBounds(getX(), menuBarY, getWidth(), Layout.persistentMenu_height);
		backgroundMenu.setBackground(Color.WHITE);
		add(backgroundMenu);

		// Steal the focus when the framework starts
		focusGrabber = new JButton();
		focusGrabber.grabFocus();
		focusGrabber.setBounds(0, 0, 0, 0);
		add(focusGrabber);
	}

	// TODO connection to DB
	public HashMap<String, JComponent> getSearchList(String textTyped) {
		// TODO implement a method that returns a list of users/hashtags based
		// on the string typed in the search bar. Consider the implementation of
		// a sorting algorithm i.e. followers first, then unknown users
		return null;
	}

	// Create item that will be inserted in the popupMenuLikes
	public JComponent createLikesItem(JComponent container, Notification notification, int itemNum) {
		JLabel lblSender = new JLabel(notification.getSender().getUsername());
		JLabel lblText = new JLabel(Vars.liked);
		JLabel lblSenderProfilePicture = new JLabel();
		JLabel lblPostLiked = new JLabel();
		JPanel pnlItem = new JPanel();

		pnlItem.setLayout(null);
		pnlItem.setBackground(Color.WHITE);
		pnlItem.setBounds(0, pnlItem.getWidth() * itemNum, container.getWidth(),
				Layout.avatar_min_size + Layout.component_margin * 2);

		lblPostLiked.setBounds(Layout.component_margin, Layout.component_margin / 2, Layout.avatar_min_size,
				Layout.avatar_min_size);
		lblPostLiked
				.setIcon(RoundedImage.createRoundedImage(Layout.getScaledImage(notification.getLikedPost().getImage(),
						Layout.avatar_min_size, Layout.avatar_min_size)));
		pnlItem.add(lblPostLiked);

		lblSender.setLocation(lblPostLiked.getX() + lblPostLiked.getWidth() + Layout.component_margin * 2,
				lblPostLiked.getY() + lblPostLiked.getHeight() / 2 - Layout.label_default_height - 2);
		lblSender.setSize(pnlItem.getWidth() - lblPostLiked.getX() + lblPostLiked.getWidth(),
				Layout.label_default_height);
		pnlItem.add(lblSender);

		lblText.setLocation(lblSender.getX(), lblSender.getY() + lblSender.getHeight() + 2);
		lblText.setSize(lblSender.getSize());
		lblText.setForeground(ColorPalette.light_gray_foreground_text);
		pnlItem.add(lblText);
		pnlItem.setVisible(true);

		return pnlItem;
	}

	// Create item that will be inserted in the popupMenuSearch
	public JComponent createSearchResultItem(User userSearched, JTextField txtSearchUser, int itemNum) {
		JLabel lblProfilePicture = new JLabel();
		JLabel lblUsername = new JLabel(userSearched.getUsername());
		JLabel lblName = new JLabel(userSearched.getName());
		JPanel pnlItem = new JPanel();

		pnlItem.setLayout(null);
		pnlItem.setBackground(Color.WHITE);
		pnlItem.setBounds(0, pnlItem.getWidth() * itemNum, txtSearchUser.getWidth(),
				Layout.avatar_min_size + Layout.component_margin * 2);

		lblProfilePicture.setBounds(Layout.component_margin, Layout.component_margin / 2, Layout.avatar_min_size,
				Layout.avatar_min_size);
		lblProfilePicture.setIcon(RoundedImage.createRoundedImage(Layout
				.getScaledImage(userSearched.getProfilePicture(), Layout.avatar_min_size, Layout.avatar_min_size)));
		pnlItem.add(lblProfilePicture);

		lblUsername.setLocation(lblProfilePicture.getX() + lblProfilePicture.getWidth() + Layout.component_margin * 2,
				lblProfilePicture.getY() + lblProfilePicture.getHeight() / 2 - Layout.label_default_height - 2);
		lblUsername.setSize(pnlItem.getWidth() - lblProfilePicture.getX() + lblProfilePicture.getWidth(),
				Layout.label_default_height);
		pnlItem.add(lblUsername);

		lblName.setLocation(lblUsername.getX(), lblUsername.getY() + lblUsername.getHeight() + 2);
		lblName.setSize(lblUsername.getSize());
		lblName.setForeground(ColorPalette.light_gray_foreground_text);
		pnlItem.add(lblName);
		pnlItem.setVisible(true);

		return pnlItem;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changes.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changes.removePropertyChangeListener(listener);
	}
}
