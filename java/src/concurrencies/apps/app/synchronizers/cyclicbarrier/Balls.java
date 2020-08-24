package app.synchronizers.cyclicbarrier;

import java.awt.Color;
import java.util.concurrent.CyclicBarrier;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * https://github.com/Viktitors/Concurrent-Assignment3/tree/master
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Balls {

	public static void nap(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			//
			// Print out the name of the tread that caused this.
			//
			System.err.println("Thread " + Thread.currentThread().getName()
					+ " throwed exception " + e.getMessage());
		}
	}

	public static void main(String[] a) {

		final BallsWorld world = new BallsWorld();
		CyclicBarrier barrier1 = new CyclicBarrier(6); // CREATE THE BARRIER
		final JFrame win = new JFrame();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				win.getContentPane().add(world);
				win.pack();
				win.setVisible(true);
			}
		});

		Thread.currentThread().setName("MyMainThread");

		nap((int) (5000 * Math.random()));
		new Ball(world, 50, 80, 5, 10, Color.red, barrier1).start(); // CALL IT
																		// FOR
																		// ALL
																		// THE
																		// BALLS
		nap((int) (5000 * Math.random()));
		new Ball(world, 70, 100, 8, 6, Color.blue, barrier1).start();
		nap((int) (5000 * Math.random()));
		new Ball(world, 150, 100, 9, 7, Color.green, barrier1).start();
		nap((int) (5000 * Math.random()));
		new Ball(world, 200, 130, 3, 8, Color.black, barrier1).start();
		nap((int) (5000 * Math.random()));
		new Ball(world, 220, 130, 1, 8, Color.yellow, barrier1).start();
		nap((int) (5000 * Math.random()));
		new Ball(world, 400, 130, 20, 2, Color.ORANGE, barrier1).start();
		nap((int) (5000 * Math.random()));
	}
}