package learn.threads.vn.docs2;

import java.util.Random;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class WorkingThread implements Runnable {

	@Override
	public void run() {
		while (true) {
			processSomething();
		}
	}

	private void processSomething() {
		try {
			System.out.println("Processing working thread");
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Random r = new Random();
		int i = r.nextInt(100);
		if (i > 70) {
			throw new RuntimeException(
					"Simulate an exception was not handled in the thread");
		}
	}

}