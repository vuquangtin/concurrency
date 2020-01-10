package executors.extend;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
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
public class ThreadManager {

	private static ThreadManager threadManager;
	private ThreadPoolExecutor threadPoolExecutor;

	public ThreadManager(int maxThreadCount) {
		threadPoolExecutor = new ThreadPoolExecutor(1, maxThreadCount, 200L,
				TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10));
	}

	public synchronized static ThreadPoolExecutor getThreadManager(
			int maxThreadCount) {
		if (threadManager == null) {
			synchronized (ThreadManager.class) {
				if (threadManager == null) {
					threadManager = new ThreadManager(maxThreadCount);
				}
			}
		}
		return threadManager.threadPoolExecutor;
	}

	public synchronized static ThreadManager getThreadManager() {
		return threadManager;
	}

	// 执行任务，当线程池处于关闭，将会重新创建的线程池
	public synchronized void execute(Runnable runn) {
		if (runn == null) {
			return;
		}
		if (threadPoolExecutor == null || threadPoolExecutor.isShutdown()) {
			threadPoolExecutor = getThreadManager(1);
		}
		threadPoolExecutor.submit(runn);
	}

	// 执行任务，当线程池处于关闭，将会重新创建的线程池
	public synchronized <T> Future<T> execute(Callable<T> callable) {
		if (callable == null) {
			return null;
		}
		if (threadPoolExecutor == null || threadPoolExecutor.isShutdown()) {
			threadPoolExecutor = getThreadManager(1);
		}
		Future<T> future = threadPoolExecutor.submit(callable);

		return future;
	}

	/** 取消线程池中某个还未执行的任务 */
	public synchronized void cancel(Runnable runn) {
		if (threadPoolExecutor != null && !threadPoolExecutor.isShutdown()
				|| threadPoolExecutor.isTerminating()) {
			threadPoolExecutor.getQueue().remove(runn);
		}
	}

	/** 取消线程池中某个还未执行的任务 */
	public synchronized void cancel(Callable<?> callable) {
		if (threadPoolExecutor != null && !threadPoolExecutor.isShutdown()
				|| threadPoolExecutor.isTerminating()) {
			threadPoolExecutor.getQueue().remove(callable);
		}
	}

	/** 取消线程池中某个还未执行的任务 */
	public synchronized boolean contains(Callable<?> callable) {
		if (threadPoolExecutor != null
				&& (!threadPoolExecutor.isShutdown() || threadPoolExecutor
						.isTerminating())) {
			return threadPoolExecutor.getQueue().contains(callable);
		} else {
			return false;
		}
	}

	/** 取消线程池中某个还未执行的任务 */
	public synchronized boolean contains(Runnable runn) {
		if (threadPoolExecutor != null && !threadPoolExecutor.isShutdown()
				|| threadPoolExecutor.isTerminating()) {
			return threadPoolExecutor.getQueue().contains(runn);
		} else {
			return false;
		}
	}

	/** 立刻关闭线程池，并且正在执行的任务也将会被中断 */
	public void stop() {
		if (threadPoolExecutor != null && !threadPoolExecutor.isShutdown()
				|| threadPoolExecutor.isTerminating()) {
			threadPoolExecutor.shutdown();
		}
	}

	/** 平缓关闭单任务线程池，但是会确保所有已经加入的任务都将会被执行完毕才关闭 */
	public synchronized void shutdown() {
		if (threadPoolExecutor != null && !threadPoolExecutor.isShutdown()
				|| threadPoolExecutor.isTerminating()) {
			threadPoolExecutor.shutdownNow();
		}
	}
}