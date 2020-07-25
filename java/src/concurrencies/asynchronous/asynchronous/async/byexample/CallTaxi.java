package asynchronous.async.byexample;

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

public class CallTaxi {

	final static int TIME_TO_CALL_A = 3000;
	final static int TIME_TO_CALL_B = 4000;
	final static int TIME_TO_CALL_C = 2000;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("Start processor");
		long startTime = System.currentTimeMillis();
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
			System.out.println(" Calling tài xế A ...");
			try {
				Thread.sleep(TIME_TO_CALL_A);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return "Tài xế A đã phản hồi";
		});

		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
			System.out.println(" Calling tài xế B ...");
			try {
				Thread.sleep(TIME_TO_CALL_B);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return "Tài xế B đã phản hồi";
		});

		CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
			System.out.println(" Calling tài xế C ...");
			try {
				Thread.sleep(TIME_TO_CALL_C);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return "Tài xế C đã phản hồi";
		});

		CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(future1, future2, future3);
		System.out.println(anyOfFuture.get());
		System.out.println("Hoàn thành process trong :" + (System.currentTimeMillis() - startTime) + "ms");
	}
}