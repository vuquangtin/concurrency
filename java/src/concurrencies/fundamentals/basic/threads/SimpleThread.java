package basic.threads;

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
public class SimpleThread extends Thread {

	private String message;

	// standard logger, constructor

	public SimpleThread(String message) {
		this.message = message;
	}

	@Override
	public void run() {
		System.out.println(message);
	}

	public static void main(String[] args) throws Exception {

		Thread thread = new SimpleThread("SimpleThread executed using Thread");
		thread.start();
		thread.join();
	}

	public void givenAThread_whenSubmitToES_thenResult() throws Exception {
		ScheduledExecutorService executorService = Executors
				.newScheduledThreadPool(1);
		executorService
				.submit(new SimpleThread(
						"SimpleThread executed using ExecutorService")).get();
	}
}
