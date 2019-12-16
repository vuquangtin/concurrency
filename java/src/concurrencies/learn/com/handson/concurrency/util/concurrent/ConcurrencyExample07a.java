/**
 * 
 */
package com.handson.concurrency.util.concurrent;

import static java.lang.System.out;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample07a {

	/*
	 * Example on SyncronousQueue and PriorityBlockingQueue.In SyncronousQueue each
	 * insert should wait unit that value is consumed.
	 */
	private static final int BATCH_SIZE = 1000;

	public static void main(String[] args) throws InterruptedException {
		SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();
		/* Consumer code */
		new Thread(() -> {
			try {
				Thread.sleep(10000);
				Integer value = synchronousQueue.take();
				out.println("Completed consuming value " + value + " in the consumer thread @ " + new Date());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		out.println("Before putting the value in queue @ " + new Date());
		synchronousQueue.put(10);
		out.println("After putting the value in queue @ " + new Date());
		out.println();
		out.println();
		out.println("PriorityBlockingQueue example starts ");

		PriorityBlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue<>();
		CountDownLatch countDownLatch = new CountDownLatch(5);
		int statringValue = 1, endingValue = BATCH_SIZE;
		for (int i = 0; i < 5; ++i, statringValue = endingValue + 1, endingValue += BATCH_SIZE)
			new Thread(mutableThread(priorityBlockingQueue, statringValue, endingValue, countDownLatch)).start();
		countDownLatch.await();

		out.println("Priority Queue Values are ");
		Integer polledValue = null;
		do {
			polledValue = priorityBlockingQueue.poll();
			System.out.println(polledValue);
		} while (polledValue != null);
		
	}

	private static Runnable mutableThread(BlockingQueue<? super Integer> blockingQueue, int statringValue,
			int endingValue, CountDownLatch countDownLatch) {
		return () -> {
			try {
				for (int i = statringValue; i <= endingValue; i++)
					blockingQueue.put(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				countDownLatch.countDown();
			}
		};

	}

}
