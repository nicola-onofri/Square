package pajc.image;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;

public class DefaultResizableBorder implements ResizableBorder {
	private int dist = 5;
	int locations[] = { SwingConstants.NORTH_WEST, SwingConstants.NORTH_EAST, SwingConstants.SOUTH_WEST,
			SwingConstants.SOUTH_EAST, 0, -1, };

	int cursors[] = { Cursor.NW_RESIZE_CURSOR, Cursor.NE_RESIZE_CURSOR, Cursor.SW_RESIZE_CURSOR,
			Cursor.SE_RESIZE_CURSOR, Cursor.MOVE_CURSOR, Cursor.DEFAULT_CURSOR, };

	public DefaultResizableBorder(int dist) {
		this.dist = dist;
	}

	public Insets getBorderInsets(Component component) {
		return new Insets(dist, dist, dist, dist);
	}

	public boolean isBorderOpaque() {
		return false;
	}

	public void paintBorder(Component component, Graphics g, int x, int y, int w, int h) {
		g.setColor(Color.black);
		g.drawRect(x + dist / 2, y + dist / 2, w - dist, h - dist);
		for (int i = 0; i < locations.length - 2; i++) {
			Rectangle rect = getRectangle(x, y, w, h, locations[i]);
			g.setColor(Color.WHITE);
			g.fillRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
			g.setColor(Color.BLACK);
			g.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
		}
	}

	private Rectangle getRectangle(int x, int y, int w, int h, int location) {
		switch (location) {
		case SwingConstants.NORTH_WEST:
			return new Rectangle(x, y, dist, dist);
		case SwingConstants.NORTH_EAST:
			return new Rectangle(x + w - dist, y, dist, dist);
		case SwingConstants.SOUTH_WEST:
			return new Rectangle(x, y + h - dist, dist, dist);
		case SwingConstants.SOUTH_EAST:
			return new Rectangle(x + w - dist, y + h - dist, dist, dist);
		}
		return null;
	}

	public int getResizeCursor(MouseEvent me) {
		Component comp = me.getComponent();
		int w = comp.getWidth();
		int h = comp.getHeight();

		Rectangle bounds = new Rectangle(0, 0, w, h);

		if (!bounds.contains(me.getPoint()))
			return Cursor.DEFAULT_CURSOR;
		Rectangle actualBounds = new Rectangle(dist, dist, w - 2 * dist, h - 2 * dist);
		if (actualBounds.contains(me.getPoint()))
			return Cursor.DEFAULT_CURSOR;

		for (int i = 0; i < locations.length - 2; i++) {
			Rectangle rect = getRectangle(0, 0, w, h, locations[i]);
			if (rect.contains(me.getPoint()))
				return cursors[i];
		}

		return Cursor.MOVE_CURSOR;
	}
}