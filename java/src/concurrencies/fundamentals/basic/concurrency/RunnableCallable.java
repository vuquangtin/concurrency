package basic.concurrency;

import java.util.concurrent.Callable;

public class RunnableCallable implements Callable<Void>, Runnable {
	private Runnable runnable;
	private Callable<?> callable;

	/**
	 * Constructor that takes a runnable.
	 *
	 * @param runnable
	 *            runnable.
	 */
	public RunnableCallable(Runnable runnable) {
		this.runnable = Check.notNull(runnable, "runnable");
	}

	/**
	 * Constructor that takes a callable.
	 *
	 * @param callable
	 *            callable.
	 */
	public RunnableCallable(Callable<?> callable) {
		this.callable = Check.notNull(callable, "callable");
	}

	/**
	 * Invokes the wrapped callable/runnable as a callable.
	 *
	 * @return void
	 *
	 * @throws Exception
	 *             thrown by the wrapped callable/runnable invocation.
	 */
	@Override
	public Void call() throws Exception {
		if (runnable != null) {
			runnable.run();
		} else {
			callable.call();
		}
		return null;
	}

	/**
	 * Invokes the wrapped callable/runnable as a runnable.
	 *
	 * @throws RuntimeException
	 *             thrown by the wrapped callable/runnable invocation.
	 */
	@Override
	public void run() {
		if (runnable != null) {
			runnable.run();
		} else {
			try {
				callable.call();
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	/**
	 * Returns the class name of the wrapper callable/runnable.
	 *
	 * @return the class name of the wrapper callable/runnable.
	 */
	@Override
	public String toString() {
		return (runnable != null) ? runnable.getClass().getSimpleName() : callable.getClass().getSimpleName();
	}
}