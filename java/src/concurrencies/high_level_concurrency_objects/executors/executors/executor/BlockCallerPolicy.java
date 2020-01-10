package executors.executor;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class BlockCallerPolicy implements RejectedExecutionHandler {
	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		try {
			executor.getQueue().put(r);
		} catch (InterruptedException e) {
			throw new RejectedExecutionException(String.format(
					"Task %s rejected from ", r.toString()), e);
		}
	}
}