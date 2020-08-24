package app.synchronizers.phaser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class BallWorld extends JPanel {

	private final int xSize = 250;
	private final int ySize = 250;

	private final static Color BGCOLOR = Color.white;

	private ArrayList<Ball> balls = new ArrayList<Ball>();

	public BallWorld() {
		setPreferredSize(new Dimension(xSize, ySize));
		setOpaque(true);
		setBackground(BGCOLOR);
	}

	public void addBall(final Ball b) {
		synchronized (this) {
			balls.add(b);
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				repaint();
			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		synchronized (this) {
			for (Ball b : balls)
				b.draw(g);
		}
	}

	public int countBoll() {
		return balls.size();
	}

	public void remove(Ball b) {
		synchronized (this) {
			balls.remove(b);
			repaint();
		}
	}
}
