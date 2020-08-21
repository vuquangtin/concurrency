package synchronizers.countdownlatch.demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class NaiveCountLatch {

	public static final int NUMBER_OF_THREADS = 1000;

	public static void main(String[] args) {
		Long[] arrayFromClientApi = new Long[10_000_000];

		for (int i = 0; i < 10_000_000; i++) {
			arrayFromClientApi[i] = Long.valueOf(i);
		}

		// exchange all array items on squares of these items
		// by 1000 parallel threads

		CountDownLatch latch = new CountDownLatch(NUMBER_OF_THREADS);
		ExecutorService pool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
		for (int j = 0; j < NUMBER_OF_THREADS; j++) {
			final int k = j;
			pool.submit(new Runnable() {
				@Override
				public void run() {

					for (int i = k * 10000; i < (k + 1) * 10000; i++) {
						arrayFromClientApi[i] = arrayFromClientApi[i]
								* arrayFromClientApi[i];
					}
					latch.countDown();
				}
			});
		}

		try {
			latch.await();
		} catch (InterruptedException ie) {
		}

		long sum = 0;
		for (Long e : arrayFromClientApi) {
			sum += e;
		}
		// The system is ready to go
		System.out.println("result is " + sum);
		pool.shutdown();
	}

}