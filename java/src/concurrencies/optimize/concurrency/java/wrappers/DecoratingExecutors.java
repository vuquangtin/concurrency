package concurrency.java.wrappers;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public final class DecoratingExecutors {
	private DecoratingExecutors() {
	}

	public static Executor decorate(Executor executor, CallableWrapper wrapper) {
		return new DecoratingExecutor(executor, wrapper);
	}

	public static ExecutorService decorate(ExecutorService executor,
			CallableWrapper wrapper) {
		return new DecoratingExecutorService(executor, wrapper);
	}
}