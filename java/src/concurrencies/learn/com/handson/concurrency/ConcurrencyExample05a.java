/**
 * 
 */
package com.handson.concurrency;

import static java.lang.System.out;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample05a {

	/*
	 * synchronized example on throw exception while object is locked. If an
	 * exception is thrown in synchronized method with object locked , Thread
	 * holding the lock will release it.
	 */
	public static void main(String[] args) throws InterruptedException {
		Thread.setDefaultUncaughtExceptionHandler((Thread th, Throwable t) -> out.println(
				"Exception thrown in thread " + th.getName() + " and reason for exception is - " + t.getMessage()));

		SharedObject sharedObject = new SharedObject();
		Thread mutatorThread = new Thread(() -> {
			for (int i = 0; i <= 10; i++)
				sharedObject.addToExistingValue(i);
		});
		mutatorThread.start();
		Thread.sleep(2000);
		Thread readerThread = new Thread(
				() -> System.out.println("Shared Object value is " + sharedObject.getSharedValue()));
		readerThread.start();
	}

	private static class SharedObject {

		private int sharedValue;

		public synchronized int getSharedValue() {
			return sharedValue;
		}

		public synchronized void addToExistingValue(int valueToBeAdded) {
			if (this.sharedValue > 10)
				throw new RuntimeException("Value breached the threshold");
			this.sharedValue += valueToBeAdded;
		}

		@Override
		public String toString() {
			return "SharedObject [sharedValue=" + sharedValue + "]";
		}

	}

}
