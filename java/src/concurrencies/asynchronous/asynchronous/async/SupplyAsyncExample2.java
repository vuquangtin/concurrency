package asynchronous.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class SupplyAsyncExample2 {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		CompletableFuture<String> cf = CompletableFuture.supplyAsync(
				()-> getDataById(10), 
				executorService
			)
			.thenApply(data -> sendData(data));
		
		cf.get();
		executorService.shutdown();
	}
	private static String getDataById(int id) {
		System.out.println("getDataById: "+ Thread.currentThread().getName());
		return "Data:"+ id;
	}
	private static String sendData(String data) {
		System.out.println("sendData: "+ Thread.currentThread().getName());
		System.out.println(data);
		return data;
	}	
} 