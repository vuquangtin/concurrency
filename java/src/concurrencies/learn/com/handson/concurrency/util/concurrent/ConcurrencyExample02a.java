/**
 * 
 */
package com.handson.concurrency.util.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample02a {

	/*
	 * Example on ExecuterService.ExecuterService.invokeAll() and
	 * ExecuterService.invokeAny().
	 * 
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		List<Callable<Long>> callableTasks = new ArrayList<>();
		callableTasks.add(() -> {
			long count = 0;
			for (int i = 0; i < 10; i++) {
				count += i;
				Thread.sleep(200);
			}

			return count;
		});
		callableTasks.add(() -> {
			long count = 0;
			for (int i = 0; i < 20; i++) {
				count += i;
				Thread.sleep(200);
			}
			return count;
		});

		List<Future<Long>> results = executorService.invokeAll(callableTasks);
		results.forEach((Future<Long> future) -> {
			try {
				System.out.println("Future task " + future.toString() + " has result " + future.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		});

		Long fastestResult = executorService.invokeAny(callableTasks);
		System.out.println("Fastest executed result using invokeAny " + fastestResult);

		executorService.shutdown();
	}

}
