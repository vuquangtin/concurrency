/**
 * 
 */
package com.handson.concurrency;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample12 {

	/*
	 * Thread group example.
	 */

	public static void main(String[] args) throws InterruptedException {

		ThreadGroup threadGroup = new ThreadGroup("localThreadGroup");
		threadGroup.setMaxPriority(Thread.MAX_PRIORITY);
		Thread thread1 = new Thread(threadGroup, () -> {
			for (long i = 1; i < 100000000l; i++)
				;
		});
		Thread thread2 = new Thread(threadGroup, () -> {
			for (long i = 1; i < 100000000l; i++)
				;
		});
		thread1.start();
		thread2.start();
		System.out.println(threadGroup.activeGroupCount());
		threadGroup.list();

	}

}
