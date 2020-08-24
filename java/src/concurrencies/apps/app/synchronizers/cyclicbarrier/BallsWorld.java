package app.synchronizers.cyclicbarrier;

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
public class BallsWorld extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int xSize = 750;
	private final int ySize = 750;

	private final static Color BGCOLOR = Color.white;

	private ArrayList<Ball> balls = new ArrayList<Ball>();

	public BallsWorld() {
		setPreferredSize(new Dimension(xSize, ySize));
		setName("BallsWorld");
		setOpaque(true);
		setBackground(BGCOLOR);
	}

	//
	// In order to access the balls collection only
	// from one thread, let it be modified inside
	// the GUI thread.
	//
	public void addBall(final Ball b) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				balls.add(b);
				repaint();
			}
		});
	}

	//
	// This is run from the GUI thread and
	// reads the bolas collection.
	//
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Ball b : balls)
			b.draw(g);
	}
}