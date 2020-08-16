package concurrency.java.optimize;

import java.util.concurrent.Executor;
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
public class ExecutorCountWrapper implements Executor {
	private final Executor executor;
	private final AtomicInteger count = new AtomicInteger();

	public ExecutorCountWrapper(Executor executor) {
		this.executor = executor;
	}

	@Override
	public void execute(final Runnable command) {
		count.incrementAndGet();
		executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					command.run();
				} finally {
					count.decrementAndGet();
				}
			}
		});
	}

	public int getTaskCount() {
		return count.get();
	}
}
