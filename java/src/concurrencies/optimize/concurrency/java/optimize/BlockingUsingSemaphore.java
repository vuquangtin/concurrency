package concurrency.java.optimize;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class BlockingUsingSemaphore {

	public static void main(String[] args) {
		ExecutorService service = new ThreadPoolExecutor(3, 3, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(6, false));

		Semaphore lock = new Semaphore(6); // equal to queue capacity

		for (int i = 0; i < 100000; i++) {
			try {
				lock.acquire();
				final int t=i;
				service.submit(() -> {
					try {
						new MyRunnable(t).run();
					} finally {
						lock.release();
					}
				});
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

	}

}
