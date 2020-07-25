package executors.customthreadpoolexecutor.feature;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class Java7_0355_ExecutorConcurrentSubmitMain {
	public void runApp() throws InterruptedException, ExecutionException {
		// holder for the total sum
		AtomicInteger sum = new AtomicInteger();
		// Use the factory method of Executors
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		Future<AtomicInteger> future = null;
		for (int count = 0; count <= 100; count++) {
			future = executorService.submit(getInstanceOfCallable(count, sum));
			// prints intermediate sum
			try {
				System.out.println("Status of future : " + future.isDone() + ". Result of future : "
						+ future.get(1000, TimeUnit.MILLISECONDS).get());
			} catch (TimeoutException e) {
				System.out.println("<IGNORE> Timeout exception for count : " + count);
				// e.printStackTrace();
			}
			// System.out.println("Result of future : "+future.get().get()
			// +".Status of future : " + future.isDone());
		}

		executorService.shutdown();
		if (executorService.awaitTermination(10, TimeUnit.SECONDS)) {
			System.out.println("All threads done with their jobs");
		}
		// exec
		System.out.println("Final Sum : " + sum);
	}

	// Adds count to the sum and returns the reference of the sum as the result
	private Callable<AtomicInteger> getInstanceOfCallable(final int count, final AtomicInteger sum) {
		Callable<AtomicInteger> clientPlanCall = new Callable<AtomicInteger>() {
			public AtomicInteger call() {
				sum.addAndGet(count);
				// System.out.println("Intermediate sum :"+sum);
				return sum;
			}
		};
		return clientPlanCall;
	}

	public static void main(String[] args) throws ExecutionException {
		try {
			new Java7_0355_ExecutorConcurrentSubmitMain().runApp();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
