package threadpools.mycustom.pool.common4j;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

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
	private AtomicLong _count;
	private static final String DEFAULT_THREAD_NAME_PRIFIX = "aofeng-thread";
	private ThreadGroup _group;
	private String _threadNamePrefix;

	public DefaultThreadFactory() {
		this("aofeng-thread");
	}

	public DefaultThreadFactory(String threadNamePrefix) {
		this._count = new AtomicLong(1L);
		this._threadNamePrefix = "aofeng-thread";
		this._threadNamePrefix = threadNamePrefix;
		ThreadGroup root = this.getRootThreadGroup();
		this._group = new ThreadGroup(root, this._threadNamePrefix);
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(this._group, r);
		thread.setName(this._threadNamePrefix + "-" + this._count.getAndIncrement());
		if (thread.isDaemon()) {
			thread.setDaemon(false);
		}
		if (5 != thread.getPriority()) {
			thread.setPriority(5);
		}
		return thread;
	}

	private ThreadGroup getRootThreadGroup() {
		ThreadGroup threadGroup;
		for (threadGroup = Thread.currentThread().getThreadGroup(); null != threadGroup
				.getParent(); threadGroup = threadGroup.getParent()) {
		}
		return threadGroup;
	}
}