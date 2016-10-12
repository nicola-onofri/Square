package pajc.square.appView;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pajc.config.Layout;
import pajc.config.Vars;
import pajc.square.model.Post;
import pajc.square.model.User;

public class PostGrid extends JPanel implements PropertyChangeListener {
	private static final long serialVersionUID = 1L;
	private final int IMAGES_PER_ROW = 4;
	private ArrayList<ImageIcon> postsThumbnails;
	private PropertyChangeSupport changes;
	private GridBagLayout gridBagLayout;
	private JFrame postDetailsFrame;

	// PostGrid Constructor
	public PostGrid(User loggedUser, User user) {
		this.changes = new PropertyChangeSupport(this);
		this.postsThumbnails = new ArrayList<>();

		gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);

		// Debug
		 addPost(this, new Post(user, "test", new
		 GregorianCalendar().getTime(), new ArrayList<>(), new ArrayList<>(),
		 new ImageIcon(Vars.post_path + "wiz_2.png")), loggedUser, user);
		 addPost(this, new Post(user, "test", new
		 GregorianCalendar().getTime(), new ArrayList<>(), new ArrayList<>(),
		 new ImageIcon(Vars.post_path + "wiz_3.png")), loggedUser, user);
		 addPost(this, new Post(user, "test", new
		 GregorianCalendar().getTime(), new ArrayList<>(), new ArrayList<>(),
		 new ImageIcon(Vars.post_path + "wiz_4.png")), loggedUser, user);
		 addPost(this, new Post(user, "test", new
		 GregorianCalendar().getTime(), new ArrayList<>(), new ArrayList<>(),
		 new ImageIcon(Vars.post_path + "wiz_5.png")), loggedUser, user);
		 addPost(this, new Post(user, "test", new
		 GregorianCalendar().getTime(), new ArrayList<>(), new ArrayList<>(),
		 new ImageIcon(Vars.post_path + "wiz_6.png")), loggedUser, user);
		 addPost(this, new Post(user, "test", new
		 GregorianCalendar().getTime(), new ArrayList<>(), new ArrayList<>(),
		 new ImageIcon(Vars.post_path + "wiz_7.png")), loggedUser, user);
		 addPost(this, new Post(user, "test", new
		 GregorianCalendar().getTime(), new ArrayList<>(), new ArrayList<>(),
		 new ImageIcon(Vars.post_path + "wiz_8.png")), loggedUser, user);
		 addPost(this, new Post(user, "test", new
		 GregorianCalendar().getTime(), new ArrayList<>(), new ArrayList<>(),
		 new ImageIcon(Vars.post_path + "wiz_9.png")), loggedUser, user);

	}

	public void addPost(PostGrid pg, Post post, User loggedUser, User user) {
		ImageIcon resizedThumbnail = Layout.getScaledImage(post.getImage(), Layout.post_thumbnail_size,
				Layout.post_thumbnail_size);

		JLabel lblIcon = new JLabel();
		lblIcon.setIcon(resizedThumbnail);
		lblIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				postDetailsFrame = new JFrame(post.getOwner().getUsername() + "'s post");
				postDetailsFrame.setBounds(Layout.dim.width / 4, Layout.dim.height / 4, Layout.dim.width / 2,
						Layout.dim.height / 2);
				postDetailsFrame.addPropertyChangeListener(pg);
				PostDetails pd = new PostDetails(postDetailsFrame, post, loggedUser, user);
				pd.addPropertyChangeListener(pg);
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

		GridBagConstraints gbc = new GridBagConstraints();
		int previousArraySize = loggedUser.getPosts().size();

		// View
		gbc.gridx = previousArraySize % IMAGES_PER_ROW;
		gbc.gridy = (int) (previousArraySize) / IMAGES_PER_ROW;
		gbc.insets = new Insets(5, 5, 5, 5);
		add(lblIcon, gbc);

		// Add Post in user
		// loggedUser.getPosts().add(new Post(loggedUser, "wiz", new
		// GregorianCalendar().getTime(), new ArrayList<>(),
		// new ArrayList<>(), resizedThumbnail));

		loggedUser.getPosts().add(new Post(loggedUser, "wiz", new GregorianCalendar().getTime(), new ArrayList<>(),
				new ArrayList<>(), resizedThumbnail));
}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		System.out.println(listener.getClass().getSimpleName());
		changes.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changes.removePropertyChangeListener(listener);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println(evt.getPropertyName() + "- PostGrid");
		firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
	}
}
