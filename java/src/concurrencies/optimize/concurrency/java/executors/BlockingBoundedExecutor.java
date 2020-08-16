package concurrency.java.executors;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class BlockingBoundedExecutor {

	/**
	 * The executor used for the BlockingBoundedExecutor.
	 */
	private Executor executor = null;

	/**
	 * The Semaphore to be used to turn the executor into a blocking bounded
	 * executor.
	 */
	private Semaphore semaphore = null;

	/**
	 * The constructor of the BlockingBoundedExecutor class.
	 */
	public BlockingBoundedExecutor(Executor executor, int bound) {
		this.executor = executor;
		this.semaphore = new Semaphore(bound);
	}

	/**
	 * Executes the runnable if the semaphore permits the execution of the given
	 * runnable.
	 */
	public void submitTask(final Runnable runnable) {
		try {
			semaphore.acquire();
			try {
				executor.execute(new Runnable() {

					@Override
					public void run() {
						try {
							runnable.run();
						} finally {
							semaphore.release();
						}
					}

				});
			} catch (RejectedExecutionException ignored) {
				semaphore.release();
			}
		} catch (InterruptedException ex) {
			// restore the interuption status after catching
			// InterruptedException
			Thread.currentThread().interrupt();
			if (true)
				System.out.println(
						"InterruptedException with the semaphore.acquire() method in the BlockingBoundedExecutor class:\n"
								+ ex.getMessage());
		}
	}

}