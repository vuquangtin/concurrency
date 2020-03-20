package running.threads.managers.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Main {

	public static void main(String[] args) throws InterruptedException {
		runThread();
		runConcurrency();
	}

	private static void runThread() {
		MyRunnable runnable1 = new MyRunnable();
		MyRunnable runnable2 = new MyRunnable();
		MyRunnable runnable3 = new MyRunnable();
		MyRunnable runnable4 = new MyRunnable();
		MyRunnable runnable5 = new MyRunnable();
		MyRunnable runnable6 = new MyRunnable();
		Thread thread1 = new Thread(runnable1);
		Thread thread2 = new Thread(runnable2);
		Thread thread3 = new Thread(runnable3);
		Thread thread4 = new Thread(runnable4);
		Thread thread5 = new Thread(runnable5);
		Thread thread6 = new Thread(runnable6);
		Arrays.asList(thread1, thread2, thread3, thread4, thread5, thread6)
				.parallelStream().forEach(Thread::start);
	}

	private static void runConcurrency() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(4);
		MyCallable callable1 = new MyCallable();
		MyCallable callable2 = new MyCallable();
		MyCallable callable3 = new MyCallable();
		MyCallable callable4 = new MyCallable();
		MyCallable callable5 = new MyCallable();
		MyCallable callable6 = new MyCallable();
		List<Future<String>> futures = executor.invokeAll(Arrays.asList(
				callable1, callable2, callable3, callable4, callable5,
				callable6));
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
