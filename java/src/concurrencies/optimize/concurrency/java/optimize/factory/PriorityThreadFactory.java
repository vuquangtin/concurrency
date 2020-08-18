package concurrency.java.optimize.factory;

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
public class PriorityThreadFactory implements ThreadFactory {

	private final String name;
	private final int priority;
	private boolean daemon;
	protected final AtomicInteger id = new AtomicInteger(1);

	public PriorityThreadFactory(String name) {
		this(name, Thread.NORM_PRIORITY, false);
	}

	public PriorityThreadFactory(String name, int priority) {
		this(name, priority, false);
	}

	public PriorityThreadFactory(String name, int priority, boolean daemon) {
		this.name = name;
		this.priority = priority;
		this.daemon = daemon;
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(r, name.concat("-") + id.getAndDecrement());
		thread.setPriority(priority);
		thread.setDaemon(daemon);
		return thread;
	}
}
