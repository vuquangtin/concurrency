/**
 * 
 */
package com.handson.concurrency.util.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample02 {

	/*
	 * Example on ExecuterService.
	 * 
	 */
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(2);

		Future<Long> futureResult1 = executorService.submit(() -> {
			long count = 0;
			for (int i = 0; i < 100; i++) {
				count += i;
				Thread.sleep(2);
			}
			return count;
		});
		Future<Long> futureResult2 = executorService.submit(() -> {
			long count = 0;
			for (int i = 0; i < 1000000; i++) {
				count += i;
				if (count > 9000000)
					throw new RuntimeException();
			}
			return count;
		});

		new Thread(() -> {
			while (true)
				try {
					Long result = futureResult1.get(200, TimeUnit.MILLISECONDS);
					System.out.println("Executor task 1 result is " + result);
					return;
				} catch (InterruptedException | ExecutionException | TimeoutException e) {
					e.printStackTrace();
				}
		}).start();

		new Thread(() -> {
			while (true)
				try {
					Long result = futureResult2.get(2, TimeUnit.NANOSECONDS);
					System.out.println("Executor task 2 result is " + result);
				} catch (InterruptedException | TimeoutException e) {
					e.printStackTrace();
				} catch (ExecutionException e2) {
					System.err.println("Exception while executing task 2");
					e2.printStackTrace();
					for (int i = 0; i < 5; i++)
						System.out.println();
					return;
				}
		}).start();

		executorService.shutdown();
	}

}
