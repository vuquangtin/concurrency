package concurrency.java.optimize;

import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import concurrencies.utilities.Log4jUtils;
import concurrency.java.optimize.tasks.MyRunnableSemaphore;

/**
 * 
 * JavaThreadingOptimizationUsingThreadPoolExecutor
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class JTOUsingThreadPoolExecutorAndSemaphore {
	static Logger logger = Logger
			.getLogger(JTOUsingThreadPoolExecutorAndSemaphore.class.getName());
	private static final int queueSize = 10;
	// private static final int queueSizeSleep = 1000;
	private static AtomicInteger counter = new AtomicInteger();

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		ThreadPoolExecutor executor;
		int sizeOfThreadPoolExecutor = 25;
		Semaphore semaphone = new Semaphore(queueSize
				+ sizeOfThreadPoolExecutor);
		executor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(sizeOfThreadPoolExecutor);
		while (true) {
			try {
				semaphone.acquire();
				executor.execute(new MyRunnableSemaphore(counter.get(),
						semaphone));
				counter.incrementAndGet();
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				ex.printStackTrace();
			}
			if (counter.get() > 100)
				break;
		}
		executor.shutdown();
	}

}
