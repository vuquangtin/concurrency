package concurrency.java.optimize.google;

import java.util.concurrent.Executor;

/**
 * An executor which executes its tasks sequentially.
 *
 * Differs from Executors#newSingleThreadExecutor by sourcing a thread from a
 * shared executor when needed.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class SequentialExecutor extends BoundedExecutor {
	public SequentialExecutor(Executor executor) {
		super(executor, 1);
	}
}