package asynchronous.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class SupplyAsyncExample3 {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		CompletableFuture<String> cf = CompletableFuture.supplyAsync(()-> getDataById(10))
			.whenComplete((data, error) -> {
				consumeData(data);
				if(error!= null) {
					System.out.println(error);
				}
			 });
		cf.get();
	}
	private static String getDataById(int id) {
		System.out.println("getDataById: "+ Thread.currentThread().getName());
		return "Data:"+ id;
	}
	private static void consumeData(String data) {
		System.out.println("consumeData: "+ Thread.currentThread().getName());
		System.out.println(data);
	}	
} 