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

public class HandleException {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		CompletableFuture<String> handleAge = CompletableFuture.supplyAsync(() -> -1).thenApply(age -> {
			if (age < 0) {
				throw new IllegalArgumentException("Tuổi không thể âm!");
			}
			if (age > 18) {
				return "Nguoi lon";
			} else {
				return "Tre em";
			}
		}).exceptionally(ex -> {
			System.out.println("Bạn đã nhập sai tuổi - " + ex.getMessage());
			return "Chưa rõ tuổi";
		});
		System.out.println("Maturity : " + handleAge.get());
	}
}