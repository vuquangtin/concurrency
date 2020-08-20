package concurrency.java.wrappers;

import java.util.concurrent.Callable;

import com.google.common.base.Throwables;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public abstract class CallableWrapper {
	public abstract <T> Callable<T> wrap(Callable<T> callable);

	public Runnable wrap(Runnable runnable) {
		CallableRunnable unwrapped = new CallableRunnable(runnable);
		Callable<Void> wrapped = wrap(unwrapped);
		if (wrapped == unwrapped) {
			return runnable;
		}
		return new RunnableCallable(wrapped);
	}

	private static class RunnableCallable implements Runnable {
		private final Callable<?> callable;

		public RunnableCallable(Callable<?> callable) {
			this.callable = callable;
		}

		@Override
		public void run() {
			try {
				callable.call();
			} catch (Exception e) {
				throw Throwables.propagate(e);
			}
		}
	}

	private static class CallableRunnable implements Callable<Void> {
		private final Runnable runnable;

		public CallableRunnable(Runnable runnable) {
			this.runnable = runnable;
		}

		@Override
		public Void call() throws Exception {
			runnable.run();
			return null;
		}
	}
}