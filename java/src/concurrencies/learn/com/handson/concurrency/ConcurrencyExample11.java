/**
 * 
 */
package com.handson.concurrency;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample11 {

	/*
	 * Thread class all methods examples.
	 */

	public static void main(String[] args) throws InterruptedException {

		Thread thread = new Thread(() -> {
			for (long i = 1; i < 100000000l; i++)
				;
		});
		thread.start();
		/* Get class loader */
		ClassLoader classLoader = thread.getContextClassLoader();
		/*
		 * StackTraceElement[] stackTraceElements = thread.getStackTrace();
		 * Stream<StackTraceElement> streamStackTraceElements =
		 * Stream.of(stackTraceElements);
		 * streamStackTraceElements.forEach(streamStackTraceElement -> {
		 * System.out.println(streamStackTraceElement); });
		 */
		ThreadGroup threadGroup = thread.getThreadGroup();
		System.out.println(threadGroup);
		System.out.println(thread.isDaemon());
		System.out.println(thread.isAlive());
		System.out.println(thread.isInterrupted());
		System.out.println(thread.getState());
		System.out.println(thread.activeCount());
		thread.join();

	}

}
