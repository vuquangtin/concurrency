package executors.executor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class NamedThreadFactory implements ThreadFactory {

	private static final AtomicInteger threadPoolNumber = new AtomicInteger(1);
	private static final AtomicInteger threadNumber = new AtomicInteger(1);
	private final String prefix;
	private final boolean isDaemon;
	private final ThreadGroup threadGroup;

	public NamedThreadFactory(String prefix) {
		this(prefix, true);
	}

	public NamedThreadFactory() {
		this(true);
	}

	public NamedThreadFactory(boolean daemon) {
		this("mc-pool-" + threadPoolNumber.getAndIncrement(), daemon);
	}

	public NamedThreadFactory(String prefix, boolean daemon) {
		this.prefix = prefix + "-thread-";
		this.isDaemon = daemon;
		SecurityManager s = System.getSecurityManager();
		this.threadGroup = (s == null) ? Thread.currentThread()
				.getThreadGroup() : s.getThreadGroup();
	}

	@Override
	public Thread newThread(Runnable runnable) {
		String name = prefix + threadNumber.getAndIncrement();
		Thread ret = new Thread(threadGroup, runnable, name, 0);
		ret.setDaemon(isDaemon);
		return ret;
	}

	public ThreadGroup getThreadGroup() {
		return threadGroup;
	}

}