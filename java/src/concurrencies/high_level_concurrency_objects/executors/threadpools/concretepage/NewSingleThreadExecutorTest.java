package threadpools.concretepage;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
public class NewSingleThreadExecutorTest {
	public static void main(final String... args) throws InterruptedException, ExecutionException {
		// creates thread pool with one thead
		final ExecutorService exService = Executors.newSingleThreadExecutor();
		// callable thread starts to execute
		final Future<Integer> callFuture = exService.submit(new NewSingleThreadExecutorTest().new CallableThread());
		// gets value of callable thread
		final int callval = callFuture.get();
		System.out.println("Callable:" + callval);
		// checks for thread termination
		final boolean isTerminated = exService.isTerminated();
		System.out.println(isTerminated);
		// waits for termination for 30 seconds only
		exService.awaitTermination(30, TimeUnit.SECONDS);
		exService.shutdownNow();
	}

	// Callable thread
	class CallableThread implements Callable<Integer> {
		@Override
		public Integer call() {
			int cnt = 0;
			for (; cnt < 5; cnt++) {
				System.out.println("call:" + cnt);
			}
			return cnt;
		}
	}
}