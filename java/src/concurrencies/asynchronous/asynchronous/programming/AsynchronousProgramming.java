package asynchronous.programming;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class AsynchronousProgramming {

	public static void main(String[] args) throws ExecutionException,
			InterruptedException {
		completableFutureRunAsync();
		completableFutureRunAsyncWithLambda();
	}

	private static void completableFutureRunAsync() throws ExecutionException,
			InterruptedException {

		System.out.println("Run async a Runnable in a Completable Future...");

		// Run a task specified by a Runnable Object asynchronously.
		CompletableFuture<Void> future = CompletableFuture
				.runAsync(ConcurrencyUtil
						.createRunnableTask(
								"i'm an async runnable running in a completable future",
								5000));

		// Block and wait for the future to complete - nothing is returned
		System.out.println(future.get());

		System.out.println("------------------------------");
	}

	private static void completableFutureRunAsyncWithLambda()
			throws ExecutionException, InterruptedException {

		System.out.println("Run async a Runnable in a Completable Future...");

		// Run a task specified by a Runnable Object asynchronously.
		CompletableFuture<Void> future = CompletableFuture
				.runAsync(() -> {
					try {
						System.out.println(String
								.format("%s: %s", Thread.currentThread()
										.getName(),
										"i'm an async runnable running in a completable future"));
						TimeUnit.MILLISECONDS.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				});

		// Block and wait for the future to complete - nothing is returned
		System.out.println(future.get());

		System.out.println("------------------------------");
	}
}
