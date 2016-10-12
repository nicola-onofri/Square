package pajc.square.appView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import pajc.config.FileHelpers;
import pajc.config.Layout;
import pajc.config.Vars;
import pajc.image.ImageResizer;

public class AvatarUpload extends JPanel {
	private static final long serialVersionUID = 1L;

	public AvatarUpload() throws IOException {
		setOpaque(false);

		// Upload Button
		JButton uploadButton = new JButton();
		add(uploadButton);
		uploadButton.setBounds(Layout.initial_x + 160, Layout.initial_y + Layout.input_margin, 40, 40);
		ImageIcon upload_button_icon = new ImageIcon(Vars.icon_path + "camera_upload.png");
		uploadButton.setIcon(upload_button_icon);
		uploadButton.setOpaque(false);
		uploadButton.setContentAreaFilled(false);
		uploadButton.setBorderPainted(false);

		JFileChooser fileChooser = new JFileChooser();

		uploadButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				Object[] options = { "Upload Image", "Webcam Capture" };
				int response = JOptionPane.showOptionDialog(null, "Select image src", "Image Chooser",
						JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

				if (response == 0) {
					try {
						int upload = fileChooser.showOpenDialog(AvatarUpload.this);

						// Filter Images
						FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif",
								"jpeg");
						// fileChooser.setFileFilter(filter);

						if (upload == JFileChooser.APPROVE_OPTION) {
							File inputfile = fileChooser.getSelectedFile();
							BufferedImage in = ImageIO.read(inputfile);
							if (in != null) {
								String file_extension = FileHelpers.getFileExtension(inputfile.getName());
								ImageResizer resize = new ImageResizer(in, Vars.avatar_path, file_extension);
//								File outputfile = new File(Vars.AVATAR_PATH + inputfile.getName());
//								ImageIO.write(in, "jpg", outputfile);
							} else
								JOptionPane.showMessageDialog(null, "Formato dell'immagine non valido!",
										"Validation Error", JOptionPane.WARNING_MESSAGE);
						}
					} catch (Exception ex) {
					}
				}
				// Webcam Image Capture
				else if (response == 1) {
					Webcam webcam = Webcam.getDefault();
					webcam.setViewSize(WebcamResolution.VGA.getSize());

					WebcamPanel panel = new WebcamPanel(webcam);
					panel.setFPSDisplayed(true);
					panel.setDisplayDebugInfo(true);
					panel.setImageSizeDisplayed(true);
					panel.setMirrored(true);

					JFrame window = new JFrame("Capture Image");
					window.add(panel);
					window.setResizable(true);
					window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					window.pack();
					window.setVisible(true);
				}
			}
		});

		// add(fileChooser);
//		ImageIcon avatar_img = RoundedImage.newRoundedImage("res/polina_profile.jpg");
//		JLabel label = new JLabel(RoundedImage.newRoundedImage("res/polina_profile.jpg"));
//		label.setBounds(this.getWidth() / 2, this.getWidth() / 2, this.getWidth(), this.getHeight());
		// add(label);
	}

}
