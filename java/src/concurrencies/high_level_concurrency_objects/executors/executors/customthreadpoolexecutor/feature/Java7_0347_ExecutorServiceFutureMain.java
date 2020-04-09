package executors.customthreadpoolexecutor.feature;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//Future allows to write asynchronous code in Java, where Future promises result to be available in future
public class Java7_0347_ExecutorServiceFutureMain {

	public static void main(String args[]) throws InterruptedException, ExecutionException {
		new Java7_0347_ExecutorServiceFutureMain().executorServiceFuture();
	}
	
	public void executorServiceFuture() throws InterruptedException, ExecutionException {
		final ExecutorService executorServiceNewFixedThreadPool = Executors.newFixedThreadPool(3);
		Future<Long> future = null;
		
		System.out.println("Submitting Task ...");
		future = executorServiceNewFixedThreadPool.submit(new Java7_0347_FactorialCalculatorConcurrentThread(10));
		System.out.println("Task is submitted");

		while (!future.isDone()) {
			System.out.println("Task is not completed yet....");
			Thread.sleep(1); // sleep for 1 millisecond before checking again
		}

		System.out.println("Task is completed, let's check result");
		long factorial = (long) future.get();
		System.out.println("Factorial of 1000000 is : " + factorial);

		executorServiceNewFixedThreadPool.shutdown();
	}

}
