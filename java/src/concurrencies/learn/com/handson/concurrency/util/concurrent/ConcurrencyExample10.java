/**
 * 
 */
package com.handson.concurrency.util.concurrent;

import static java.lang.System.out;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample10 {

	/*
	 * Example on Semaphore.Semaphore is inverse to CyclicBarrier, It allows only
	 * max configured Threads.So it is thread max limit construct. BinarySempahore
	 * can be used as lock i.e Semaphore with count 1.Semaphore also supports Thread
	 * fairness property flag as constructor argument.
	 */

	private static final int SEMAPHORE_ALLOW_THREAD_COUNT = 5;

	private static final int MAX_THREAD_COUNT = 5 * SEMAPHORE_ALLOW_THREAD_COUNT;

	public static void main(String[] args) {
		Semaphore semaphore = new Semaphore(SEMAPHORE_ALLOW_THREAD_COUNT, true);
		ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD_COUNT);
		for (int threadIndex = 1; threadIndex <= MAX_THREAD_COUNT; threadIndex++)
			executorService.submit(createRunnableThreadWithSemaphore(semaphore, threadIndex));
		executorService.shutdown();

	}

	private static Callable<Void> createRunnableThreadWithSemaphore(Semaphore semaphore, int threadIndex) {
		return () -> {
			semaphore.acquire();
			out.println("After Acquiring Semaphore in Thread with index " + threadIndex + " @ " + new Date());
			Thread.sleep(5000);
			semaphore.release();
			out.println("After Releasing Semaphore in Thread with index " + threadIndex + " @ " + new Date());

			return null;
		};
	}

}
