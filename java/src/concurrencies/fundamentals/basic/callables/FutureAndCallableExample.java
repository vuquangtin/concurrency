package basic.callables;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Design Patterns
 * 
 * @see https://github.com/callicoder/java-concurrency-examples
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class FutureAndCallableExample {
	public static void main(String[] args) throws InterruptedException,
			ExecutionException {
		ExecutorService executorService = Executors.newSingleThreadExecutor();

		Callable<String> callable = () -> {
			// Perform some computation
			System.out.println("Entered Callable");
			Thread.sleep(2000);
			return "Hello from Callable";
		};

		System.out.println("Submitting Callable");
		Future<String> future = executorService.submit(callable);
		// This line executes immediately
		System.out
				.println("Do something else while callable is getting executed");

		System.out.println("Retrieve the result of the future");
		// Future.get() blocks until the result is available
		String result = future.get();
		System.out.println(result);

		executorService.shutdown();
	}
}