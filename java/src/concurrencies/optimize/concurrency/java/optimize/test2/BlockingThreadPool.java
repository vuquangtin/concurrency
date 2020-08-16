package concurrency.java.optimize.test2;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * A fixed-capacity queue which blocks threads which attempt to submit while it
 * is at max capacity.
 * 
 * @param <E>
 *            The type of object which the queue will hold
 */
class LimitedQueue<E> extends LinkedBlockingQueue<E> {
	public LimitedQueue(int maxSize) {
		super(maxSize);
	}

	@Override
	public boolean offer(E e) {
		// turn offer() and add() into a blocking calls (unless interrupted)
		try {
			put(e);
			return true;
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
		return false;
	}
}

/**
 * A ThreadPoolExecutor using a fixed number of threads to handle the job queue.
 * This class's job queue has a size limit. When it is full, any further job
 * submissions will block the submitting thread until there is again space in
 * the queue, whereupon the submission will complete.
 ** 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class BlockingThreadPool extends ThreadPoolExecutor {
	public static final TimeUnit HASHING_TIME_LIMIT_UNITS = TimeUnit.MINUTES;
    public static final int THREAD_TIMEOUT_MILLIS = 60;
    public static final TimeUnit THREAD_TIMEOUT_UNITS = TimeUnit.MINUTES;
	/**
	 * @param num_threads
	 *            The number of threads used to execute jobs in the job queue
	 * @param queue_capacity
	 *            The maximum capacity of the job queue
	 */
	public BlockingThreadPool(int num_threads, int queue_capacity) {
		// For this class to function correctly, the minimum and maximum number
		// of threads must be equal (as they are
		// below).
		// See
		// https://stackoverflow.com/questions/4521983/java-executorservice-that-blocks-on-submission-after-a-certain-queue-size
		// for more details
		super(num_threads, num_threads, THREAD_TIMEOUT_MILLIS, THREAD_TIMEOUT_UNITS,
				new LimitedQueue<>(queue_capacity));
	}
}