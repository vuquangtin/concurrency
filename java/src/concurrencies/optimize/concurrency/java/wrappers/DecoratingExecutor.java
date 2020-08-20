package concurrency.java.wrappers;

import java.util.concurrent.Executor;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class DecoratingExecutor implements Executor {
	private final Executor wrappedExecutor;
	private final CallableWrapper wrapper;

	DecoratingExecutor(final Executor wrappedExecutor,
			final CallableWrapper wrapper) {
		this.wrappedExecutor = wrappedExecutor;
		this.wrapper = wrapper;
	}

	@Override
	public void execute(final Runnable runnable) {
		wrappedExecutor.execute(wrapper.wrap(runnable));
	}
}