package asynchronous.async.gpcoder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */

public class CompletableFuture3 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("Run a task specified by a Runnable Object asynchronously.");
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
			System.out.println("It is runnig in a separate thread than the main thread.");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return "Completed";
		});

		System.out.println("It is also running... ");
		// Block and wait for the future to complete
		System.out.println("Result: " + future.get());
		System.out.println("Done!!!");
	}
}