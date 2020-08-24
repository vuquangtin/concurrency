package app.synchronizers.countdownlatch;

import java.util.concurrent.CountDownLatch;
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
public class Study08AtomicInteger {
	// private static int total;
	private static AtomicInteger total;

	public static void main(String[] args) throws InterruptedException {
		total = new AtomicInteger();

		CountDownLatch latch = new CountDownLatch(10);
		for (int n = 0; n < 10; n++) {
			Thread th = new Thread(() -> {
				for (int i = 0; i < 1000; i++) {
					// total++;
					total.incrementAndGet();
				}
				latch.countDown();
			});
			th.start();
		}

		latch.await();
		System.out.println(total); // int=8919, AtomicInt=10000

	}
}