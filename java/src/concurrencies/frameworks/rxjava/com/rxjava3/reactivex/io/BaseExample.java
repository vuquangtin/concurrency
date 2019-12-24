package com.rxjava3.reactivex.io;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class BaseExample {
	static Logger logger = Logger.getLogger(BaseExample.class.getName());

	protected final List<String> desserts = Arrays.asList("Biscuits / cookies",
			"Cakes", "Chocolates", "Candies", "Custards", "Puddings",
			"Deep-fried desserts", "Frozen desserts", "Jellied desserts",
			"Pastries", "Pies", "Cobblers", "Clafoutis", "Sweet soups");

	protected final Random random = new Random();

	/**
	 * List of Desserts
	 * 
	 * @return
	 */
	public List<String> getDesserts() {
		return desserts;
	}

	/**
	 * Get random dessert
	 * 
	 * @return
	 */
	protected String getRandomDessert() {
		int pos = random.nextInt(desserts.size());
		String dessert = desserts.get(pos);
		return dessert;
	}

	/**
	 * Utility to compute the time in nanoseconds
	 * 
	 * @param startTime
	 * @return
	 */
	protected long calcTime(long startTime) {
		long endTime = System.nanoTime();
		return TimeUnit.MILLISECONDS.convert(endTime - startTime,
				TimeUnit.NANOSECONDS);
	}

	protected void shutDownExecutor(ExecutorService exec) {

		try {
			logger.info("attempt to shutdown executor");
			exec.shutdown();
			exec.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			logger.warning("tasks interrupted");
		} finally {
			if (!exec.isTerminated()) {
				logger.warning("cancel non-finished tasks");
			}
			exec.shutdownNow();
			logger.info("shutdown finished");
		}

	}

}
