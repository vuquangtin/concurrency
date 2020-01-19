package com.features.synchronizers.barriers;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Barriers are used for blocking a group of threads until they come together at
 * a single point in order to proceed. Basically, convergence of threads.
 * 
 * Accepts a runnable in it's constructor to be called when the threads reach
 * the barrier, but before its unblocked
 * 
 * Most common implementation is cyclic barrier.
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class UsingBarriers {

	public static void main(String[] args) {

		Runnable barrierAction = () -> System.out.println("Well done, guys!");

		ExecutorService executor = Executors.newCachedThreadPool();
		CyclicBarrier barrier = new CyclicBarrier(10, barrierAction);

		Runnable task = () -> {
			try {
				// simulating a task that can take at most 1sec to run
				System.out.println("Doing task for " + Thread.currentThread().getName());
				Thread.sleep(new Random().nextInt(10) * 100);
				System.out.println("Done for " + Thread.currentThread().getName());
				barrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		};

		for (int i = 0; i < 100; i++) {
			executor.execute(task);
		}
		executor.shutdown();

	}

}
