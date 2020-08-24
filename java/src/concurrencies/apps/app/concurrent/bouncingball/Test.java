package app.concurrent.bouncingball;

import javax.swing.JFrame;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Test extends JFrame {

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

	DrawBallPanel d = new DrawBallPanel(radius, diameter, X, Y);

	public Test() {

		Runnable runnable = new Runnable() {

			@Override
			public void run() {

				while (true) {

					width = d.getWidth();
					height = d.getHeight();

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
					d.repaint();

					try {
						Thread.sleep(50);
					} catch (InterruptedException ex) {
					}

				}
			}
		};
		add(d);
		Thread th = new Thread(runnable);
		th.start();

		// ExecutorService es = Executors.newCachedThreadPool();
		// es.execute(runnable);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 200);
	}

	public static void main(String[] args) {

		Test t = new Test();
		t.setVisible(true);
	}

}
