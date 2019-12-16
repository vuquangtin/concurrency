/**
 * 
 */
package com.handson.concurrency;

import static java.lang.System.out;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample10 {

	/*
	 * Thread interruption example. Thread.interrupt is an alternate mechanism to
	 * Thread.stop method, Thread.stop is deprecated method due to stopping methods
	 * abnormally will not release the acquired locks .Thread.interrupt() method can
	 * be used to interrupt a thread which is in sleep or wait
	 * state.Thread.interrupt can be used in heavy cpu consumption tasks , this is
	 * done using Thread.interrepted() method which check's interrupt flag , returns
	 * boolean and reset the flag. Following example shows how interruption
	 * mechanism works in java.
	 */

	public static void main(String[] args) throws InterruptedException {

		/* Global Thread exception handling */
		Thread.setDefaultUncaughtExceptionHandler((Thread th, Throwable t) -> out.println(
				"Exception thrown in thread " + th.getName() + " and reason for exception is - " + t.getMessage()));

		SharedObject sharedObject = new SharedObject();

		Thread thread0 = new Thread(() -> {
			try {
				out.println("Entering Thread 0 before sleep ");
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				out.println("Caught InterruptedException in thread 0 ");
				throw new RuntimeException(e);
			}
		});

		Thread thread1 = new Thread(() -> {
			try {
				synchronized (sharedObject) {
					out.println("Entering Thread 1 synchronized block ");
					sharedObject.wait();
				}
			} catch (InterruptedException e) {
				out.println("Caught InterruptedException in thread 1 ");
				throw new RuntimeException(e);
			}
		});

		Thread thread2 = new Thread(() -> {

			@SuppressWarnings("unused")
			long result;
			for (long l = 1; l < Long.MAX_VALUE; l++) {
				if (Thread.interrupted())
					throw new RuntimeException("Thread was interrupted after iteration " + l);
				for (long j = 1; j < 10000000l; j++)
					result = l * j;
			}
		});
		thread0.start();
		thread1.start();
		thread2.start();
		Thread.sleep(1000);
		thread0.interrupt();
		thread1.interrupt();
		thread2.interrupt();

	}

	private static class SharedObject {

	}

}
