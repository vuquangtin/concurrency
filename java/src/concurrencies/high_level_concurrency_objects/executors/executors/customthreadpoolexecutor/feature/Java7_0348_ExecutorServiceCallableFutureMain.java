package executors.customthreadpoolexecutor.feature;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Java7_0348_ExecutorServiceCallableFutureMain {

	public static void main(String... args) throws InterruptedException, ExecutionException {
		new Java7_0348_ExecutorServiceCallableFutureMain().executorServiceFutureTask();
	}
	
	// use FutureTask for asynchronous processing
	public void executorServiceFutureTask() throws InterruptedException, ExecutionException {
		// creating thread pool to execute task which implements Callable
		ExecutorService es = Executors.newSingleThreadExecutor();

		System.out.println("submitted callable task to calculate factorial of 10");
		Future<Long> result10 = es.submit(new Java7_0347_FactorialCalculatorConcurrentThread(10));

		System.out.println("submitted callable task to calculate factorial of 15");
		Future<Long> result15 = es.submit(new Java7_0347_FactorialCalculatorConcurrentThread(15));

		System.out.println("submitted callable task to calculate factorial of 20");
		Future<Long> result20 = es.submit(new Java7_0347_FactorialCalculatorConcurrentThread(20));

		System.out.println("Calling get method of Future to fetch result of factorial 10");
		long factorialof10 = (long) result10.get();
		System.out.println("factorial of 10 is : " + factorialof10);

		System.out.println("Calling get method of Future to get result of factorial 15");
		long factorialof15 = (long) result15.get();
		System.out.println("factorial of 15 is : " + factorialof15);

		System.out.println("Calling get method of Future to get result of factorial 20");
		long factorialof20 = (long) result20.get();
		System.out.println("factorial of 20 is : " + factorialof20);
	}

}
