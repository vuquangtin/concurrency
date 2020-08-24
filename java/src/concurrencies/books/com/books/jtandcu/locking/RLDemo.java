package com.books.jtandcu.locking;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class RLDemo {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		final ReentrantLock lock = new ReentrantLock();
		class Worker implements Runnable {
			private final String name;

			Worker(String name) {
				this.name = name;
			}

			@Override
			public void run() {
				lock.lock();
				try {
					if (lock.isHeldByCurrentThread())
						System.out.printf(
								"Thread %s entered critical section.%n", name);
					System.out.printf("Thread %s performing work.%n", name);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException ie) {
						ie.printStackTrace();
					}
					System.out.printf("Thread %s finished working.%n", name);
				} finally {
					lock.unlock();
				}
			}
		}
		executor.execute(new Worker("ThdA"));
		executor.execute(new Worker("ThdB"));
		executor.execute(new Worker("ThdC"));
		executor.execute(new Worker("ThdD"));
		try {
			executor.awaitTermination(15, TimeUnit.SECONDS);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		executor.shutdownNow();
	}
}