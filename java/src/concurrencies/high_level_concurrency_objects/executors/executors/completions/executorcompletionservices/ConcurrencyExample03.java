package executors.completions.executorcompletionservices;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ConcurrencyExample03 {

	/*
	 * Example on ExecutorCompletionService.ExecutorCompletionService.take()
	 * method blocks until any Callable task completes it's execution.
	 */
	public static void main(String[] args) throws InterruptedException,
			ExecutionException {

		ExecutorService executorService = Executors.newFixedThreadPool(5);
		ExecutorCompletionService<Long> executorCompletionService = new ExecutorCompletionService<>(
				executorService);
		executorCompletionService.submit(() -> {
			long count = 0;
			for (int i = 0; i < 20; i++) {
				count += i;
				Thread.sleep(200);
			}
			return count;
		});
		executorCompletionService.submit(() -> {
			long count = 0;
			for (int i = 0; i < 10; i++) {
				count += i;
				Thread.sleep(200);
			}

			return count;
		});

		for (int i = 0; i < 2; i++) {
			Future<Long> future = executorCompletionService.take();
			System.out.println("Future task " + future.toString()
					+ " has result " + future.get());
		}

		executorService.shutdown();

	}
}
