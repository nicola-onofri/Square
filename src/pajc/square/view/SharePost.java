package pajc.square.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import pajc.config.ColorPalette;
import pajc.config.DataValidation;
import pajc.config.FileHelpers;
import pajc.config.Layout;
import pajc.config.RoundedImage;
import pajc.config.Vars;
import pajc.image.ImageResizer;
import pajc.square.model.Post;
import pajc.square.model.User;

//Share post page
public class SharePost extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton btnSubmit;
	private JButton btnSearch;
	private JLabel lblMask;
	private JLabel lblProfile;
	private JLabel lblUsername;
	private JLabel lblImage;
	private JPanel pnlHeader;
	private JPanel pnlFooter;
	private JSeparator jsHeader;
	private JSeparator jsFooter;
	private JTextArea txtCaption;

	public SharePost(User loggedUser) {
		setTitle("New Post");
		// TODO magic numbers
		setSize(360, 593);
		setResizable(false);
		setLocation(Layout.dim.width / 2 - getWidth() / 2, Layout.dim.height / 2 - getHeight() / 2);
		getContentPane().setLayout(null);
		getContentPane().setBackground(ColorPalette.white_background);

		// Header section
		pnlHeader = new JPanel();
		pnlHeader.setBackground(Color.WHITE);
		pnlHeader.setBounds(0, 0, getWidth(), Layout.header_shareFrame);
		getContentPane().add(pnlHeader);
		pnlHeader.setLayout(null);

		// Circlular frame
		lblMask = new JLabel();

		lblMask.setLocation(Layout.component_margin, pnlHeader.getHeight() / 2 - lblMask.getHeight() / 2);
		pnlHeader.add(lblMask);

		// Profile picture
		lblProfile = new JLabel();
		lblProfile.setSize(Layout.avatar_min_size, Layout.avatar_min_size);
		lblProfile.setLocation(Layout.component_margin, pnlHeader.getHeight() / 2 - lblProfile.getHeight() / 2);
		lblProfile.setIcon(RoundedImage.createRoundedImage(
				Layout.getScaledImage(loggedUser.getProfilePicture(), lblProfile.getHeight(), lblProfile.getWidth())));
		pnlHeader.add(lblProfile);

		// Logged User username
		lblUsername = new JLabel(loggedUser.getUsername());
		lblUsername.setFont(new Font("Droid Sans", Font.PLAIN, 24));
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setSize(pnlHeader.getWidth() - lblProfile.getX(), Layout.label_default_height * 2);
		lblUsername.setLocation(lblProfile.getX(), pnlHeader.getHeight() / 2 - lblUsername.getHeight() / 2);
		pnlHeader.add(lblUsername);

		// Separator
		jsHeader = new JSeparator(SwingConstants.HORIZONTAL);
		jsHeader.setLocation(0, Layout.header_shareFrame);
		jsHeader.setSize(getWidth(), Layout.separator_height);
		jsHeader.setBackground(ColorPalette.light_blue_separator);
		getContentPane().add(jsHeader);

		// Uploaded Picture
		lblImage = new JLabel();
		lblImage.setBounds(0, pnlHeader.getHeight(), getWidth(), getWidth());
		lblImage.setBackground(Color.BLACK);
		lblImage.setOpaque(true);
		getContentPane().add(lblImage);

		// Separator
		jsFooter = new JSeparator();
		jsFooter = new JSeparator(SwingConstants.HORIZONTAL);
		jsFooter.setLocation(0, Layout.header_shareFrame + lblImage.getHeight());
		jsFooter.setSize(getWidth(), Layout.separator_height);
		jsFooter.setBackground(ColorPalette.light_blue_separator);
		getContentPane().add(jsFooter);

		// Footer Section
		pnlFooter = new JPanel();
		pnlFooter.setBackground(Color.WHITE);
		pnlFooter.setBounds(0, Layout.header_shareFrame + lblImage.getHeight(), getWidth(),
				getHeight() - Layout.header_shareFrame + lblImage.getHeight());
		getContentPane().add(pnlFooter);
		pnlFooter.setLayout(null);

		// User Caption
		txtCaption = new JTextArea();
		txtCaption.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		txtCaption.setText(Vars.placeholder_caption);
		txtCaption.setFocusable(true);
		txtCaption.setMargin(new Insets(2, 6, 0, 5));
		txtCaption.setLineWrap(true);
		txtCaption.setWrapStyleWord(true);
		txtCaption.setBackground(ColorPalette.white_background);
		txtCaption.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		txtCaption.setBounds(Layout.component_margin, Layout.component_margin, getWidth() - Layout.component_margin * 2,
				Layout.label_default_height * 5);
		pnlFooter.add(txtCaption);
		DataValidation.deleteTextOnFocusGained(txtCaption, Vars.placeholder_caption);

		// Submit Button
		btnSubmit = new JButton("Submit");
		btnSubmit.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		btnSubmit.setBounds(Layout.component_margin,
				txtCaption.getY() + txtCaption.getHeight() + Layout.component_margin,
				getWidth() / 2 - Layout.component_margin - Layout.component_margin / 2, Layout.button_default_height);
		pnlFooter.add(btnSubmit);

		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (lblImage.getIcon() != null && txtCaption.getText() != null
						&& !txtCaption.getText().equals(Vars.placeholder_caption)) {
					Post submittedPost = new Post(loggedUser, txtCaption.getText(), new GregorianCalendar().getTime(),
							new ArrayList<>(), new ArrayList<>(), Layout.convertIcon_ImageIcon(lblImage.getIcon()));
					System.out.println(submittedPost);
				} else
					JOptionPane.showMessageDialog(null, Vars.warning_no_icon, "Validation Error",
							JOptionPane.ERROR_MESSAGE);
			}
		});

		// Search Button
		btnSearch = new JButton("Search");
		btnSearch.grabFocus();
		btnSearch.setFont(new Font("Droid Sans", Font.PLAIN, 13));
		btnSearch.setBounds(getWidth() / 2 + Layout.component_margin / 2, btnSubmit.getY(), btnSubmit.getWidth(),
				Layout.button_default_height);
		pnlFooter.add(btnSearch);

		JFileChooser fileChooser = new JFileChooser();

		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Object[] options = { "Upload Image", "Webcam Capture" };
				int response = JOptionPane.showOptionDialog(null, "Select image src", "Image Chooser",
						JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

				if (response == 0) {
					try {
						int upload = fileChooser.showOpenDialog(null);

						// Filter Images
						fileChooser
								.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg"));

						if (upload == JFileChooser.APPROVE_OPTION) {
							File inputfile = fileChooser.getSelectedFile();
							BufferedImage in = ImageIO.read(inputfile);
							if (in != null) {
								String file_extension = FileHelpers.getFileExtension(inputfile.getName());
								ImageResizer resize = new ImageResizer(in, Vars.avatar_path, file_extension);
								File outputfile = new File(Vars.avatar_path + inputfile.getName());
								ImageIO.write(in, "jpg", outputfile);

								// Can't get the cropped file
								lblImage.setIcon(Layout.getScaledImage(new ImageIcon(in), lblImage.getWidth(),
										lblImage.getHeight()));
								System.out.println();
							} else
								JOptionPane.showMessageDialog(null, Vars.warning_file_format, "Validation Error",
										JOptionPane.WARNING_MESSAGE);
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
	}
}
