package pajc.square.appView;

import javax.swing.JPanel;

import pajc.config.Layout;

import javax.swing.JLabel;

public class SinglePostView extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel lblPostPicture;

	public SinglePostView() {
		setLayout(null);
		setSize(Layout.single_post_size + Layout.component_margin, Layout.single_post_size + Layout.component_margin);

		lblPostPicture = new JLabel("New label");
		lblPostPicture.setBounds(Layout.component_margin, Layout.component_margin, Layout.single_post_size,
				Layout.single_post_size);
		add(lblPostPicture);

	}

}
