/**
 * 
 */
package com.handson.concurrency.util.concurrent;

import static java.lang.System.out;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample09 {

	/*
	 * Example on CyclicBarrier.CyclicBarrier is a synchronization construct which
	 * blocks multiple threads until barrier count has breached.
	 */

	private static final int THREAD_COUNT = 5;

	public static void main(String[] args) {
		CyclicBarrier cyclicBarrier = new CyclicBarrier(THREAD_COUNT,
				() -> out.println("Inside Cyclic Barrier callback after the barrier count breach."));
		ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
		for (int threadIndex = 1; threadIndex <= THREAD_COUNT; threadIndex++)
			executorService.submit(createRunnableThreadWithCyclicBarrier(cyclicBarrier, threadIndex));
		out.println("After Thread's creation in Main Thread @ " + new Date());
		out.println();
		out.println();
		executorService.shutdown();
	}

	private static Callable<Void> createRunnableThreadWithCyclicBarrier(CyclicBarrier cyclicBarrier, int threadIndex) {
		return () -> {
			Thread.sleep(5000);
			out.println("Before CyclicBarrier await in Thread with index " + threadIndex + " @ " + new Date());
			cyclicBarrier.await();
			out.println("After CyclicBarrier await in Thread with index " + threadIndex + " @ " + new Date());
			return null;
		};
	}

}
