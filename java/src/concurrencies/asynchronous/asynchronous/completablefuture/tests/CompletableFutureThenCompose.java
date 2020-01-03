package asynchronous.completablefuture.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * thenCompose<br/>
 * Let’s Say we want to first find Account Number and then calculate Balance for
 * that account and after calculations we want to send notifications.<br/>
 * Now All these task are Dependent and methods are returning CompletableFuture
 * , Then We need to use thenCompose Method. This is similar to flatMap in case
 * of Streams.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class CompletableFutureThenCompose {

	public CompletableFuture<Integer> findAccountNumber() {
		sleep(1);
		System.out.println(Thread.currentThread() + " findAccountNumber");
		return CompletableFuture.completedFuture(10);
	}

	public CompletableFuture<Integer> calculateBalance(int accountNumber) {
		System.out.println(Thread.currentThread() + " calculateBalance");
		sleep(1);
		return CompletableFuture.completedFuture(accountNumber * accountNumber);
	}

	public CompletableFuture<Integer> notifyBalance(Integer balance) {
		System.out.println(Thread.currentThread() + "Sending Notification");
		sleep(1);
		return CompletableFuture.completedFuture(balance);

	}

	private void sleep(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void completableFutureThenCompose() {

		Integer join = findAccountNumber().thenComposeAsync(this::calculateBalance).thenCompose(this::notifyBalance)
				.join();
		assertEquals(new Integer(100), join);

	}

}