/**
 * 
 */
package com.handson.concurrency;

import static java.lang.System.out;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample05 {

	/*
	 * synchronized example on static and instance methods and synchronized on
	 * different instances for same class.
	 */
	public static void main(String[] args) throws InterruptedException {

		SharedObject sharedObject = new SharedObject();
		CountDownLatch countDownLatch = new CountDownLatch(2);

		Callable<Void> sharedValueMutator = () -> {
			for (int i = 0; i <= 10; i++)
				sharedObject.addToExistingValue(i);
			countDownLatch.countDown();
			return null;
		};

		Callable<Void> sharedValueReaderOnSynchronizedStaticMethod = () -> {
			for (int i = 0; i <= 10; i++)
				out.println("SynchronizedMethodkSharedValueReader " + SharedObject.toString(sharedObject));
			countDownLatch.countDown();
			return null;

		};

		/*
		 * Below execution flow shows that SharedObject.addToExistingValue is locked at
		 * object level and SharedObject.toString is locked at class level(Locked on
		 * SharedObject.class Object)
		 */
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		executorService.submit(sharedValueMutator);
		executorService.submit(sharedValueReaderOnSynchronizedStaticMethod);
		executorService.shutdown();
		countDownLatch.await();
		System.out.println("Final Value of Shared Object is " + sharedObject.getSharedValue());
		System.out.println("Example 1 is over !!!");
		System.out.println("Example 2 starts !!!");
		/*
		 * Below example shows that threads running synchronized instance method of 2
		 * separate objects of same class will have different/own locks(Lock at object
		 * level)
		 */
		SharedObject sharedObject1 = new SharedObject();
		SharedObject sharedObject2 = new SharedObject();

		ExecutorService executorService1 = Executors.newFixedThreadPool(2);

		Callable<Void> sharedValueCallable1 = () -> {

			for (int i = 0; i <= 10; i++)
				sharedObject1.addToExistingValue(i);
			System.out.println("sharedValueCallable1 " + SharedObject.toString(sharedObject1));
			return null;
		};

		Callable<Void> sharedValueCallable2 = () -> {
			for (int i = 0; i <= 10; i++)
				sharedObject2.addToExistingValue(i);
			System.out.println("sharedValueCallable2 " + SharedObject.toString(sharedObject2));
			return null;
		};

		executorService1.submit(sharedValueCallable1);
		executorService1.submit(sharedValueCallable2);
		executorService1.shutdown();

	}

	private static class SharedObject {

		private int sharedValue;

		public int getSharedValue() throws InterruptedException {
			return sharedValue;
		}

		public synchronized void addToExistingValue(int valueToBeAdded) throws InterruptedException {
			Thread.sleep(1000);
			this.sharedValue += valueToBeAdded;
		}

		public static synchronized String toString(SharedObject sharedObject) throws InterruptedException {
			Thread.sleep(500);
			return String.format("Shared Value is %d", sharedObject.getSharedValue());
		}

	}

}
