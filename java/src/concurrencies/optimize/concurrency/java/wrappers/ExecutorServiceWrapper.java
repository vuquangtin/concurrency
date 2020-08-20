package concurrency.java.wrappers;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
public class ExecutorServiceWrapper {

	private final ExecutorService executor;

	public ExecutorServiceWrapper(int nrMaxThreads) {
		executor = Executors.newFixedThreadPool(nrMaxThreads);
	}

	public <V> Future<V> submit(Callable<V> task) {
		return executor.submit(task);
	}

	public void shutdown() {
		executor.shutdown();
	}

	public void shutdownWait(long timeout) {
		executor.shutdown();
		try {
			if (!executor.awaitTermination(timeout, TimeUnit.MILLISECONDS)) {
				executor.shutdownNow();
			}
		} catch (InterruptedException e) {
			executor.shutdownNow();
		}
	}
}