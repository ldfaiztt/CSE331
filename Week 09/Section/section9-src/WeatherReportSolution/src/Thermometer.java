import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

/**
 * A component to display a temperature visually using a thermometer.
 */
public class Thermometer extends JComponent {
	private static final long serialVersionUID = 1L;

	// Constants for the look-and-feel of the thermometer
	public static final Color FG = Color.RED;
	public static final Color BG = Color.WHITE;
	public static final int RADIUS = 8;
	public static final int TUBE_WIDTH = 8;
	public static final int WIDTH = 48;
	public static final int HEIGHT = 75;
	public static final int MARGIN = 10;

	// The percentage (from 0 to 100 inclusive) full the thermometer is.
	private int percentFull;

	public Thermometer() {
		percentFull = 75;
		this.setPreferredSize(new Dimension(WIDTH + MARGIN, HEIGHT + MARGIN));
	}

	/**
	 * Sets the percent full of this thermometer.
	 * 
	 * @param percent
	 *            the percent full of the thermometer
	 * @throws IllegalArgumentException
	 *             if not 0 <= percent <= 100
	 */
	public void setPercentFull(int percent) {
		if (percent > 100 | percent < 0)
			throw new IllegalArgumentException("Invalid percent");
		percentFull = percent;

		// Since our state has updated, we need to repaint.
		repaint();
	}

	/**
	 * Paints this component on the given Graphics.
	 * 
	 * @param g
	 *            the graphics to use when painting
	 */
	@Override
	public void paintComponent(Graphics g) {
		// The superclass' paintComponent must always be called first.
		super.paintComponent(g);

		// An ugly but permissible cast, since we have knowledge of Swing's
		// implementation
		Graphics2D g2d = (Graphics2D) g;

		int xOff = this.getWidth() / 2 - WIDTH / 2;
		int yOff = this.getHeight() / 2 - HEIGHT / 2;

		int maxHeight = HEIGHT - RADIUS;
		int tempHeight = percentFull * maxHeight / 100;

		// Draw the white background rectangle
		g2d.setColor(BG);
		g2d.fillRect(xOff + WIDTH / 2 - TUBE_WIDTH / 2, yOff, TUBE_WIDTH,
				maxHeight);

		// Draw the circular base
		g2d.setColor(FG);
		g2d.fillOval(xOff + WIDTH / 2 - RADIUS, yOff + HEIGHT - 2 * RADIUS,
				RADIUS * 2, RADIUS * 2);

		// Draw the thermometer
		g2d.fillRect(xOff + WIDTH / 2 - TUBE_WIDTH / 2, yOff + maxHeight
				- tempHeight, TUBE_WIDTH, tempHeight);

	}
}
