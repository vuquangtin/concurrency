package asynchronous.programming;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ConcurrencyUtil {

	static public void getIfNotCancelled(Future<String> future) {
		try {
			if (!future.isCancelled()) { // check the status of the Future
				System.out.println("FUTURE.get: " + future.get()); // Blocking
																	// call to
																	// retrieve
																	// its value
			} else {
				System.out.println("FUTURE Cancelled!");
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	static public Runnable createRunnableTask(String s, Integer millis) {
		Runnable runnableTask = () -> {
			try {
				System.out.println(String.format("%s: %s", Thread
						.currentThread().getName(), s));
				TimeUnit.MILLISECONDS.sleep(millis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
		return runnableTask;
	}

	static public Callable<String> createCallableTask(String s, Integer millis) {
		Callable<String> callableTask = () -> {
			System.out.println(String.format("%s: %s", Thread.currentThread()
					.getName(), s));
			TimeUnit.MILLISECONDS.sleep(millis);
			return "Callable Result of " + Thread.currentThread().getName();
		};
		return callableTask;
	}

	static public void sleep(Integer millis) {
		try {
			TimeUnit.MILLISECONDS.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	static public void printFutures(List<Future<String>> futures) {
		futures.forEach(f -> {
			try {
				System.out.println("RESULT of Submitted Callable: " + f.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		});
	}
}