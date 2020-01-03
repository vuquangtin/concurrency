package com.rxjava3.complerable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class CompletableFutureTest {
	public static void main(String[] args) {
		main0();
	}

	public static void main0() {
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		CompletionService<Integer> executorCompletionService = new ExecutorCompletionService<>(
				executorService);
		executorCompletionService.submit(() -> {
			try {
				Thread.sleep(1500);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 100;
		});
		executorCompletionService.submit(() -> {
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 100;
		});
		Thread thread = new Thread(
				() -> {
					try {
						System.out.println(Thread.currentThread().getName()
								+ ":start");
						executorCompletionService.take().get();
						System.out.println(Thread.currentThread().getName()
								+ ":end");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});

		Thread thread2 = new Thread(
				() -> {
					try {
						System.out.println(Thread.currentThread().getName()
								+ ":start");
						executorCompletionService.take().get();
						System.out.println(Thread.currentThread().getName()
								+ ":end");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});

		thread.start();
		thread2.start();
		// completableFuture.complete(100);
		executorService.shutdown();
	}

	@Test
	public void completedFutureExample() {
		CompletableFuture<String> cf = CompletableFuture
				.completedFuture("message");
		assertTrue(cf.isDone());
		assertEquals("message", cf.getNow(null));
	}

}