package concurrency.java.executors;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
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

	public BlockingBoundedExecutor(int numberOfThreads, int queueSize) {

		// The Queue is not bounded
		// We might be tempted to bind it with queueSize but that can be
		// dangerous because
		// we release the semaphore in the last line *within* the execution of
		// the thread
		// So, in a really close call, the task may be submitted before the
		// thread becomes free
		// And, if queue is also full our execution will be rejected
		final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();

		// Get permits = number of threads allowed + number of items allowed in
		// queue
		semaphore = new Semaphore(numberOfThreads + queueSize);

		executor = new java.util.concurrent.ThreadPoolExecutor(numberOfThreads,
				numberOfThreads, 100, TimeUnit.SECONDS, queue);

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
				System.out
						.println("InterruptedException with the semaphore.acquire() method in the BlockingBoundedExecutor class:\n"
								+ ex.getMessage());
		}
	}

	public void runTask(final Runnable task) throws InterruptedException {

		// Before submitting the task, acquire a permit
		// This will block if all the permits are in use thereby stopping the
		// generation of more work
		semaphore.acquire();

		Runnable newTask = new Runnable() {

			@Override
			public void run() {

				try {
					task.run();
				} finally {
					// When the thread has done it's work, release the permit.
					// Ideally we would want to do this *after* the thread is
					// returned to the pool
					// But, placing it here also gives acceptable performance
					semaphore.release();
				}

			}

		};

		try {
			executor.execute(newTask);
		} catch (RejectedExecutionException re) {
			// Since we have already acquired a permit, in case our execution is
			// rejected we want to release the permit.
			semaphore.release();
		}

	}

	public void shutDown() {
		((java.util.concurrent.ThreadPoolExecutor) executor).shutdown();
		try {
			// This is just a hack to make sure my JVM terminates within a day
			// If the process is really long running, this should be altered
			((java.util.concurrent.ThreadPoolExecutor) executor)
					.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws Exception {
		BlockingThreadPoolExecutor exec = new BlockingThreadPoolExecutor(10, 5);

		// Submit 100 threads for execution
		// The output demonstrates that up to 15 are submitted out of which 10
		// start running and 5 are queued up
		for (int i = 0; i < 100; i++) {
			System.out.println("Submitting Task " + i);
			final int j = i;
			Runnable r = new Runnable() {

				@Override
				public void run() {

					System.out.println("Thread " + j + " says HI");
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					System.out.println("Thread " + j + " says BYE");

				}

			};
			exec.runTask(r);
		}

		exec.shutDown();
	}

}