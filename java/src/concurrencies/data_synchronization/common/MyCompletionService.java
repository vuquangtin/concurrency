package common;

import java.util.Random;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class MyCompletionService {
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		ExecutorService executor = Executors.newFixedThreadPool(10);
		CompletionService completionService = new ExecutorCompletionService(executor);
		for (int i = 1; i <= 10; i++) {
			final int result = i;
			completionService.submit(() -> {
				Thread.sleep(new Random().nextInt(3000));
				return result;
			});
		}
		System.out.println(completionService.take().get());
		executor.shutdown();
	}
}