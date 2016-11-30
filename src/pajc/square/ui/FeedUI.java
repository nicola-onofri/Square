package pajc.square.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import pajc.config.Layout;
import pajc.square.model.Post;
import pajc.square.model.User;

public class FeedUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private final int IMAGES_PER_ROW = 2;
	private SinglePost postView;
	private GridBagConstraints gbc;
	private GridBagLayout gbl;

	// FeedUI
	public FeedUI(Rectangle bounds, User loggedUser, ArrayList<Post> posts) {
		int previousArraySize = loggedUser.getPosts().size();
		gbl = new GridBagLayout();

		// setBounds(bounds.getBounds());
		setLocation(0, 0);
		setSize((int) bounds.getWidth(), (int) bounds.getWidth());
		setLayout(gbl);
		setVisible(true);
		setBorder(null);
		setBackground(Color.BLACK);

		System.out.println("Posts: " + posts.size());

		for (int index = 0; index < posts.size(); index++) {
			postView = new SinglePost(new Rectangle(0, 0, (int) bounds.getWidth(), (int) bounds.getHeight()),
					posts.get(index), loggedUser);

			postView.setVisible(true);
			
			gbc = new GridBagConstraints();
			gbc.gridx = previousArraySize % IMAGES_PER_ROW;
			gbc.gridy = (int) (previousArraySize) / IMAGES_PER_ROW;
			gbc.insets = new Insets(5, 5, 5, 5);
			add(postView, gbc);
		}
	}

	public void addPost(PostGrid pg, Post post, User loggedUser, User user) {
		ImageIcon resizedThumbnail = Layout.getScaledImage(post.getImage(), Layout.post_thumbnail_size,
				Layout.post_thumbnail_size);
	}

}
