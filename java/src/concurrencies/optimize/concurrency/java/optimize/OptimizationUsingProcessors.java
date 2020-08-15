package concurrency.java.optimize;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class OptimizationUsingProcessors {

	private final static int NUM_CORES = Runtime.getRuntime()
			.availableProcessors();
	private static long count;
	private static Runnable r = new Runnable() {

		@Override
		public void run() {
			int count = 0;
			for (int i = 0; i < 100_000; i++) {
				count += i;
			}
			OptimizationUsingProcessors.count += count;
		}
	};

	public static void main(String[] args) throws Exception {
		System.out.println("NUM_CORES:" + NUM_CORES);
		// warmup
		runWith(10);

		// test
		runWith(NUM_CORES + 1);
		runWith(100);
		runWith(1000);
		runWith(10000);
	}

	private static void runWith(int poolSize) throws InterruptedException {
		long average = 0;
		for (int run = 0; run < 10; run++) { // run 10 times and take the
												// average
			OptimizationUsingProcessors.count = 0;
			ExecutorService executor = Executors.newFixedThreadPool(poolSize);
			long start = System.nanoTime();
			for (int i = 0; i < 50000; i++) {
				executor.submit(r);
			}
			executor.shutdown();
			executor.awaitTermination(10, TimeUnit.SECONDS);
			long end = System.nanoTime();
			average += ((end - start) / 1000000);
			System.gc();
		}
		System.out.println("Pool size: " + poolSize + ": " + average / 10
				+ " ms.  ");
	}
}
