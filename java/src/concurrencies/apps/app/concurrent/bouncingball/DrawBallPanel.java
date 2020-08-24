package app.concurrent.bouncingball;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class DrawBallPanel extends JPanel implements Runnable {

	// ball size
	private float radius;
	private float diameter;

	// center of Call
	float X;
	float Y;

	public DrawBallPanel(float radius, float diameter, float X, float Y) {
		this.radius = radius;
		this.diameter = diameter;
		this.X = X;
		this.Y = Y;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.GRAY);
		g2d.fillOval((int) (X - radius) - 10, (int) (Y - radius) + 5,
				(int) diameter, (int) diameter);

		g2d.setColor(Color.BLUE);
		g2d.fillOval((int) (X - radius), (int) (Y - radius), (int) diameter,
				(int) diameter);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
