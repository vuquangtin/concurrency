/**
 * 
 */
package com.handson.concurrency;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample08a {

	/*
	 * Example on Deadlock.This is a sample example on deadlock using Threads
	 * acquiring locks in reverse order using nested synchronized blocks. Only way
	 * to stop this program is by killing it.In this example to simulate deadlock
	 * SharedObjects Code has been written in that way.
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
				synchronized (sharedObject2) {
					System.out.println("Entering Thread 2 outer synchronized block ");
					Thread.sleep(1000);
					synchronized (sharedObject1) {
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
