/**
 * 
 */
package com.handson.concurrency.util.concurrent;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample01 {

	/*
	 * Example on ThreadLocal.Threadlocal will always keep shared variable at Thread
	 * Level.
	 */
	public static void main(String[] args) {

		ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 1);
		Thread thread1 = new Thread(() -> {
			try {
				for (int i = 0; i < 100; i++) {
					Thread.sleep(10);
					threadLocal.set(i);
					System.out.println("ThreadLocal value in thread 1 is " + threadLocal.get());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		Thread thread2 = new Thread(() -> {
			try {
				for (int i = 200; i < 300; i++) {
					Thread.sleep(10);
					threadLocal.set(i);
					System.out.println("ThreadLocal value in thread 2 is " + threadLocal.get());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		thread1.start();
		thread2.start();

	}

}
