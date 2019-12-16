/**
 * 
 */
package com.handson.concurrency;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample08 {

	/*
	 * Example on Deadlock.This is a sample example on deadlock using Threads
	 * acquiring locks in reverse order using synchronized methods. Only way to stop
	 * this program is by killing it.In this example to simulate deadlock
	 * SharedObjects Code has been written in that way.
	 */
	public static void main(String[] args) {
		SharedObject1 sharedObject1 = new SharedObject1();
		SharedObject2 sharedObject2 = new SharedObject2();
		sharedObject1.setSharedObject2(sharedObject2);
		sharedObject2.setSharedObject1(sharedObject1);

		Thread thread1 = new Thread(() -> {
			try {
				sharedObject1.getSharedValueOfObject2();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		Thread thread2 = new Thread(() -> {
			try {
				sharedObject2.getSharedValueOfObject1();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		thread1.start();
		thread2.start();

	}

	private static class SharedObject1 {

		private final String sharedValue = "SharedObject1Value";
		private SharedObject2 sharedObject2;

		public void setSharedObject2(SharedObject2 sharedObject2) {
			this.sharedObject2 = sharedObject2;
		}

		public synchronized String getSharedValue() {
			return sharedValue;
		}

		public synchronized String getSharedValueOfObject2() throws InterruptedException {
			System.out.println("Entering getSharedValueOfObject2 method");
			Thread.sleep(1000);
			return sharedObject2.getSharedValue();
		}

	}

	private static class SharedObject2 {

		private final String sharedValue = "SharedObject2Value";
		private SharedObject1 sharedObject1;

		public void setSharedObject1(SharedObject1 sharedObject1) {
			this.sharedObject1 = sharedObject1;
		}

		public synchronized String getSharedValueOfObject1() throws InterruptedException {
			System.out.println("Entering getSharedValueOfObject1 method");
			Thread.sleep(1000);
			return sharedObject1.getSharedValue();
		}

		public synchronized String getSharedValue() {
			return sharedValue;
		}

	}

}
