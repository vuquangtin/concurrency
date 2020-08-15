package os.mechanical.sympathy;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreadWithAtomic {
	public void singleThreadWithCAS(int MAX) {
		AtomicInteger counter = new AtomicInteger(0);
		for (int i = 0; i < MAX; i++) {
			counter.getAndIncrement();
		}
	}

	public void twoThreadWithCAS(int MAX) throws Exception {
		AtomicInteger sharedCounter = new AtomicInteger(0);
		// First thread
		final Thread t1 = new Thread(() -> {
			for (int i = 0; i < MAX / 2; i++) {
				sharedCounter.getAndIncrement();
			}
		});

		// Second thread
		final Thread t2 = new Thread(() -> {
			for (int i = 0; i < MAX / 2; i++) {
				sharedCounter.getAndIncrement();
			}
		});

		// Start threads
		t1.start();
		t2.start();

		// Wait threads
		t1.join();
		t2.join();
	}
}
