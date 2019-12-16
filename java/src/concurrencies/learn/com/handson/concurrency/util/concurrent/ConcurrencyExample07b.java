/**
 * 
 */
package com.handson.concurrency.util.concurrent;

import static java.lang.System.out;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample07b {

	/*
	 * Example on TrasferQueue using LinkedTransferQueue implementation.TrasferQueue
	 * is a sub interface of BlockingQueue.A TransferQueue may be useful for example
	 * in message passing applications in which producers sometimes (using method
	 * transfer(E)) await receipt of elements by consumers invoking take or poll.
	 */
	private static final int PRODUCERS_COUNT = 10;

	public static void main(String[] args) throws InterruptedException {
		TransferQueue<Integer> transferQueue = new LinkedTransferQueue<>();
		ExecutorService executorService = Executors.newFixedThreadPool(PRODUCERS_COUNT + 2);
		for (int i = 1; i <= PRODUCERS_COUNT; i++)
			executorService.submit(constructProducerThread(transferQueue, i));
		executorService.submit(constructConsumerThread(transferQueue));
		executorService.shutdown();
	}

	private static Callable<Void> constructConsumerThread(TransferQueue<Integer> transferQueue) {
		return () -> {
			Integer value = null;
			do {
				Thread.sleep(2000);
				value = transferQueue.poll();
				if (value != null)
					out.println("Completed consuming value " + value + " in the consumer thread @ " + new Date());
			} while (value != null);

			return null;
		};
	}

	private static Callable<Void> constructProducerThread(TransferQueue<Integer> transferQueue, int i) {
		return () -> {
			out.println("Before putting the value " + i + " in queue @ " + new Date());
			transferQueue.transfer(i);
			out.println("After putting the value " + i + " in queue @ " + new Date());
			return null;
		};
	}

}
