package executors.threads;

import java.util.concurrent.ThreadFactory;
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
public class DefaultThreadFactory implements ThreadFactory {

	private static final AtomicInteger poolNumber = new AtomicInteger(1);
	private final ThreadGroup threadGroup;
	private final AtomicInteger currentThreadNumber = new AtomicInteger(1);
	private final String namePrefix;
	private int priority = Thread.NORM_PRIORITY;
	private boolean isDaemon = false;

	public DefaultThreadFactory(String prefix) {
		this(prefix, false);
	}

	public DefaultThreadFactory(String prefix, boolean isDaemon) {
		this(prefix, isDaemon, Thread.NORM_PRIORITY);
	}

	public DefaultThreadFactory(String prefix, boolean isDaemon, int priority) {
		SecurityManager s = System.getSecurityManager();
		this.threadGroup = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		this.namePrefix = prefix + "-" + poolNumber.getAndIncrement() + "-thread-";
		this.isDaemon = isDaemon;
		this.priority = priority;
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(threadGroup, r, namePrefix + currentThreadNumber.getAndIncrement(), 0);
		thread.setDaemon(isDaemon);
		thread.setPriority(priority);
		return thread;
	}
}