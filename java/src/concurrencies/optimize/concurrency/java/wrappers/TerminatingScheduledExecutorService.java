package concurrency.java.wrappers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class TerminatingScheduledExecutorService extends
		DelegatingScheduledExecutorService implements AutoCloseable {
	static Logger logger = Logger
			.getLogger(TerminatingScheduledExecutorService.class.getName());

	private final int timeout;
	private final TimeUnit units;

	TerminatingScheduledExecutorService(ScheduledExecutorService delegate,
			int timeout, TimeUnit units) {
		super(delegate);
		this.timeout = timeout;
		this.units = units;
	}

	@Override
	public void close() throws TimeoutException {
		ExecutorService delegate = getDelegate();
		delegate.shutdown();
		try {
			if (!delegate.awaitTermination(timeout, units)) {
				throw new TimeoutException("Executor service " + delegate
						+ " did not shutdown after " + timeout + " " + units);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			logger.debug("While awaiting executor service termination");
		}
	}
}