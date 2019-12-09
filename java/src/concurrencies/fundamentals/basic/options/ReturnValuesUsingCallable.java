package basic.options;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
 * Threads that returns values are better implemented in Java using Callable<V> and Future<V>
 * Use the Executors Framework to run a Callable task
 */

public class ReturnValuesUsingCallable {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		System.out.println("Thread main started");
		
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		List<Future<Integer>> returnedValues = executorService.invokeAll(Arrays.asList(
				new ReturnValuesSumFirstNumbers(50), 
				new ReturnValuesSumFirstNumbers(40),
				new ReturnValuesSumFirstNumbers(30),
				new ReturnValuesSumFirstNumbers(20),
				new ReturnValuesSumFirstNumbers(10)));
		
		for (Future<Integer> value : returnedValues) {
			System.out.println(value.get());
		}
		
		executorService.shutdown();
		
		System.out.println("Thread main finished");
	}
}

class ReturnValuesSumFirstNumbers implements Callable<Integer> {
	private int n;
	
	public ReturnValuesSumFirstNumbers(int n) {
		this.n = n;
	}
	
	public Integer call() {
		int sum = 0;
		for (int i = 1; i <= n; i++) {
			System.out.println("[" + Thread.currentThread().getName() + "] Adding " + i);
			sum += i;
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return sum;
	}
}