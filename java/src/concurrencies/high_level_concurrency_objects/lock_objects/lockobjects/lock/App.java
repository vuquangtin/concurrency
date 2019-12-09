package lockobjects.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class App {

	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	/**
	 * Program entry point
	 * 
	 * @param args
	 *            command line args
	 */
	public static void main(String[] args) {

		ExecutorService executeService = Executors.newFixedThreadPool(10);
		ReaderWriterLock lock = new ReaderWriterLock();

		// Start writers
		IntStream.range(0, 5).forEach(
				i -> executeService.submit(new Writer("Writer " + i, lock
						.writeLock(), ThreadLocalRandom.current()
						.nextLong(5000))));
		LOGGER.info("Writers added...");

		// Start readers
		IntStream.range(0, 5)
				.forEach(
						i -> executeService.submit(new Reader("Reader " + i,
								lock.readLock(), ThreadLocalRandom.current()
										.nextLong(10))));
		LOGGER.info("Readers added...");

		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			LOGGER.error("Error sleeping before adding more readers", e);
			Thread.currentThread().interrupt();
		}

		// Start readers
		IntStream.range(6, 10)
				.forEach(
						i -> executeService.submit(new Reader("Reader " + i,
								lock.readLock(), ThreadLocalRandom.current()
										.nextLong(10))));
		LOGGER.info("More readers added...");

		// In the system console, it can see that the read operations are
		// executed concurrently while
		// write operations are exclusive.
		executeService.shutdown();
		try {
			executeService.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			LOGGER.error("Error waiting for ExecutorService shutdown", e);
			Thread.currentThread().interrupt();
		}

	}

}