/**
 * 
 */
package com.handson.concurrency;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample06a {

	private static final int THREAD_COUNT = 10;

	/*
	 * Wait and notifyAll example.For Multiple producers and consumers, we can use
	 * wait and notifyAll.In this example we also count no.of produced and no.of
	 * consumed values.
	 */
	public static void main(String[] args) throws InterruptedException {
		Thread.setDefaultUncaughtExceptionHandler((Thread th, Throwable t) -> out.println(
				"Exception thrown in thread " + th.getName() + " and reason for exception is - " + t.getMessage()));

		CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT * 2);
		ProducerConsumerSharedObject sharedObject = new ProducerConsumerSharedObject();
		List<Thread> producerThreads = new ArrayList<>();
		List<Thread> consumerThreads = new ArrayList<>();
		for (int i = 0; i < THREAD_COUNT; i++)
			producerThreads.add(constructProducerThread(sharedObject, i, countDownLatch));
		for (int i = 0; i < THREAD_COUNT; i++)
			consumerThreads.add(constructConsumerThread(sharedObject, i, countDownLatch));

		producerThreads.parallelStream().forEach(Thread::start);
		consumerThreads.parallelStream().forEach(Thread::start);
		countDownLatch.await();
		out.println("Total number of values produced - " + sharedObject.getValuesProduced());
		out.println("Total number of values consumed - " + sharedObject.getValuesConsumed());
	}

	private static Thread constructConsumerThread(ProducerConsumerSharedObject sharedObject, int threadId,
			CountDownLatch countDownLatch) {
		Thread consumerThread = new Thread(() -> {
			try {
				for (int i = 0; i < 10; i++)
					System.out.println(String.format("Shared Object value in Thread %d is - %s ", threadId,
							sharedObject.getSharedValue()));
				countDownLatch.countDown();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
		return consumerThread;
	}

	private static Thread constructProducerThread(ProducerConsumerSharedObject sharedObject, int threadId,
			CountDownLatch countDownLatch) {
		Thread producerThread = new Thread(() -> {
			try {
				for (int i = 0; i < 10; i++)
					sharedObject.setSharedValue(String.format("Producer Thread %d value %d", threadId, i));
				countDownLatch.countDown();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
		return producerThread;
	}

	private static class ProducerConsumerSharedObject {

		private String sharedValue;
		private int valuesProduced;
		private int valuesConsumed;

		public synchronized String getSharedValue() throws InterruptedException {
			while (sharedValue == null)
				wait();
			valuesConsumed++;
			String valuetoBeRetuned = sharedValue;
			sharedValue = null;
			notifyAll();
			return valuetoBeRetuned;
		}

		public synchronized void setSharedValue(String sharedValue) throws InterruptedException {
			while (this.sharedValue != null)
				wait();
			valuesProduced++;
			this.sharedValue = sharedValue;
			notifyAll();
		}

		public int getValuesProduced() {
			return valuesProduced;
		}

		public int getValuesConsumed() {
			return valuesConsumed;
		}

		@Override
		public String toString() {
			return "SharedObject [sharedValue=" + sharedValue + "]";
		}

	}
}
