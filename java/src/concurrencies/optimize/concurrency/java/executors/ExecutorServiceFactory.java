package concurrency.java.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
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
public class ExecutorServiceFactory {
	private static ExecutorServiceFactory executorFactory = new ExecutorServiceFactory();
	/**
	 * 定时任务线程池
	 */
	private ExecutorService executors;
	private static final String NAME = "Thread Mission - ";

	private ExecutorServiceFactory() {
	}

	/**
	 * 获取工厂实例
	 * 
	 * @return
	 */
	public static ExecutorServiceFactory getInstance() {
		return executorFactory;
	}

	/**
	 * 延迟、定时周期任务线程池
	 * 
	 * @return
	 */
	public ExecutorService createScheduledThreadPool() {
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		executors = Executors.newScheduledThreadPool(availableProcessors * 10,
				getThreadFactory());
		return executors;
	}

	/**
	 * 单线程线程池
	 * 
	 * @return
	 */
	public ExecutorService createSingleThreadExecutor() {
		executors = Executors.newSingleThreadExecutor(getThreadFactory());
		return executors;
	}

	/**
	 * 可缓存线程池，无上限，空闲线程优先执行
	 * 
	 * @return
	 */
	public ExecutorService createCachedThreadPool() {
		executors = Executors.newCachedThreadPool(getThreadFactory());
		return executors;
	}

	/**
	 * 可控制线程最大并发数线程池
	 * 
	 * @return
	 */
	public ExecutorService createFixedThreadPool(int count) {
		executors = Executors.newFixedThreadPool(count, getThreadFactory());
		return executors;
	}

	/**
	 * 获取线程池工厂
	 * 
	 * @return
	 */
	private ThreadFactory getThreadFactory() {
		return new ThreadFactory() {
			AtomicInteger sn = new AtomicInteger();

			public Thread newThread(Runnable r) {
				SecurityManager s = System.getSecurityManager();
				ThreadGroup group = (s != null) ? s.getThreadGroup() : Thread
						.currentThread().getThreadGroup();
				Thread t = new Thread(group, r);
				t.setName(NAME + sn.incrementAndGet());
				return t;
			}
		};
	}
}