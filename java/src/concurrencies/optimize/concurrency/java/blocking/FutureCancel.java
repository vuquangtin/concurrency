package concurrency.java.blocking;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class FutureCancel {
	private static final ExecutorService executorService = Executors
			.newCachedThreadPool();

	public static void timedRun(final Runnable runnable, long timeOut,
			TimeUnit timeUnit) throws InterruptedException {
		Future<?> submit = executorService.submit(runnable);
		executorService.execute(runnable);
		try {
			submit.get(timeOut, timeUnit);
		} catch (InterruptedException e) {
			throw e;
		} catch (ExecutionException e) {
			throw launderThrowable(e.getCause());
		} catch (TimeoutException e) {
		} finally {
			submit.cancel(true);
		}
	}

	public static RuntimeException launderThrowable(Throwable t) {
		if (t instanceof RuntimeException) {
			return (RuntimeException) t;
		} else if (t instanceof Error) {
			throw (Error) t;
		} else {
			throw new IllegalStateException("Not unchecked", t);
		}
	}
}
