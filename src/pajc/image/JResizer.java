package pajc.image;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

class JResizer extends JComponent implements PropertyChangeListener {
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private final int resizer_min_size = 100;

	// Constructor of JResizer
	public JResizer(DraggableComponent comp, JFrame frame) {
		this(comp, new DefaultResizableBorder(5));
		comp.addPropertyChangeListener(this);
		this.frame = frame;
	}

	// Constructor of JResizer
	public JResizer(Component comp, ResizableBorder border) {
		setLayout(new BorderLayout());
		add(comp);
		setBorder(border);
	}

	public void setBorder(Border border) {
		removeMouseListener(resizeListener);
		removeMouseMotionListener(resizeListener);

		if (border instanceof ResizableBorder) {
			addMouseListener(resizeListener);
			addMouseMotionListener(resizeListener);
		}
		super.setBorder(border);
	}

	void didResize() {
		if (getParent() != null) {
			getParent().repaint();
			invalidate();
			((JComponent) getParent()).revalidate();
		}
	}

	MouseInputListener resizeListener = new MouseInputAdapter() {
		private int cursor;
		private Point startPos = null;

		public void mouseMoved(MouseEvent me) {
			ResizableBorder border = (ResizableBorder) getBorder();
			setCursor(Cursor.getPredefinedCursor(border.getResizeCursor(me)));
		}

		public void mouseExited(MouseEvent mouseEvent) {
			setCursor(Cursor.getDefaultCursor());
		}

		public void mousePressed(MouseEvent me) {
			ResizableBorder border = (ResizableBorder) getBorder();
			cursor = border.getResizeCursor(me);
			startPos = me.getPoint();
		}

		public void mouseDragged(MouseEvent me) {
			if (startPos != null) {
				int dx = me.getX() - startPos.x;
				int dy = me.getY() - startPos.y;

				// Check whether the mouse is inside the frame
				if (isMouseInFrame(me)) {
					switch (cursor) {
					case Cursor.NW_RESIZE_CURSOR:
						if (applyLimitedSize(dx, dy) && getWidth() - dx > resizer_min_size) {
							setBounds(getX() + dx, getY() + dx, getWidth() - dx, getHeight() - dx);
							didResize();
						}
						break;
					case Cursor.NE_RESIZE_CURSOR:
						if (applyLimitedSize(dx, dy) && getWidth() + dx > resizer_min_size) {
							setBounds(getX(), getY() + dy, getWidth() - dy, getHeight() - dy);
							startPos = new Point(me.getX(), startPos.y);
							didResize();
						}
						break;
					case Cursor.SW_RESIZE_CURSOR:
						if (applyLimitedSize(dx, dy) && getWidth() - dx > resizer_min_size) {
							setBounds(getX() + dx, getY(), getWidth() - dx, getHeight() - dx);
							startPos = new Point(startPos.x, me.getY());
							didResize();
						}
						break;
					case Cursor.SE_RESIZE_CURSOR:
						if (applyLimitedSize(dx, dy) && getWidth() + dx > resizer_min_size) {
							setBounds(getX(), getY(), getWidth() + dx, getHeight() + dx);
							startPos = me.getPoint();
							didResize();
						}
						break;
					case Cursor.MOVE_CURSOR:
						if (applyLimitedSize(dx, dy)) {
							Rectangle bounds = getBounds();
							bounds.translate(dx, dy);
							setBounds(bounds);
							didResize();
						}
						break;
					}

					// Cursor shouldn't change while dragging
					setCursor(Cursor.getPredefinedCursor(cursor));
				}
			}
		}

		public boolean applyLimitedSize(int dx, int dy) {
			boolean resize = false;
			int frame_width = frame.getWidth();
			int frame_height = frame.getHeight();
			int resizer_size = getWidth();

			System.out.println("Frame Size: " + getWidth() + "x" + getHeight());
			System.out.println("Get X/Y: " + getX() + "x" + getY());
			System.out.println("Get dx/dy: " + dx + "x" + dy);

			if (getX() + dx > 0 && getY() + dy > 0 && getX() + resizer_size + dx < frame_width
					&& getY() + resizer_size + dy < frame_height)
				resize = true;

			return resize;
		}

		public boolean isMouseInFrame(MouseEvent me) {
			int frame_width = frame.getWidth();
			int frame_height = frame.getHeight();
			int frame_x = (int) frame.getLocationOnScreen().getX();
			int frame_y = (int) frame.getLocationOnScreen().getY();
			int mouse_x = me.getXOnScreen();
			int mouse_y = me.getYOnScreen();

			if (mouse_x > frame_x && mouse_x < frame_x + frame_width && mouse_y > frame_y
					&& mouse_y < frame_y + frame_height)
				return true;

			return false;
		}

		public void mouseReleased(MouseEvent mouseEvent) {
			startPos = null;
		}
	};

	@Override
	public void propertyChange(PropertyChangeEvent changeEvt) {
		setLocation((Point) changeEvt.getNewValue());
	}
}