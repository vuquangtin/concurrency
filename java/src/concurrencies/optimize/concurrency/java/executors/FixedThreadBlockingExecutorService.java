package concurrency.java.executors;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class FixedThreadBlockingExecutorService extends AbstractExecutorService {

	private final ExecutorService executor;
	private final Semaphore blockExecution;

	public FixedThreadBlockingExecutorService(int nTreads) {
		this.executor = Executors.newFixedThreadPool(nTreads);
		blockExecution = new Semaphore(nTreads);
	}

	@Override
	public void shutdown() {
		executor.shutdown();
	}

	@Override
	public List<Runnable> shutdownNow() {
		return executor.shutdownNow();
	}

	@Override
	public boolean isShutdown() {
		return executor.isShutdown();
	}

	@Override
	public boolean isTerminated() {
		return executor.isTerminated();
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return executor.awaitTermination(timeout, unit);
	}

	@Override
	public void execute(Runnable command) {
		blockExecution.acquireUninterruptibly();
		executor.execute(() -> {
			try {
				command.run();
			} finally {
				blockExecution.release();
			}
		});
	}
}