package asynchronous.completablefuture.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

/**
 * <h1>CompletableFuture</h1> CompletableFuture is an implementation of the
 * Future & CompletionStage interface but with a lot of modern Features. It
 * Supports lambdas and takes advantage of non-blocking methods via callbacks
 * and promotes asynchronous reactive programming model. CompletableFuture
 * allows us to write non-blocking code by running a task on a separate thread
 * than the main application thread and notifying the main thread about its
 * Progress, Completion or Failure. CompletableFuture is inspired from
 * ListenableFuture in Guava and Are similar to Promise in java scripts.
 * 
 * <h2>Why CompletableFuture instead Of Future?</h2> Callable and Future were
 * introduced in Java 5. Future is placeholders for a result that hasn’t
 * happened yet.Future Can use a Runnable or Callable instance to complete the
 * submitted task. There are two methods to get actual value from Future. get()
 * : When this method is called, thread will wait for result indefinitely.
 * get(long timeout, TimeUnit unit): When this method is called, thread will
 * wait for result only for specified time. <br/>
 * There are multiple problems with Future <br/>
 * Blocking - The get method is blocking and need to wait until the computation
 * is done. Future does not have any method that can notify on completion and
 * does not have capability to attach a callback function. Chaining &
 * Composition - Many times we want to chain multiple future to complete long
 * computation. You need to merger results and send results to another task.
 * It’s Hard to implement such chaining with future. Exception Handling - Future
 * does not provide any construct for Exception Handling. All these issues are
 * addressed by CompletableFuture. lets try different methods provided by
 * CompletableFuture
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class SimpleCompletableFutureTest {

	public AtomicInteger someStateVaribale = new AtomicInteger(1);

	/***
	 * <h1>Create Simple Completeable Future</h1>
	 * 
	 * The simplest way is to create CompleteableFuture is
	 * CompleteableFuture.completedFuture method which returns an a new,
	 * finished CompleteableFuture. Creating already Completed
	 * CompleteableFuture becomes very useful in many cases. <i>Note : if we
	 * call get method on incomplete CompleteableFuture , the get call will
	 * block forever because the Future is never completed.We can use
	 * CompletableFuture.complete() method to manually complete a Future.</i>
	 */
	@Test
	public void simpleComletedCompletableFuture() {
		CompletableFuture<String> completableFuture = CompletableFuture.completedFuture("Some Value");
		assertTrue(completableFuture.isDone());
		try {
			assertEquals("Some Value", completableFuture.get());
		} catch (ExecutionException | InterruptedException e) {
			fail("No Exception expected");
		}
	}

	@Test
	public void simpleCompletableFuture() {
		CompletableFuture<String> completableFuture = new CompletableFuture<>();
		completableFuture.complete("Some Value");
		assertTrue(completableFuture.isDone());
		try {
			assertEquals("Some Value", completableFuture.get());
		} catch (ExecutionException | InterruptedException e) {
			fail("No Exception expected");
		}
	}

	public String processSomeData() {
		System.out.println(Thread.currentThread() + " Processing Some Data");
		return "Some Value";
	}

	/***
	 * <h1>Simple Asynchronous computation using SupplyAsync</h1> If we want to
	 * run some task in background that Returns Some Value, then we can use
	 * CompletableFuture.supplyAsync() it takes a Supplier<T> and returns
	 * completableFuture<T>
	 */
	@Test
	public void completableFutureSupplyAsync() {
		CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(this::processSomeData);
		try {
			assertEquals("Some Value", supplyAsync.get());
		} catch (ExecutionException | InterruptedException e) {
			fail("No Exception expected");
		}

	}

	/***
	 * <h1>CompletableFuture with Custom Executor</h1> You might be wondering,
	 * Which Thread is executing the supplyAsync & runAsync task and Who is
	 * creating these Threads? Similar to parallel streams CompletableFuture
	 * executes these tasks in a thread obtained from the global
	 * ForkJoinPool.commonPool(). <b>We Can always provide our custom Executor to
	 * </b>.All the methods in the CompletableFuture API has two
	 * variants, With or Without Executor.
	 */
	@Test
	public void completableFutureSupplyAsyncWithExecuto() {
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2);
		CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(this::processSomeData,
				newFixedThreadPool);
		try {
			assertEquals("Some Value", supplyAsync.get());
		} catch (ExecutionException | InterruptedException e) {
			fail("No Exception expected");
		}
	}

	public void process() {
		System.out.println(Thread.currentThread() + " Process");
		someStateVaribale.set(100);

	}

	/***
	 * <h1>Simple Asynchronous computation using runAsync</h1> If We want to run
	 * some task in background that does not returns any value, then we can use
	 * CompletableFuture.runAsync() it takes a Runnable and returns
	 * CompletableFuture<Void>
	 */
	@Test
	public void completableFutureRunAsync() {
		CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> process());
		runAsync.join();
		assertEquals(100, someStateVaribale.get());

	}
}