package concurrency.java.optimize;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;
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
public class BoundedExecutor extends ThreadPoolExecutor {

	private final Semaphore semaphore;

	public BoundedExecutor(int bound) {
		super(bound, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
		semaphore = new Semaphore(bound);
	}

	/**
	 * Submits task to execution pool, but blocks while number of running
	 * threads has reached the bound limit
	 */
	public <T> Future<T> submitButBlockIfFull(final Callable<T> task) throws InterruptedException {

		semaphore.acquire();
		return submit(task);
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);

		semaphore.release();
	}
}