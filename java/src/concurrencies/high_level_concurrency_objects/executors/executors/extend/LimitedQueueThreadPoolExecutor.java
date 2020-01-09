package executors.extend;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class LimitedQueueThreadPoolExecutor implements ExecutorService {

	private final ThreadPoolExecutor threadPoolExecutor;
	private final BlockingQueue<Runnable> queue;
	private final int maxElementsInQueue;

	public LimitedQueueThreadPoolExecutor(final int numberOfThreads, final int maxElementsInQueue) {
		this.maxElementsInQueue = maxElementsInQueue;
		queue = new LinkedBlockingDeque<Runnable>();
		threadPoolExecutor = new ThreadPoolExecutor(numberOfThreads, numberOfThreads, 1, TimeUnit.SECONDS, queue);
	}

	protected void waitTillFreeSpaceInQueue() {
		while (!threadPoolExecutor.isTerminated() && queue.size() >= maxElementsInQueue) {
			try {
				Thread.sleep(1);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void execute(final Runnable command) {
		waitTillFreeSpaceInQueue();
		threadPoolExecutor.execute(command);
	}

	public boolean awaitTermination(final long timeout, final TimeUnit unit) throws InterruptedException {
		return threadPoolExecutor.awaitTermination(timeout, unit);
	}

	public <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks) throws InterruptedException {
		waitTillFreeSpaceInQueue();
		return threadPoolExecutor.invokeAll(tasks);
	}

	public <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks, final long timeout,
			final TimeUnit unit) throws InterruptedException {
		waitTillFreeSpaceInQueue();
		return threadPoolExecutor.invokeAll(tasks, timeout, unit);
	}

	public <T> T invokeAny(final Collection<? extends Callable<T>> tasks)
			throws InterruptedException, ExecutionException {
		waitTillFreeSpaceInQueue();
		return threadPoolExecutor.invokeAny(tasks);
	}

	public <T> T invokeAny(final Collection<? extends Callable<T>> tasks, final long timeout, final TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		waitTillFreeSpaceInQueue();
		return threadPoolExecutor.invokeAny(tasks, timeout, unit);
	}

	public boolean isShutdown() {
		return threadPoolExecutor.isShutdown();
	}

	public boolean isTerminated() {
		return threadPoolExecutor.isTerminated();
	}

	public void shutdown() {
		threadPoolExecutor.shutdown();
	}

	public List<Runnable> shutdownNow() {
		return threadPoolExecutor.shutdownNow();
	}

	public <T> Future<T> submit(final Callable<T> task) {
		waitTillFreeSpaceInQueue();
		return threadPoolExecutor.submit(task);
	}

	public Future<?> submit(final Runnable task) {
		waitTillFreeSpaceInQueue();
		return threadPoolExecutor.submit(task);
	}

	public <T> Future<T> submit(final Runnable task, final T result) {
		waitTillFreeSpaceInQueue();
		return threadPoolExecutor.submit(task, result);
	}

}
