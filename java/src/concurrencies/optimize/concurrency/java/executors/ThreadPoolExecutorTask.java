package concurrency.java.executors;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ThreadPoolExecutorTask {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = new ThreadPoolExecutor(10, 20, 30, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(10), r -> {
					Thread thread = new Thread(r);
					return thread;
				}, new ThreadPoolExecutor.AbortPolicy());

		IntStream.range(0, 40).boxed().forEach(i -> {
			executorService.execute(() -> {
				try {
					TimeUnit.SECONDS.sleep(10);
					System.out.println(Thread.currentThread().getName() + "[" + i + "] finish done");
				} catch (InterruptedException e) {
					// e.printStackTrace();
				}
			});

		});

		// executorService.shutdown();
		// executorService.awaitTermination(2, TimeUnit.SECONDS);

		List<Runnable> runnableList = executorService.shutdownNow();
		System.out.println(runnableList);
		System.out.println(runnableList.size());
		System.out.println("-------------------over----------------------");

	}
}