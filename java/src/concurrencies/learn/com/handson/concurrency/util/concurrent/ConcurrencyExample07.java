/**
 * 
 */
package com.handson.concurrency.util.concurrent;

import static java.lang.System.out;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample07 {

	/*
	 * Example on BlockingQueue.BlockingQueue is a concurrent data structure which
	 * which blocks consumer when queue is empty and blocks producer when queue is
	 * full ( for blocking to happen use put and take methods ).BlockingQueue also
	 * supports non-blocking operations such as offer and poll. BlockingQueue
	 * interface has several implementation classes such as
	 * ArrayBlockingQueue,LinkedBlockingQueue,PriorityBlockingQueue and
	 * SynchronousQueue.In this example we are going to look at ArrayBlockingQueue
	 * and LinkedBlockingQueue.
	 */

	private static final int QUEUE_LENGTH = 100;

	public static void main(String[] args) throws InterruptedException {

		testBlockingQueueImplementation(new ArrayBlockingQueue<>(QUEUE_LENGTH));
		testBlockingQueueImplementation(new LinkedBlockingDeque<>(QUEUE_LENGTH));

	}

	private static void testBlockingQueueImplementation(BlockingQueue<Integer> arrayBlockingQueue)
			throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(6);
		ConcurrentSkipListSet<Integer> totalValuesConsumed = new ConcurrentSkipListSet<>();
		new Thread(constructProducerThread(arrayBlockingQueue, countDownLatch)).start();
		for (int i = 0; i < 5; i++)
			new Thread(constructConsumerThread(arrayBlockingQueue, countDownLatch, totalValuesConsumed)).start();
		countDownLatch.await();
		out.println("Total Values consumed from blocking queue " + arrayBlockingQueue.getClass().getName() + " is "
				+ totalValuesConsumed.size());
	}

	private static Runnable constructProducerThread(BlockingQueue<? super Integer> blockingQueue,
			CountDownLatch countDownLatch) {

		Runnable mapMutatorThread = () -> {
			try {
				for (int i = 1; i <= 10 * QUEUE_LENGTH; i++)
					blockingQueue.put(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				countDownLatch.countDown();
			}
		};
		return mapMutatorThread;

	}

	private static Runnable constructConsumerThread(BlockingQueue<? extends Integer> blockingQueue,
			CountDownLatch countDownLatch, ConcurrentSkipListSet<? super Integer> totalValuesConsumed) {
		Runnable mapMutatorThread = () -> {
			try {
				for (int i = 1; i <= 2 * QUEUE_LENGTH; i++)
					totalValuesConsumed.add(blockingQueue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				countDownLatch.countDown();
			}
		};
		return mapMutatorThread;

	}
}
