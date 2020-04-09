package executors.customthreadpoolexecutor.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class Java7_0355_ExecutorConcurrentInvokeAllMain {
	public void runApp() throws InterruptedException, ExecutionException {
		// variable to store the sum
		AtomicInteger sum = new AtomicInteger();
		// Use our friendly neighbourhood factory method of the Executors.
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		List<Callable<AtomicInteger>> callableList = new ArrayList<Callable<AtomicInteger>>();
		for (int count = 0; count <= 100; count++) {
			callableList.add(getInstanceOfCallable(count, sum));
		}
		// returns only after all tasks are complete
		List<Future<AtomicInteger>> resultFuture = executorService.invokeAll(callableList);
		// Prints 5050 all through
		for (Future<AtomicInteger> future : resultFuture) {
			// Didn't deliberately put a timeout here for the get method.
			// Remember, the invoke All does not return until the task is done.
			System.out.println("Status of future : " + future.isDone() + ". Result of future : " + future.get().get());
		}
		executorService.shutdown();
		// You might as well call a resultFuture.get(0).get().get() and that would give you the same
		// result since all your worker threads hold reference to the same atomicinteger sum.
		System.out.println("Final Sum : " + sum);
	}

	// Adds count to the sum and returns the reference of the sum as the result
	private Callable<AtomicInteger> getInstanceOfCallable(final int count, final AtomicInteger sum) {
		Callable<AtomicInteger> clientPlanCall = new Callable<AtomicInteger>() {
			public AtomicInteger call() {
				sum.addAndGet(count);
				System.out.println("Intermediate sum :" + sum);
				return sum;
			}
		};
		return clientPlanCall;
	}

	public static void main(String[] args) throws ExecutionException {
		try {
			new Java7_0355_ExecutorConcurrentInvokeAllMain().runApp();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
