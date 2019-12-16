/**
 * 
 */
package com.handson.concurrency.util.concurrent;

import static java.lang.System.out;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample13 {

	/*
	 * Example on ThreadLocalRandom.
	 */
	public static void main(String[] args) {
		ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
		new Thread(() -> {
			ThreadLocalRandom thread1LocalRandom = ThreadLocalRandom.current();
			out.println("Thread Local Random value in Thread 1 is " + thread1LocalRandom.nextInt());
		}).start();
		new Thread(() -> {
			ThreadLocalRandom thread2LocalRandom = ThreadLocalRandom.current();
			out.println("Thread Local Random value in Thread 2 is " + thread2LocalRandom.nextInt());
		}).start();
		out.println("Thread Local Random value in Main Thread  is " + threadLocalRandom.nextInt());
	}

}
