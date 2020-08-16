package concurrency.java.optimize;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;

/**
 * 
 * distributes {@code Runnable}s to a fixed number of threads. To keep the code
 * lean, this is not an {@code ExecutorService}. In particular there is only
 * very simple support to shut this executor down.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */

public class ParallelExecutor implements Executor {
	// other bounded queues work as well and are useful to buffer peak loads
	private final BlockingQueue<Runnable> workQueue = new SynchronousQueue<Runnable>();
	private final Thread[] threads;

	/* + **********************************************************************/
	/**
	 * creates the requested number of threads and starts them to wait for
	 * incoming work
	 */
	public ParallelExecutor(int numThreads) {
		this.threads = new Thread[numThreads];
		for (int i = 0; i < numThreads; i++) {
			// could reuse the same Runner all over, but keep it simple
			Thread t = new Thread(new Runner());
			this.threads[i] = t;
			t.start();
		}
	}

	/* + **********************************************************************/
	/**
	 * returns immediately without waiting for the task to be finished, but may
	 * block if all worker threads are busy.
	 * 
	 * @throws RejectedExecutionException
	 *             if we got interrupted while waiting for a free worker
	 */
	@Override
	public void execute(Runnable task) {
		try {
			workQueue.put(task);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RejectedExecutionException("interrupt while waiting for a free " + "worker.", e);
		}
	}

	/* + **********************************************************************/
	/**
	 * Interrupts all workers and joins them. Tasks susceptible to an interrupt
	 * will preempt their work. Blocks until the last thread surrendered.
	 */
	public void interruptAndJoinAll() throws InterruptedException {
		for (Thread t : threads) {
			t.interrupt();
		}
		for (Thread t : threads) {
			t.join();
		}
	}

	/* + **********************************************************************/
	private final class Runner implements Runnable {
		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				Runnable task;
				try {
					task = workQueue.take();
				} catch (InterruptedException e) {
					// canonical handling despite exiting right away
					Thread.currentThread().interrupt();
					return;
				}
				try {
					task.run();
				} catch (RuntimeException e) {
					// production code to use a logging framework
					e.printStackTrace();
				}
			}
		}
	}
}
