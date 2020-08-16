package concurrency.java.optimize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
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
public class MultiThreadUtil {
	private static final int THREAD_SIZE = Runtime.getRuntime().availableProcessors() + 1;

	/**
	 * 线程池执行任务。 阻塞调用者线程，直到任务执行完成
	 *
	 * @param works
	 * @param workQueueSize
	 */
	public static void executeRunnableWorks(List<Runnable> works, int workQueueSize) {
		ThreadPoolExecutor pool = new ThreadPoolExecutor(THREAD_SIZE, THREAD_SIZE, 0L, TimeUnit.MILLISECONDS,
				new LimitedQueue<>(workQueueSize));
		/* 提交任务 */
		for (Runnable work : works) {
			pool.submit(work);
		}
		waitPoolShutDown(pool);
	}

	private static void waitPoolShutDown(ThreadPoolExecutor pool) {
		pool.shutdown();
		while (true) {
			try {
				if (pool.awaitTermination(1L, TimeUnit.MINUTES)) {
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static <T> List<Future<T>> executeCallableWorks(Collection<Callable<T>> works, int workQueueSize) {
		ThreadPoolExecutor pool = new ThreadPoolExecutor(THREAD_SIZE, THREAD_SIZE, 0L, TimeUnit.MILLISECONDS,
				new LimitedQueue<>(workQueueSize));
		List<Future<T>> futures = new ArrayList<>(works.size());
		/* 提交任务 */
		for (Callable<T> work : works) {
			futures.add(pool.submit(work));
		}
		waitPoolShutDown(pool);
		return futures;
	}

	/**
	 * 线程池在提交任务的的时候，submit方法调用的是工作队列的offer方法，而队列的offer方法是没有阻塞功能的，因此如果任务提交过快，
	 * 导致塞满工作队列的时候 就会触发RejectedExecutionHandler，它会抛出异常，从而导致任务提交失败。
	 * <p>
	 * 该队列的offer方法是具有阻塞功能的。
	 *
	 * @param <E>
	 */
	public static class LimitedQueue<E> extends LinkedBlockingQueue<E> {
		public LimitedQueue(int capacity) {
			super(capacity);
		}

		@Override
		public boolean offer(E e) {
			// turn offer() and add() into a blocking calls (unless interrupted)
			/*
			 * 方法来源于
			 * https://stackoverflow.com/questions/4521983/java-executorservice-
			 * that-blocks-on-submission-after-a-certain-queue-size
			 */
			try {
				put(e);
				return true;
			} catch (InterruptedException ie) {
				Thread.currentThread().interrupt();
			}
			return false;
		}
	}
}
