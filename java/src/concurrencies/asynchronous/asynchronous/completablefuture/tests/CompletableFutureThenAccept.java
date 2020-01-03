package asynchronous.completablefuture.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

/**
 * <h1>CompletableFuture Callbacks and Chaining</h1> We know that
 * CompletableFuture.get() is blocking and we want to avoid this. We should get
 * some notification after Future completes. CompletableFuture provides
 * thenApply(), thenAccept() and thenRun() to attach callbacks
 * 
 * <br/>
 * <b>thenAccept()</b> <br/>
 * If We want to run some code after receiving some value from Future then we
 * can use thenAccept() <br/>
 * <b>thenApply()</b> <br/>
 * If We want to run some code after receiving value from Future and then want
 * to return some value for this we can use thenAccept() <br/>
 * <b>thenRun()</b> <br/>
 * If We want to run some code after completion of the Future and dont want to
 * return any value for this we can use thenRun()
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class CompletableFutureThenAccept {

	public AtomicInteger someStateVaribale = new AtomicInteger(1);

	public String process() {
		System.out.println(Thread.currentThread() + " Process Method");
		sleep(1);
		return "Some Value";
	}

	private void sleep(Integer i) {
		try {
			TimeUnit.SECONDS.sleep(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void notify(String str) {
		System.out.println(Thread.currentThread() + "Recived vlaue " + str);
		someStateVaribale.set(100);
		sleep(1);
	}

	@Test
	public void completableFutureThenAccept() {
		CompletableFuture.supplyAsync(this::process).thenAccept(this::notify).join();
		assertEquals(100, someStateVaribale.get());
	}

}
