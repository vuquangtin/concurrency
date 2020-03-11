package learn.threads.vn;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import basic.futuretasks.MyCallable;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class MyRunnableUseExecutorService implements Runnable {

	@Override
	public void run() {
		try {
			String threadName = Thread.currentThread().getName();
			System.out.println("Running in " + threadName);
			Thread.sleep(5000);
			System.out.println("Finish in " + threadName);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void runConcurrency() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(4);
		MyCallable callable1 = new MyCallable();
		MyCallable callable2 = new MyCallable();
		List<Future<String>> futures = executor.invokeAll(Arrays.asList(
				callable1, callable2));
		futures.parallelStream().forEach(future -> {
			try {
				String result = future.get();
				System.out.println("Finish in " + result);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		});
		executor.shutdown();
	}
}