package concurrency.java.optimize;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import concurrencies.utilities.Log4jUtils;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class JavaThreadingOptimizationUsingThreadPoolExecutor {
	static Logger logger = Logger
			.getLogger(JavaThreadingOptimizationUsingThreadPoolExecutor.class
					.getName());
	private static final int queueSize = 250;
	private static final int queueSizeSleep = 1000;
	private static AtomicInteger counter = new AtomicInteger();

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		ThreadPoolExecutor executor;
		int sizeOfThreadPoolExecutor = 25;

		executor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(sizeOfThreadPoolExecutor);
		while (true) {
			try {
				executor.execute(new MyRunnable(counter.get()));
				counter.incrementAndGet();
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				ex.printStackTrace();
			}
			try {
				logger.debug("queueSize:" + executor.getQueue().size());
				if (executor.getQueue().size() >= queueSize) {
					logger.debug("sleep " + queueSizeSleep + " ms:["
							+ counter.get() + "]");
					Thread.sleep(queueSizeSleep);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

}
