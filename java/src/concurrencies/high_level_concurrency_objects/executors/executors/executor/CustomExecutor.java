package executors.executor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class CustomExecutor {

	private final ThreadPoolExecutor executor;
	private final Semaphore semaphore;

	public CustomExecutor(int corePoolSize, int maxPoolSize, int keepAliveTime,
			TimeUnit unit, int queueSize, ThreadFactory factory,
			RejectedExecutionHandler rejectedHandler) {
		BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(
				queueSize);
		this.executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize,
				keepAliveTime, unit, queue, factory, rejectedHandler);
		this.semaphore = new Semaphore(queueSize + maxPoolSize);
	}

	public void shutdown() {
		this.executor.shutdown();
	}

	public ThreadPoolExecutor getExecutor() {
		return executor;
	}

	private void exec(final Runnable command) throws InterruptedException {
		semaphore.acquire();
		try {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						command.run();
					} finally {
						semaphore.release();
					}
				}
			});
		} catch (RejectedExecutionException e) {
			semaphore.release();
			throw e;
		}
	}

	public void execute(Runnable command) throws InterruptedException {
		exec(command);
	}
}