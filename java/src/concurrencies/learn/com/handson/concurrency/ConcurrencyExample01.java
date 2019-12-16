/**
 * 
 */
package com.handson.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample01 {

	private boolean done = false;

	/*
	 * Simple Multi threading example with 2 thread having a shared variable (done).
	 * Note in this example we have created thread's using anonymous inner class and
	 * lambda expression and notice how of them are referring done variable.In
	 * Lambda using this.done and anonymous class using
	 * ConcurrencyExample01.this.done.
	 */

	public static void main(String[] args) {

		ConcurrencyExample01 concurrencyExample01 = new ConcurrencyExample01();
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Runnable hellos = concurrencyExample01.getHellosThread();
		Runnable goodbyes = concurrencyExample01.getGoodByesThread();
		executor.execute(hellos);
		executor.execute(goodbyes);
		executor.shutdown();
	}

	private Runnable getGoodByesThread() {
		Runnable goodbyes = new Runnable() {

			@Override
			public void run() {
				int i = 1;
				while (!ConcurrencyExample01.this.done)
					i++;
				System.out.println("GoodBye final count " + i);
			}
		};

		return goodbyes;
	}

	private Runnable getHellosThread() {
		Runnable hellos = () -> {
			int i = 0;
			while (i < 1000)
				i++;
			System.out.println("Hello final count " + i);
			this.done = true;
		};
		return hellos;
	}

}
