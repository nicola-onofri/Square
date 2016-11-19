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
	public static final int initial_x = 200;
	public static final int initial_y = 200;
	public static final int col1_initial_x = 100;
	public static final int col2_initial_x = 300;
	public static final int input_width = 200;
	public static final int input_height = 40;
	public static final int input_margin = 60;
	public static final int input_opacity = 85;
	public static final int slider_width = 170;
	public static final int slider_heigth = 39;

	public static final int textarea_width = 250;
	public static final int textarea_offset = 50;

	public static final int component_margin = 12;
	public static final int singlePost_border_size = 1;
	public static final int label_default_height = 15;
	public static final int label_default_width = 100;
	public static final int button_default_height = 25;
	public static final int button_default_width = 150;
	public static final int tab_icon_size = 30;
	public static final int separator_height = 2;
	public static final int mainFrame_width_offset = 0; // 0
	public static final int mainFrame_height_offset = 100; // 100

	// Media Path
	public static final int avatar_upload_size = 100;
	public static final int avatar_min_size = 50;
	public static final int profile_picture_size = 170;
	public static final int avatar_thumbnail_size = 30;
	public static final int post_thumbnail_size = 150;
	public static final int single_post_pnl_offset = 90; // 120
	public static final int single_post_size = 300;

	// Profile Header
	public static final int profile_header_text_x = 100;
	public static final int profile_header_text_y = 250;

	public static final int persistentNavBar_height = 55; // 55
	public static final int persistentMenu_height = 95; // 95
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
