package executors.extend;

import java.util.concurrent.*;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class SimpleThreadPoolExecutor extends ThreadPoolExecutor {
	private String threadPoolName;

	public SimpleThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, String threadPoolName) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		this.threadPoolName = threadPoolName;
	}

	public SimpleThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, String threadPoolName) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
		this.threadPoolName = threadPoolName;
	}

	public SimpleThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler, String threadPoolName) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
		this.threadPoolName = threadPoolName;
	}

	public SimpleThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler,
			String threadPoolName) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
		this.threadPoolName = threadPoolName;
	}

	/**
	 * 修改thread name
	 * 
	 * @param t
	 * @param r
	 */
	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		if (!t.getName().toLowerCase().contains("crawler")) {
			String[] strings = t.getName().split("-");
			t.setName("crawler-" + this.threadPoolName + "-thread-" + strings[strings.length - 1]);
		}
	}
}
