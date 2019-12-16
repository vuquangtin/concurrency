/**
 * 
 */
package com.handson.concurrency;

import static java.lang.System.out;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample04 {

	/*
	 * Simple synchronized block and synchronized method example.Try executing this
	 * example multiple times and observe behavior.
	 */
	public static void main(String[] args) {

		SharedObjectSynchronizedBlock sharedObjectSynchronizedBlock = new SharedObjectSynchronizedBlock();
		SharedObjectSynchronizedMethod sharedObjectSynchronizedMethod = new SharedObjectSynchronizedMethod();

		Callable<Void> synchronizedBlockSharedValueMutator = () -> {
			for (int i = 0; i <= 10; i++)
				sharedObjectSynchronizedBlock.addToExistingValue(i);
			return null;
		};

		Callable<Void> synchronizedBlockSharedValueReader = () -> {
			for (int i = 0; i <= 10; i++)
				out.println("SynchronizedBlockSharedValueReader" + sharedObjectSynchronizedBlock.getSharedValue());
			return null;
		};

		Callable<Void> synchronizedMethodSharedValueMutator = () -> {
			for (int i = 0; i <= 10; i++)
				sharedObjectSynchronizedMethod.addToExistingValue(i);
			return null;
		};

		Callable<Void> synchronizedMethodkSharedValueReader = () -> {
			for (int i = 0; i <= 10; i++)
				out.println("SynchronizedMethodkSharedValueReader " + sharedObjectSynchronizedMethod.getSharedValue());
			return null;

		};

		ExecutorService executorService = Executors.newFixedThreadPool(4);
		executorService.submit(synchronizedBlockSharedValueMutator);
		executorService.submit(synchronizedBlockSharedValueReader);
		executorService.submit(synchronizedMethodSharedValueMutator);
		executorService.submit(synchronizedMethodkSharedValueReader);
		executorService.shutdown();

	}

	private static class SharedObjectSynchronizedMethod {

		private int sharedValue;

		public synchronized int getSharedValue() throws InterruptedException {
			Thread.sleep(1000);
			return sharedValue;
		}

		public synchronized void addToExistingValue(int valueToBeAdded) throws InterruptedException {
			Thread.sleep(1000);
			this.sharedValue += valueToBeAdded;
		}

	}

	private static class SharedObjectSynchronizedBlock {

		private int sharedValue;

		public int getSharedValue() throws InterruptedException {
			synchronized (this) {
				Thread.sleep(1000);
				return sharedValue;
			}
		}

		public void addToExistingValue(int valueToBeAdded) throws InterruptedException {
			synchronized (this) {
				Thread.sleep(1000);
				this.sharedValue += valueToBeAdded;
			}
		}

	}

}
