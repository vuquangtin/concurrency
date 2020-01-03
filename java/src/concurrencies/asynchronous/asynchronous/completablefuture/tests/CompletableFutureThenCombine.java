package asynchronous.completablefuture.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * <h1>CompletableFuture thenCompose and thenCombine</h1> thenCombine<br/>
 * As name suggest thenCombine is used to merge results of two independent
 * CompletableFuture. Assume that for a person we get first name and last name
 * by calling two different independent methods. To get the Full name we want ot
 * merge results of both the methods then we will use thenCombine.
 * 
 * 
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class CompletableFutureThenCombine {

	public CompletableFuture<String> findFirstName() {

		return CompletableFuture.supplyAsync(() -> {
			sleep(1);
			System.out.println(Thread.currentThread() + " findFirstName");
			return "Niraj";
		});

	}

	public CompletableFuture<String> findLastName() {

		return CompletableFuture.supplyAsync(() -> {
			sleep(1);
			System.out.println(Thread.currentThread() + " findLastName");
			return "Sonawane";
		});
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
	public void completableFutureThenCombine() {

		CompletableFuture<String> thenCombine = findFirstName().thenCombine(findLastName(), (firstName, lastname) -> {
			return firstName + lastname;
		});
		String fullName = thenCombine.join();
		assertEquals("NirajSonawane", fullName);

	}

}
