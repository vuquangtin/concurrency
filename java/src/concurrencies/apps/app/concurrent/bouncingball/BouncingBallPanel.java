package app.concurrent.bouncingball;

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
public class BouncingBallPanel extends JPanel {

	// Box height and width
	int width;
	int height;

	// Ball Size
	float radius = 40;
	float diameter = radius * 2;

	// Center of Call
	float X = radius + 50;
	float Y = radius + 20;

	// Direction
	float dx = 3;
	float dy = 3;

	Color c;

	public void setColor(Color c) {
		this.c = c;
	}

	public BouncingBallPanel(float radius, Color color) {
		this.radius = radius;
		this.c = color;
	}

	public BouncingBallPanel() {

		ExecutorService executorService = Executors.newCachedThreadPool();

		executorService.execute(new Runnable() {

			@Override
			public void run() {

				while (true) {

					width = getWidth();
					height = getHeight();

					X = X + dx;
					Y = Y + dy;

					if (X - radius < 0) {
						dx = -dx;
						X = radius;
					} else if (X + radius > width) {
						dx = -dx;
						X = width - radius;
					}

					if (Y - radius < 0) {
						dy = -dy;
						Y = radius;
					} else if (Y + radius > height) {
						dy = -dy;
						Y = height - radius;
					}
					repaint();

					try {
						Thread.sleep(50);
					} catch (InterruptedException ex) {
					}

				}

			}
		});

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.GRAY);
		g.fillOval((int) (X - radius) - 10, (int) (Y - radius) + 5,
				(int) diameter, (int) diameter);

		g.setColor(c);
		g.fillOval((int) (X - radius), (int) (Y - radius), (int) diameter,
				(int) diameter);

	}
}