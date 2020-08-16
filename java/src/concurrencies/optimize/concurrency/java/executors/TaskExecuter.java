package concurrency.java.executors;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import lombok.NonNull;

/**
 * F
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class TaskExecuter {
	static Logger logger = Logger.getLogger(TaskExecuter.class.getName());
	private static final String TAG = "TaskExecuter";
	/**
	 * 当前设备的CPU数
	 */
	private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

	/**
	 * 当前线程池默认的线程数
	 */
	private static final int CORE_POOL_SIZE = CPU_COUNT + 1;

	/**
	 * 线程池最大线程数
	 */
	private static final int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;

	/**
	 * 调度任务最大线程池数
	 */
	private static final int MAX_SCHEDULED_POOL_SIZE = 2;
	static int putValue = 0;
	static int takeValue = 0;

	/**
	 * 是否keep alive
	 */
	private static final int KEEP_ALIVE = 1;

	public static LimitedQueue<Runnable> limitedQueue = new LimitedQueue<>();

	private static ThreadPoolExecutor sExecutorService = null;

	private synchronized static void ensureThreadPoolExecutor() {
		if (sExecutorService == null) {
			sExecutorService = new ThreadPoolExecutor(5, MAX_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, limitedQueue,
					new DefaultThreadPoolFactory(), new ThreadPoolExecutor.DiscardOldestPolicy());
		}
	}

	/**
	 * 执行异步任务，默认线程池
	 *
	 * @param task
	 *            需要异步执行的任务
	 */
	public static Future<?> executeTask(Runnable task) {
		ensureThreadPoolExecutor();
		return sExecutorService.submit(task);
	}

	/**
	 * 默认的线程池工厂，线程使用默认优先级
	 */
	private static class DefaultThreadPoolFactory implements ThreadFactory {

		/**
		 * 当前线程池号
		 */
		private static final AtomicInteger THREAD_POOL_NUM = new AtomicInteger(1);
		/**
		 * 线程所属分组
		 */
		private final ThreadGroup group;
		/**
		 * 线程代号
		 */
		private final AtomicInteger threadNum = new AtomicInteger(1);

		/**
		 * 当前总线程数
		 */
		private final AtomicInteger totalThreadNum = new AtomicInteger(1);

		/**
		 * 线程名前缀
		 */
		private final String namePrefix;

		/**
		 * 默认线城市工厂
		 */
		DefaultThreadPoolFactory() {
			SecurityManager s = System.getSecurityManager();
			group = s != null ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
			namePrefix = "pool-id-" + THREAD_POOL_NUM.getAndIncrement();
		}

		@Override
		public Thread newThread(@NonNull Runnable r) {
			Thread thread = new Thread(group, r, namePrefix + " thread-id-" + threadNum.getAndIncrement()
					+ " total-thread-num-" + totalThreadNum.getAndIncrement());
			thread.setPriority(Thread.NORM_PRIORITY);
			if (thread.isDaemon()) {
				thread.setDaemon(false);
			}
			return thread;
		}
	}

	public static class LimitedQueue<Runnable> extends LinkedBlockingQueue<java.lang.Runnable> {
		public LimitedQueue() {
			// super(maxSize);
		}

		@Override
		public boolean offer(java.lang.Runnable e) {
			// turn offer() and add() into a blocking calls (unless interrupted)
			try {
				putValue++;
				logger.debug("wjq" + "offer: " + putValue);
				put(e);
				return true;
			} catch (InterruptedException ie) {
				Thread.currentThread().interrupt();
			}
			return false;
		}

		@Override
		public java.lang.Runnable poll() {
			logger.debug("wjq" + "poll: ");
			try {
				return super.poll(KEEP_ALIVE, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			logger.debug("wjq" + "null: ");
			return null;
		}

		@Override
		public java.lang.Runnable take() throws InterruptedException {
			takeValue++;
			logger.debug("wjq" + "take: " + takeValue);
			return super.take();
		}
	}
}
