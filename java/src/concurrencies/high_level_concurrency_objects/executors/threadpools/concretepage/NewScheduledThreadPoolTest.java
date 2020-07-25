package threadpools.concretepage;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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
public class NewScheduledThreadPoolTest {
	public static void main(final String... args) throws InterruptedException, ExecutionException {
		// creates thread pool with 2 thread
		final ScheduledExecutorService schExService = Executors.newScheduledThreadPool(1);
		// Object creation of runnable thread.
		final Runnable ob = new NewScheduledThreadPoolTest().new DemoThread();
		final Runnable ob1 = new NewScheduledThreadPoolTest().new DemoThread1();
		// Thread scheduling
		schExService.scheduleAtFixedRate(ob, 2, 10, TimeUnit.SECONDS);
		schExService.scheduleAtFixedRate(ob1, 2, 10, TimeUnit.SECONDS);
		// waits for termination for 30 seconds only
		schExService.awaitTermination(50, TimeUnit.SECONDS);
		// shutdown now.
		// schExService.shutdownNow();
		System.out.println("Shutdown Complete");
	}

	// Runnable thread
	class DemoThread implements Runnable {
		@Override
		public void run() {
			System.out.println("Done0" + new Date());
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	class DemoThread1 implements Runnable {
		@Override
		public void run() {
			System.out.println("Done1" + new Date());
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}