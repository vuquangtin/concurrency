package executors.executor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/***
 * Guide to Java 8 Concurrency Using Executors <br/>
 * -------------<br/>
 * Here's an overview of the improvements Java 8 brought to concurrent
 * programming with a focus on ExecutorService.<br/>
 * ----------------------<br/>
 * 
 * @author vuquangtin
 *
 */
public class UsingExecutors {

	public static void main(String[] args) {
		/***
		 * ExecutorService The Concurrency API introduces the concept of an
		 * ExecutorService as a higher-level replacement for working with
		 * threads directly.
		 * 
		 * Executors are capable of managing a pool of threads, so we do not
		 * have to manually create new threads and run tasks in an asynchronous
		 * fashion.
		 * 
		 * Have a look at a simple Java ExecutorService:
		 */
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(() -> {
			String threadName = Thread.currentThread().getName();
			System.out.println("Hello " + threadName);
		});
		/***
		 * ExecutorService Implementation Since ExecutorService is an interface,
		 * it has to be implemented in order to make any use of it. The
		 * ExecutorService has the following implementations in the
		 * java.util.concurrent package:
		 * 
		 * ThreadPoolExecutor ScheduledThreadPoolExecutor Executors' factory
		 * class can also be used to create executor instances.
		 * 
		 * For example:
		 */
		ExecutorService executorService1 = Executors.newSingleThreadExecutor();
		ExecutorService executorService2 = Executors.newFixedThreadPool(5);
		/***
		 * Delegating Tasks to ExecutorService Below are few of the different
		 * ways that can be used to delegate tasks for execution to an
		 * ExecutorService:
		 * 
		 * execute(Runnable command) submit(Callable task) submit(Runnable task)
		 * invokeAny(Collection<? extends Callable<T>> tasks)
		 * invokeAll(Collection<? extends Callable<T>> tasks) execute(Runnable
		 * Command) This method takes a java.lang.Runnable object and executes
		 * it asynchronously.
		 */
		ExecutorService executor1 = Executors.newSingleThreadExecutor();
		executor1.execute(() -> {
			String threadName = Thread.currentThread().getName();
			System.out.println("Hello " + threadName);
		});
		/**
		 * submit(Callable Task) and submit(Runnable Task) The submit(Runnable
		 * task) method takes a Runnable implementation and returns a Future
		 * object, which can be used to check if the Runnable as finished
		 * executing.
		 */
		Runnable task = () -> {
			System.out.println("runnable task");
		};

		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Future<?> future = executorService.submit(task);
		try {
			System.out.println("value - " + future.get());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // returns null if the
			// task has finished
			// successfully
		/**
		 * Callables are functional interfaces, but unlike Runnable, they return
		 * a value. A submit(callable task) method takes a Callable
		 * implementation
		 */
		Callable<String> task2 = () -> "task 1 ";
		ExecutorService executorService3 = Executors.newSingleThreadExecutor();
		Future future2 = executorService3.submit(task2);
		try {
			System.out.println("value - " + future2.get());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // returns task1
		/**
		 * invokeAll(Collection<? extends Callable<T>> tasks) This method
		 * supports batch submitting of multiple callables at once. It accepts a
		 * collection of callables and returns a list of futures.
		 */
		ExecutorService executor4 = Executors.newFixedThreadPool(1);

		List<Callable<String>> callables = Arrays
				.asList(() -> "t1", () -> "t2");

		try {
			executor4.invokeAll(callables).stream().map(future4 -> {
				try {
					return future4.get();
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
			}).forEach(System.out::println);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/**
		 * invokeAny(Collection<? extends Callable<T>> Tasks) This method works
		 * slightly differently than invokeAll(). Instead of returning future
		 * objects, it blocks until the first callable terminates and returns
		 * the result of that callable.
		 */
		ExecutorService executor6 = Executors.newWorkStealingPool();

		List<Callable<String>> callables2 = Arrays.asList(
				() -> "task 1 completed", () -> "task 2 completed");
		try {
			executor6.invokeAll(callables2).forEach((future6) -> {
				try {
					System.out.println(future6.get());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}
}
