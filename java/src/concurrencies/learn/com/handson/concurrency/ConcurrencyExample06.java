/**
 * 
 */
package com.handson.concurrency;

import static java.lang.System.out;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample06 {

	/*
	 * Wait and notify example.For Single producer and single consumer, it's enough
	 * to use wait and notify. To check alternate behavior on this example,
	 * uncomment all commented code and observe the behavior.
	 */
	public static void main(String[] args) throws InterruptedException {

		Thread.setDefaultUncaughtExceptionHandler((Thread th, Throwable t) -> out.println(
				"Exception thrown in thread " + th.getName() + " and reason for exception is - " + t.getMessage()));

		ProducerConsumerSharedObject sharedObject = new ProducerConsumerSharedObject();
		Thread producerThread = new Thread(() -> {
			try {
				for (int i = 0; i <= 10; i++) {
					sharedObject.setSharedValue(String.valueOf(i));
					/* sharedObject.setSharedValue(String.valueOf(i)); */
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
		producerThread.start();
		Thread consumerThread = new Thread(() -> {
			try {
				for (int i = 0; i <= 10; i++)
					System.out.println("Shared Object value is " + sharedObject.getSharedValue());
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
		consumerThread.start();

		Thread.sleep(3000);
		/* Below Code if for interrupting ProducerThread in case blocked */
		/* producerThread.interrupt(); */

	}

	private static class ProducerConsumerSharedObject {

		private String sharedValue;

		public synchronized String getSharedValue() throws InterruptedException {
			while (sharedValue == null)
				wait();
			String valuetoBeRetuned = sharedValue;
			sharedValue = null;
			notify();
			return valuetoBeRetuned;
		}

		public synchronized void setSharedValue(String sharedValue) throws InterruptedException {
			while (this.sharedValue != null)
				wait();
			this.sharedValue = sharedValue;
			notify();
		}

		@Override
		public String toString() {
			return "SharedObject [sharedValue=" + sharedValue + "]";
		}

	}

}
