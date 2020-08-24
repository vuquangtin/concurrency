package app.synchronizers.countdownlatch;

import java.awt.Dimension;
import java.util.concurrent.CountDownLatch;

import javax.swing.JComponent;
import javax.swing.JProgressBar;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ProgressThread extends Thread {
	public static final int PROGRESS_WIDTH = 350;
	private static final int CATCH_UP_COUNT = 75;
	private final JProgressBar progressBar;
	private final CountDownLatch latch;
	private final int slowness;

	public ProgressThread(CountDownLatch latch, int slowness) {
		this.latch = latch;
		this.slowness = slowness;
		progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(PROGRESS_WIDTH - 30, 25));
		progressBar.setStringPainted(true);
	}

	JComponent getProgressComponent() {
		return progressBar;
	}

	@Override
	public void run() {

		int c = 0;
		while (true) {
			progressBar.setValue(++c);
			if (c > 100) {
				break;
			}

			try {
				Thread.sleep(c < CATCH_UP_COUNT ? slowness : 100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (c == CATCH_UP_COUNT) {
				// decrease the count
				latch.countDown();
				try {
					// wait until count = 0
					latch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}