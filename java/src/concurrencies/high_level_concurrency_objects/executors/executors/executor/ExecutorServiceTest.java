package executors.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class ExecutorServiceTest {

	public static void main(String[] args) throws InterruptedException {

		// A maximum of 10 threads will run at any time!
		ExecutorService executor = Executors.newFixedThreadPool(10);

		// Submit and execute 100 threads!
		for (int i = 0; i < 100; i++) {
			final int number = i;
			executor.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println("Running " + number);
					sleep();
					System.out.println("Finishing " + number);
				}
			});
		}

		System.out.println("Waiting...");

		// No more threads can be submitted to the executor service!
		executor.shutdown();

		// Blocks until all 100 submitted threads have finished!
		executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);

		System.out.println("Done");
	}

	private static void sleep() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}