package com.features.synchronizers.latches;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 
 * Latches are used to delay the progress of threads until it reach a terminal
 * state
 *
 * Most common implementation is CountDownLatch.
 * 
 * In CountDownLatch, each event adds 1. When ready, countDown() is called,
 * decrementing by counter by 1. await() method releases when counter is 0.
 * 
 * Single use synchronizer.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class UsingLatches {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		CountDownLatch latch = new CountDownLatch(3);
		Runnable r = () -> {
			try {
				Thread.sleep(1000);
				System.out.println("Service in " + Thread.currentThread().getName() + " initialized.");
				latch.countDown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
		executor.execute(r);
		executor.execute(r);
		executor.execute(r);
		try {
			latch.await(2, TimeUnit.SECONDS);
			System.out.println("All services up and running!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();
	}

}
