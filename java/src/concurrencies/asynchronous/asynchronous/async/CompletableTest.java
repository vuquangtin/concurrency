package asynchronous.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class CompletableTest {
	public static void main(String... args) throws ExecutionException, InterruptedException {
		final CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> doSomethingAndReturnA())
				.thenApply(a -> convertToB(a));

		future.get();
	}

	private static int convertToB(final String a) {
		System.out.println("convertToB: " + Thread.currentThread().getName());
		return Integer.parseInt(a);
	}

	private static String doSomethingAndReturnA() {
		System.out.println("doSomethingAndReturnA: " + Thread.currentThread().getName());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return "1";
	}
}