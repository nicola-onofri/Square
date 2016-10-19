package pajc.square.appView;

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JScrollPane;

import pajc.config.ColorPalette;
import pajc.square.model.Post;
import pajc.square.model.User;

public class FeedView extends JScrollPane {
	private static final long serialVersionUID = 1L;
	private SinglePostView postView;

	public FeedView(Rectangle bounds, User loggedUser, ArrayList<Post> posts) {
		super();
		setLayout(null);
		setVisible(true);
		setBounds(bounds.getBounds());
		setBackground(ColorPalette.light_gray_background);

		// This component will contain a list of SinglePostViews getting them
		// from the DB. It will implement an algorithm to sort posts and it
		// will (eventually) have a btn at the end to change post view from
		// single post in the middle of the panel to gridLayout (more compact)

		// I've decided to use streams instead of for loops so that filtering
		// and sorting posts will be easier
		posts.stream().forEach(post -> {
			postView = new SinglePostView(bounds, post, loggedUser);
			add(postView);
		});

	}

}
