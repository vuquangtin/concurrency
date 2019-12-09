package basic.app2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
	 * @param args
	 *            the command line arguments - not used
	 */
	public static void main(String... args) {
		final WashingMachine washingMachine = new WashingMachine();
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 3; i++) {
			executorService.execute(washingMachine::wash);
		}
		executorService.shutdown();
		try {
			executorService.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException ie) {
			LOGGER.error("ERROR: Waiting on executor service shutdown!");
		}
	}
	
}
