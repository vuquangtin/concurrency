package app.synchronizers.phaser;

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.Phaser;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Ball implements Runnable {

	BallWorld world;

	private volatile boolean visible = false;

	private int xpos, ypos, xinc, yinc;

	public Color getCol() {
		return col;
	}

	private final Color col;
	private final Phaser phaser;

	private final static int BALLW = 10;
	private final static int BALLH = 10;

	public Ball(BallWorld world, int xpos, int ypos, int xinc, int yinc,
			Color col, Phaser phaser) {

		this.world = world;
		this.xpos = xpos;
		this.ypos = ypos;
		this.xinc = xinc;
		this.yinc = yinc;
		this.col = col;
		this.phaser = phaser;
		world.addBall(this);
	}

	@Override
	public void run() {
		this.visible = true;
		try {
			while (true) {
				move();
			}
		} catch (InterruptedException e) {
			// Пока ничего:)
			phaser.arriveAndDeregister();
		}
	}

	// private final static CyclicBarrier barrier = new CyclicBarrier(4);

	private boolean intersection() {
		boolean result = false;
		for (int x = xpos; x <= xpos + BALLW; x++) {
			if (x >= ypos && x <= ypos + BALLH) {
				result = true;
				break;
			}
		}
		return result;
	}

	public void move() throws InterruptedException {
		if (xpos >= world.getWidth() - BALLW || xpos <= 0)
			xinc = -xinc;

		if (ypos >= world.getHeight() - BALLH || ypos <= 0)
			yinc = -yinc;

		if (intersection())
			phaser.arriveAndAwaitAdvance();

		Thread.sleep(30);
		doMove();
		world.repaint();
	}

	public synchronized void doMove() {
		xpos += xinc;
		ypos += yinc;
	}

	public synchronized void draw(Graphics g) {
		if (visible) {
			g.setColor(col);
			g.fillOval(xpos, ypos, BALLW, BALLH);
		}
	}
}
