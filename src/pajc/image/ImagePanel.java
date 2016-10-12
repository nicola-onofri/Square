package pajc.image;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

class ImagePanel extends JComponent {
	private static final long serialVersionUID = 1L;
	private Image image;

	public ImagePanel(Image image, int frame_width, int frame_height) {
		// Get Scaled Image Instance
		Image scaledImage = image.getScaledInstance(frame_width, frame_height, Image.SCALE_SMOOTH);
		this.image = scaledImage;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}
}