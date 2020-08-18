package concurrency.java.bounded.blocking;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class BlockingThreadPoolExecutor extends ThreadPoolExecutor {
	static Logger logger = Logger.getLogger(BlockingThreadPoolExecutor.class
			.getName());

	/**
	 * RejectedExecutionHandler
	 */
	private static final RejectedExecutionHandler rjeHandler = new RejectedExecutionHandler() {

		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor tpe) {
			// Retry count for intrumentation and debugging
			int retryCount = 0;

			// Try indefinitely to add the task to the queue
			while (true) {
				retryCount++;

				if (tpe.isShutdown()) // If the executor is shutdown, reject the
										// task and
				// throw RejectedExecutionException
				{
					((BlockingThreadPoolExecutor) tpe).taskRejectedGaveUp(r,
							retryCount);
					throw new RejectedExecutionException(
							"ThreadPool has been shutdown");
				}

				try {
					if (tpe.getQueue().offer(r, 1, TimeUnit.SECONDS)) {
						// Task got accepted!
						((BlockingThreadPoolExecutor) tpe).taskAccepted(r,
								retryCount);
						break;
					} else {
						((BlockingThreadPoolExecutor) tpe)
								.taskRejectedRetrying(r, retryCount);
					}

				} catch (InterruptedException e) {
					throw new AssertionError(e);
				}
			}
		}

	};

	/**
	 * Default constructor. Create a ThreadPool of a single thread with a very
	 * large queue.
	 */
	public BlockingThreadPoolExecutor() {
		this(1, 1, Integer.MAX_VALUE, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(), new PriorityThreadFactory(
						"workerthread"));
	}

	/**
	 * Constructor
	 *
	 * @param corePoolSize
	 *            - Number of threads of keep in the pool
	 * @param maxPoolSize
	 *            - Maximum number of threads that can be spawned
	 * @param queueCapacity
	 *            - Size of the task queue
	 * @param baseThreadName
	 *            - Name assigned to each worker thread
	 * @param priority
	 *            - Priority of worker threads
	 * @param daemon
	 *            - If true, workers threads are created as daemons.
	 */
	public BlockingThreadPoolExecutor(int corePoolSize, int maxPoolSize,
			int queueCapacity, String baseThreadName, int priority,
			boolean daemon) {
		this(corePoolSize, maxPoolSize, Integer.MAX_VALUE, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(queueCapacity),
				new PriorityThreadFactory(baseThreadName, priority, daemon));
	}

	/**
	 * Constructor
	 *
	 * @param corePoolSize
	 *            - Number of threads of keep in the pool
	 * @param maxPoolSize
	 *            - Maximum number of threads that can be spawned
	 * @param keepAliveTime
	 * @param unit
	 * @param queue
	 * @param threadFactory
	 */
	public BlockingThreadPoolExecutor(int corePoolSize, int maxPoolSize,
			long keepAliveTime, TimeUnit unit,
			java.util.concurrent.BlockingQueue<Runnable> queue,
			ThreadFactory threadFactory) {
		super(corePoolSize, maxPoolSize, keepAliveTime, unit, queue,
				threadFactory);
		allowCoreThreadTimeOut(true);

		// Attach a customer RejectedExecutionHandler defined above.
		this.setRejectedExecutionHandler(rjeHandler);
	}

	/**
	 * Called when giving up on the task and rejecting it for good.
	 *
	 * @param r
	 * @param retryCount
	 */
	protected void taskRejectedGaveUp(Runnable r, int retryCount) {
		// logger.debug("Gave Up: {}",retryCount );
	}

	/**
	 * Called when the task that was rejected initially is rejected again.
	 *
	 * @param r
	 *            - Task
	 * @param retryCount
	 *            - number of total retries
	 */
	protected void taskRejectedRetrying(Runnable r, int retryCount) {
		// logger.debug("Retrying: {}",retryCount );
	}

	/**
	 * Called when the rejected task is finally accepted.
	 *
	 * @param r
	 *            - Task
	 * @param retryCount
	 *            - number of retries before acceptance
	 */
	protected void taskAccepted(Runnable r, int retryCount) {
		// logger.debug("Accepted: {}",retryCount );
	}

}