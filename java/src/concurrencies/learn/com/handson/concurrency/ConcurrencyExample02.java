/**
 * 
 */
package com.handson.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample02 {

	private int counter = 0;

	/*
	 * Below example sometime's will execute forever due to goodbye thread running
	 * forever. This is due to value of the variables are read from cpu cache.To
	 * overcome this issue we can use volatile keyword.See next example.
	 */

	public static void main(String[] args) {

		ConcurrencyExample02 concurrencyExample01 = new ConcurrencyExample02();
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Callable<Void> hellos = concurrencyExample01.getHellosThread();
		Callable<Void> goodbyes = concurrencyExample01.getGoodByesThread();
		executor.submit(hellos);
		executor.submit(goodbyes);
		executor.shutdown();
	}

	private Callable<Void> getGoodByesThread() {
		return () -> {
			int localcounter = 0;
			System.out.println("Starting goodbye thread !!!");
			while (counter != 1000)
				localcounter++;
			System.out.println("Goodbye final count " + localcounter);
			return null;
		};
	}

	private Callable<Void> getHellosThread() {
		return () -> {
			System.out.println("Starting hello thread !!!");
			while (counter < 2000) {
				counter++;
				Thread.sleep(1);
			}
			System.out.println("Hello final count " + counter);
			return null;
		};
	}

}
