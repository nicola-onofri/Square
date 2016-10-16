package pajc.config;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Layout {

	// Components
	public final static int initial_x = 200;
	public final static int initial_y = 200;
	public final static int col1_initial_x = 100;
	public final static int col2_initial_x = 300;
	public final static int input_width = 200;
	public final static int input_height = 40;
	public final static int input_margin = 60;
	public final static int input_opacity = 85;
	public final static int slider_width = 170;
	public final static int slider_heigth = 39;

	public final static int textarea_width = 250;
	public final static int textarea_offset = 50;

	public final static int component_margin = 12;
	public final static int singlePost_border_size = 1;
	public final static int label_default_height = 15;
	public final static int button_default_height = 25;
	public final static int button_default_width = 150;
	public final static int tab_icon_size = 30;
	public final static int separator_height = 2;

	// Media
	public final static int avatar_upload_size = 100;
	public final static int avatar_min_size = 50;
	public final static int profile_picture_size = 170;
	public final static int avatar_thumbnail_size = 30;
	public final static int post_thumbnail_size = 150;
	public static final int single_post_size = 300;

	// Profile Header
	public final static int profile_header_text_x = 100;
	public final static int profile_header_text_y = 250;

	public static final int persistentNavBar_height = 60;
	public static final int persistentMenu_height = 100;
	public static final int header_shareFrame = 74;

	public static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

	// Image Rendering
	public static void applyQualityRenderingHints(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
	}

	public static ImageIcon getScaledImage(ImageIcon srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();

		g2.drawImage(srcImg.getImage(), 0, 0, w, h, null);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		applyQualityRenderingHints(g2);

		ImageIcon resizedImgIcon = new ImageIcon(resizedImg);
		g2.dispose();

		return resizedImgIcon;
	}

	/**
	 * @see <a href=
	 *      "https://groups.google.com/forum/#!topic/comp.lang.java.programmer/OI_IdebPL68">
	 *      https://groups.google.com/forum/#!topic/comp.lang.java.programmer/
	 *      OI_IdebPL68</a>
	 */
	public static ImageIcon convertIcon_ImageIcon(Icon icon) {
		if (icon instanceof ImageIcon)
			return new ImageIcon(((ImageIcon) icon).getImage());
		else {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gd = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gd.getDefaultConfiguration();

			BufferedImage image = gc.createCompatibleImage(icon.getIconWidth(), icon.getIconHeight());
			Graphics2D g = image.createGraphics();

			icon.paintIcon(null, g, 0, 0);
			g.dispose();
			return new ImageIcon(image);
		}
	}
}
