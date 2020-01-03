package asynchronous.completablefuture.tests;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class CompletableFutureThenApply {
	
	public AtomicInteger someStateVaribale = new AtomicInteger(1);

	public String process() {
		System.out.println(Thread.currentThread() + " Process Method");
		sleep(1);
		return "Some Value";
	}

	private void sleep(Integer i)
	{
		try {
			TimeUnit.SECONDS.sleep(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	public Integer notify(String str) {
		System.out.println(Thread.currentThread() + "Recived vlaue " + str);		
		sleep(1);
		return 1;
	}

	
	@Test
	public void completableFutureThenApply() {
		 Integer notificationId = CompletableFuture.supplyAsync(this::process)
						 .thenApply(this::notify)
						 .join();		
		assertEquals(new Integer(1),notificationId);	
	}

	

}

