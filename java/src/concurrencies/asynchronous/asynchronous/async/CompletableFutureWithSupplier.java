package asynchronous.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class CompletableFutureWithSupplier {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newSingleThreadExecutor();

		Supplier<String> supplier = () -> {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return Thread.currentThread().getName();
		};

		CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(supplier, executor);
		completableFuture.obtrudeValue("Too long!");

		String string = completableFuture.join();
		System.out.println("Result = " + string);

		string = completableFuture.join();
		System.out.println("Result = " + string);

		executor.shutdown();
	}
}