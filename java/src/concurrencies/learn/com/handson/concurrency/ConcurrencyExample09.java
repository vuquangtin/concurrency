/**
 * 
 */
package com.handson.concurrency;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample09 {

	/*
	 * Example on avoiding Deadlocks,continuation to previous example. In order to
	 * fix previous example deadlock issue , we need to always acquire the locks in
	 * same order in all thread.In this example both Thread1 and THread2 should
	 * always acquire locks in same order ( sharedObject1 and sharedObject2) or
	 * (sharedObject2 and sharedObject1).
	 */

	/*
	 * There should not be any cycle's in the order in which locks are acquired
	 * between multiple thread. Example-> Consider locks A,B,C and Threads
	 * Thread1,Thread2 and Thread3 . Thread1 Acquires lock A and B , Thread2
	 * Acquires lock B and C . Thread3 should always acquire either lock A or lock B
	 * before acquiring lock C. Thread3 should never acquire lock C first , this
	 * might create a dead lock situation.
	 */

	public static void main(String[] args) {
		SharedObject1 sharedObject1 = new SharedObject1();
		SharedObject2 sharedObject2 = new SharedObject2();

		Thread thread1 = new Thread(() -> {
			try {
				synchronized (sharedObject1) {
					System.out.println("Entering Thread 1 outer synchronized block ");
					Thread.sleep(1000);
					synchronized (sharedObject2) {
						System.out.println("Entering Thread 1 inner synchronized block ");
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		Thread thread2 = new Thread(() -> {
			try {
				synchronized (sharedObject1) {
					System.out.println("Entering Thread 2 outer synchronized block ");
					Thread.sleep(1000);
					synchronized (sharedObject2) {
						System.out.println("Entering Thread 2 inner synchronized block ");
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		thread1.start();
		thread2.start();

	}

	private static class SharedObject1 {

	}

	private static class SharedObject2 {

	}

}
