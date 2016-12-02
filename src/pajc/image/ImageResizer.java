package pajc.image;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import pajc.config.FileHelpers;
import pajc.config.Vars;

public class ImageResizer extends JComponent {
	private static final long serialVersionUID = 1L;

	private Dimension screen;
	private DraggableComponent comp;
	private JButton btn_cancel;
	private JButton btn_crop;
	private JFrame frame;
	private JResizer resizer;
	private BufferedImage cropped_img;
	private String tmp_img_path = "";
	private PropertyChangeSupport changes = new PropertyChangeSupport(this);

	// Constructor
	public ImageResizer(BufferedImage src, String path, String file_ext) {

		screen = Toolkit.getDefaultToolkit().getScreenSize();

		double img_width = src.getWidth();
		double img_height = src.getHeight();
		double image_ratio = img_width / img_height;
		int frame_height = (int) (screen.getHeight() - 100);
		int frame_width = (int) (image_ratio * frame_height);

		frame = new JFrame("Resize Image");
		frame.setSize(frame_width, frame_height);
		frame.setContentPane(new ImagePanel(src, frame_width, frame_height));
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Cancel Button
		btn_cancel = new JButton("Cancel");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btn_cancel.setBounds(frame.getWidth() - 220, frame.getHeight() - 60, 100, 25);
		frame.getContentPane().add(btn_cancel);

		// DraggableComponent
		comp = new DraggableComponent(frame);
		comp.setBounds(100, 100, 200, 200);
		frame.getContentPane().add(comp);

		// JResizer
		resizer = new JResizer(comp, frame);
		resizer.setBounds(100, 100, 200, 200);
		frame.getContentPane().add(resizer, BorderLayout.CENTER);
		resizer.invalidate();

		// Crop Button
		btn_crop = new JButton("Crop");
		btn_crop.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Crop Confirmation
				int dialogResult = JOptionPane.showConfirmDialog(null, "Confirm Selection?", "Crop Confirmation",
						JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {

					// int x = resizer.getX(), y = resizer.getY(), w =
					// resizer.getWidth(), h = resizer.getHeight();
					int x = comp.getX(), y = comp.getY(), w = comp.getWidth(), h = comp.getHeight();
					
					BufferedImage dst = src.getSubimage(x, y, w, h);

					// Write bufferedImage in a local file
//					try {
//						if (!tmp_img_path.equals("")) {
//							File old_file = new File(tmp_img_path);
//							old_file.delete();
//							ImageIO.write(dst, file_ext, new File(file_path));
//							tmp_img_path = file_path;
//						} else {
//							ImageIO.write(dst, file_ext, new File(file_path));
//							tmp_img_path = file_path;
//						}
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					}

					firePropertyChange("Update Preview Image on SharePost", null, new ImageIcon(dst));
					System.out.println("Cropped and Saved");

					frame.setVisible(false);
					frame.dispose();
				}
			}
		});

		btn_crop.setBounds(frame.getWidth() - 120, frame.getHeight() - 60, 100, 25);
		frame.getContentPane().add(btn_crop);
	}

	String file_name = FileHelpers.getUniqueFileName();
	String file_path = Vars.media_temp_path + "/" + file_name + ".jpg";

	// Getters and Setters
	public BufferedImage getCropped_img() {
		return cropped_img;
	}

	public void setCropped_img(BufferedImage cropped_img) {
		this.cropped_img = cropped_img;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changes.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changes.removePropertyChangeListener(listener);
	}

}