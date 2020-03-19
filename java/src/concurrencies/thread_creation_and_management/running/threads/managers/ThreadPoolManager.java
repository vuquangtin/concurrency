package running.threads.managers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreadPoolManager {

	private final int DEFAULT_POOL_SIZE = 3;

	private ExecutorService executorService;

	public ThreadPoolManager() {

	}

	public void createPool(String poolName) {

		// executorService = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);
		executorService = new ThreadPoolExecutor(DEFAULT_POOL_SIZE,
				DEFAULT_POOL_SIZE, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(), new DefaultThreadFactory(
						poolName), new ThreadPoolExecutor.AbortPolicy());

	}

	public void createPool(int threadPoolSize, String poolName) {
		int size = threadPoolSize > 0 ? threadPoolSize : DEFAULT_POOL_SIZE;
		// executorService = Executors.newFixedThreadPool(size);
		executorService = new ThreadPoolExecutor(size, size, 0L,
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
				new DefaultThreadFactory(poolName),
				new ThreadPoolExecutor.AbortPolicy());
	}

	public void destoryPool() {

		executorService.shutdown();

	}

	public Future<?> execute(Task task) {
		return executorService.submit(task);
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	private class DefaultThreadFactory implements ThreadFactory {
		private final ThreadGroup group;
		private final AtomicInteger threadNumber = new AtomicInteger(1);
		private final String namePrefix;

		DefaultThreadFactory(String poolName) {
			SecurityManager s = System.getSecurityManager();
			group = (s != null) ? s.getThreadGroup() : Thread.currentThread()
					.getThreadGroup();
			namePrefix = "pool-" + poolName + "-thread-";
		}

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(group, r, namePrefix
					+ threadNumber.getAndIncrement(), 0);
			if (t.isDaemon())
				t.setDaemon(false);
			if (t.getPriority() != Thread.NORM_PRIORITY)
				t.setPriority(Thread.NORM_PRIORITY);
			return t;
		}
	}
}