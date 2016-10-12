package pajc.image;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.border.LineBorder;

import pajc.config.ColorPalette;

public class DraggableComponent extends JComponent {
	private static final long serialVersionUID = 1L;
	private PropertyChangeSupport changes = new PropertyChangeSupport(this);
	private int deltaMouseX = 0;
	private int deltaMouseY = 0;

	public DraggableComponent(JFrame frame) {
		setBorder(new LineBorder(ColorPalette.light_blue_background, 2));
		setBounds(0, 0, 50, 50);
		setOpaque(false);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent pressEvt) {
				// Distance between the mouseEvent & the initial location of the
				// border (parent component)
				deltaMouseX = pressEvt.getXOnScreen() - getParent().getX();
				deltaMouseY = pressEvt.getYOnScreen() - getParent().getY();
				setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			}

			@Override
			public void mouseReleased(MouseEvent releaseEvt) {
				setCursor(Cursor.getDefaultCursor());
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent dragEvt) {
				int deltaX = dragEvt.getXOnScreen() - deltaMouseX;
				int deltaY = dragEvt.getYOnScreen() - deltaMouseY;

				if (getX() + deltaX > 0 && getY() + deltaY > 0 && getX() + getWidth() + deltaX < frame.getWidth()
						&& getY() + getHeight() + deltaY < frame.getHeight())
					changes.firePropertyChange("Update JResizer Location", null, new Point(deltaX, deltaY));
			}
		});
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changes.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changes.removePropertyChangeListener(listener);
	}
}