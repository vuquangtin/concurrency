package app.concurrent.locks;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class StarvationDemoNotLock {
	public static Object sharedObj = new Object();

	// public static ReentrantLock lock = new ReentrantLock(true);

	public static void main(String[] args) {
		JFrame frame = createFrame();
		frame.setLayout(new FlowLayout(FlowLayout.LEFT));

		for (int i = 0; i < 5; i++) {
			ProgressThread progressThread = new ProgressThread();
			frame.add(progressThread.getProgressBar());
			progressThread.start();
		}

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private static JFrame createFrame() {
		JFrame frame = new JFrame("Starvation Demo");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(300, 300));
		return frame;
	}

	private static class ProgressThread extends Thread {
		JProgressBar progressBar;

		ProgressThread() {
			progressBar = new JProgressBar();
			progressBar.setString(this.getName());
			progressBar.setStringPainted(true);
		}

		JProgressBar getProgressBar() {
			return progressBar;
		}

		@Override
		public void run() {
			int c = 0;
			while (true) {
				synchronized (sharedObj) {
					if (c == 100) {
						c = 0;
					}
					progressBar.setValue(++c);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
