package concurrency.java.wrappers;

import java.util.concurrent.Callable;
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
public final class ThreadDelegatingDecorator {
	public static final CallableWrapper THREAD_DELEGATING_WRAPPER = new ThreadDelegatingCallableWrapper();

	private ThreadDelegatingDecorator() {
	}

	public static Executor wrapExecutor(final Executor wrappedExecutor) {
		return DecoratingExecutors.decorate(wrappedExecutor,
				THREAD_DELEGATING_WRAPPER);
	}

	public static ExecutorService wrapExecutorService(
			final ExecutorService wrappedExecutorService) {
		return DecoratingExecutors.decorate(wrappedExecutorService,
				THREAD_DELEGATING_WRAPPER);
	}

	private static class ThreadDelegatingCallableWrapper extends
			CallableWrapper {
		@Override
		public <T> Callable<T> wrap(Callable<T> callable) {
			return new DelegatingCallable<T>(callable);
		}

		@Override
		public Runnable wrap(Runnable runnable) {
			return new DelegatingRunnable(runnable);
		}
	}

	private static class DelegatingRunnable implements Runnable {
		private final Runnable wrappedRunnable;

		DelegatingRunnable(final Runnable wrappedRunnable) {
			this.wrappedRunnable = wrappedRunnable;

		}

		@Override
		public void run() {

			try {

				wrappedRunnable.run();
			} finally {

			}
		}
	}

	private static class DelegatingCallable<C> implements Callable<C> {
		private final Callable<C> wrappedCallable;

		DelegatingCallable(final Callable<C> wrappedCallable) {
			this.wrappedCallable = wrappedCallable;

		}

		@Override
		public C call() throws Exception {

			try {

				return wrappedCallable.call();
			} finally {

			}
		}
	}
}
