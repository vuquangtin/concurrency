package basic.runables;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class SimpleRunnable implements Runnable {

	private String message;

	public SimpleRunnable(String message) {
		this.message = message;
	}

	// standard logger, constructor

	@Override
	public void run() {
		System.out.println(message);
	}

	public void givenRunnable_whenRunIt_thenResult() throws Exception {
		Thread thread = new Thread(new SimpleRunnable(
				"SimpleRunnable executed using Thread"));
		thread.start();
		thread.join();
	}

	public void givenARunnable_whenSubmitToES_thenResult() throws Exception {
		ScheduledExecutorService executorService = Executors
				.newScheduledThreadPool(1);
		executorService.submit(
				new SimpleRunnable(
						"SimpleRunnable executed using ExecutorService")).get();
	}

	public void givenARunnableLambda_whenSubmitToES_thenResult()
			throws Exception {
		ScheduledExecutorService executorService = Executors
				.newScheduledThreadPool(1);
		executorService.submit(() -> System.out
				.println("Lambda runnable executed!"));
	}
}