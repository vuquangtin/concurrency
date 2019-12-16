/**
 * 
 */
package com.handson.concurrency.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample08 {

	/*
	 * Example on CountDownLatch. CountDownLatch can be used to block a thread until
	 * count down (will be reduced by other threads) has reached to
	 * zero.CountDownLatch also has other variants such as await with timeout.
	 * 
	 */
	private static final int THREAD_COUNT = 5;

	public static void main(String[] args) throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);
		ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
		for (int threadIndex = 1; threadIndex <= THREAD_COUNT; threadIndex++)
			executorService.submit(createRunnableThreadWithCountDown(countDownLatch, threadIndex));
		countDownLatch.await();
		System.out.println("After Count Down Wait in Main Thread");
		executorService.shutdown();

	}

	private static Callable<Void> createRunnableThreadWithCountDown(CountDownLatch countDownLatch, int threadIndex) {
		return () -> {
			Thread.sleep(1000);
			System.out.println("Before Count Down Latch count down in Thread with index " + threadIndex);
			countDownLatch.countDown();
			return null;
		};
	}

}
