package pajc.square.appView;

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pajc.config.ColorPalette;
import pajc.config.Layout;
import pajc.square.model.Post;
import pajc.square.model.User;

public class FeedView extends JScrollPane {
	private static final long serialVersionUID = 1L;
	private SinglePostView postView;

	public FeedView(Rectangle bounds, User loggedUser, ArrayList<Post> posts) {
		setBounds(bounds.getBounds());
		setLayout(null);
		setBorder(null);
		setVisible(true);
		setBackground(ColorPalette.light_gray_background);

		// This component will contain a list of SinglePostViews getting them
		// from the DB. It will implement an algorithm to sort posts and it
		// will (eventually) have a btn at the end to change post view from
		// single post in the middle of the panel to gridLayout (more compact)

		System.out.println("ScrollPane " + getBounds());
		// System.out.println("Bounds: " + bounds);

		for (int index = 0; index < posts.size(); index++) {
			postView = new SinglePostView(
					new Rectangle(100, (int) (bounds.getHeight()) + index * Layout.component_margin,
							(int) bounds.getWidth(), (int) bounds.getHeight()),
					posts.get(index), loggedUser);
			postView.setVisible(true);
			add(postView);
		}
	}

}
