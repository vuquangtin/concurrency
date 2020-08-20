package concurrency.java.tiny.netty;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreadPerTaskExecutor implements Executor {

	private final ThreadFactory threadFactory;

	public ThreadPerTaskExecutor(ThreadFactory threadFactory) {
		if (threadFactory == null) {
			throw new IllegalArgumentException("threadFactory is null");
		}
		this.threadFactory = threadFactory;
	}

	@Override
	public void execute(Runnable task) {
		threadFactory.newThread(task).start();
	}
}