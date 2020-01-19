package concurrencies.utilities;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ExecutorServices {

	private ExecutorServices() {
	}

	/**
	 * 公共连接池
	 *
	 * @return
	 */
	public static ExecutorService commonThreadPool() {
		return ExecutorServiceHolder.INSTANCE.commonThreadPool();
	}

	/**
	 * 新建线程池
	 *
	 * @return
	 */
	public static ExecutorService newThreadPool() {
		return ExecutorServiceHolder.INSTANCE.newThreadPool();
	}

	enum ExecutorServiceHolder {

		INSTANCE;

		private final CommonThreadPoolExecutor commonThreadPool;

		ExecutorServiceHolder() {
			this.commonThreadPool = new CommonThreadPoolExecutor(newCommonThreadPool());
		}

		private ExecutorService commonThreadPool() {
			return this.commonThreadPool;
		}

		private ThreadPoolExecutor doNewPoolExecutor(ThreadFactory threadFactory) {
			final int corePoolSize = Runtime.getRuntime().availableProcessors() << 4;

			final int maximumPoolSize = corePoolSize << 2;

			// 60
			final long keepAliveTime = 60;

			final TimeUnit timeUnit = TimeUnit.SECONDS;

			// 8192
			final int maximumCapacity = 1 << 13;

			return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit,
					new LinkedBlockingQueue<>(maximumCapacity), threadFactory);
		}

		private ThreadPoolExecutor newThreadPool() {
			return doNewPoolExecutor(new DefaultNewThreadFactory());
		}

		private ThreadPoolExecutor newCommonThreadPool() {
			return doNewPoolExecutor(new DefaultCommonThreadFactory());
		}
	}

	/**
	 * 公共连接池
	 */
	public static class CommonThreadPoolExecutor implements ExecutorService {

		private ThreadPoolExecutor threadPoolExecutor;

		public CommonThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
			this.threadPoolExecutor = threadPoolExecutor;
		}

		/**
		 * 返回真正的连接池实例
		 *
		 * @return
		 */
		public ThreadPoolExecutor threadPoolExecutor() {
			return threadPoolExecutor;
		}

		// ===========================公共连接池不能直接调用的方法=======================================

		@Override
		public void shutdown() {
			throw new UnsupportedOperationException();
		}

		@Override
		public List<Runnable> shutdownNow() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
			throw new UnsupportedOperationException();
		}

		// ==========================================================================================

		@Override
		public void execute(Runnable command) {
			threadPoolExecutor.execute(command);
		}

		@Override
		public boolean isShutdown() {
			return threadPoolExecutor.isShutdown();
		}

		@Override
		public boolean isTerminated() {
			return threadPoolExecutor.isTerminated();
		}

		@Override
		public Future<?> submit(Runnable task) {
			return threadPoolExecutor.submit(task);
		}

		@Override
		public <T> Future<T> submit(Runnable task, T result) {
			return threadPoolExecutor.submit(task, result);
		}

		@Override
		public <T> Future<T> submit(Callable<T> task) {
			return threadPoolExecutor.submit(task);
		}

		@Override
		public <T> T invokeAny(Collection<? extends Callable<T>> tasks)
				throws InterruptedException, ExecutionException {
			return threadPoolExecutor.invokeAny(tasks);
		}

		@Override
		public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
				throws InterruptedException, ExecutionException, TimeoutException {
			return threadPoolExecutor.invokeAny(tasks, timeout, unit);
		}

		@Override
		public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
			return threadPoolExecutor.invokeAll(tasks);
		}

		@Override
		public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
				throws InterruptedException {
			return threadPoolExecutor.invokeAll(tasks, timeout, unit);
		}
	}

	/**
	 * 公共线程池ThreadFactory
	 */
	static class DefaultCommonThreadFactory implements ThreadFactory {
		private static final AtomicInteger poolNumber = new AtomicInteger(1);
		private final ThreadGroup group;
		private final AtomicInteger threadNumber = new AtomicInteger(1);
		private final String namePrefix;

		DefaultCommonThreadFactory() {
			SecurityManager s = System.getSecurityManager();
			group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
			this.namePrefix = "common-pool-" + poolNumber.getAndIncrement() + "-thread-";
		}

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
			if (t.isDaemon()) {
				t.setDaemon(false);
			}
			if (t.getPriority() != Thread.NORM_PRIORITY) {
				t.setPriority(Thread.NORM_PRIORITY);
			}
			return t;
		}
	}

	/**
	 * 新线程池ThreadFactory
	 */
	static class DefaultNewThreadFactory implements ThreadFactory {
		private static final AtomicInteger poolNumber = new AtomicInteger(1);
		private final ThreadGroup group;
		private final AtomicInteger threadNumber = new AtomicInteger(1);
		private final String namePrefix;

		DefaultNewThreadFactory() {
			SecurityManager s = System.getSecurityManager();
			group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
			this.namePrefix = "new-pool-" + poolNumber.getAndIncrement() + "-thread-";
		}

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
			if (t.isDaemon()) {
				t.setDaemon(false);
			}
			if (t.getPriority() != Thread.NORM_PRIORITY) {
				t.setPriority(Thread.NORM_PRIORITY);
			}
			return t;
		}
	}

}
