package executors.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/***
 * Important Points:
 * 
 * 1) newFixedThreadPool() method creates an executor with a maximum number of
 * threads at any time. If you send more tasks than the number of threads, the
 * remaining tasks will be blocked until there is a free thread to process them
 * This method receives the maximum number of threads as a parameter you want to
 * have in your executor. In your case, you have created an executor with four
 * threads.
 * 
 * 2) The Executors class also provides the newSingleThreadExecutor() method.
 * This is an extreme case of a fixed-size thread executor. It creates an
 * executor with only one thread, so it can only execute one task at a time.
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class FixedThreadPoolExample {

	public static final int NUM_OF_THREAD = 5;

	public static void main(String[] args) {

		ExecutorService executor = Executors.newFixedThreadPool(NUM_OF_THREAD);

		for (int i = 1; i <= 10; i++) {
			Runnable worker = new WorkerThread("" + i);
			executor.execute(worker);
		}

		/*
		 * Initiates an orderly shutdown in which previously submitted tasks are
		 * executed, but no new tasks will be accepted. Invocation has no
		 * additional effect if already shut down. This method does not wait for
		 * previously submitted tasks to complete execution. Use
		 * awaitTermination to do that.
		 */
		executor.shutdown();

		// Wait until all threads are finish
		while (!executor.isTerminated()) {
			// Running ...
		}

		System.out.println("Finished all threads");
	}
}