/**
 * 
 */
package com.handson.concurrency.util.concurrent;

import static java.lang.System.out;

import java.util.concurrent.Exchanger;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample12 {

	/*
	 * Example on Exchanger. Exchanger is synchronization construct can be used to
	 * exchange value between threads.
	 */
	public static void main(String[] args) {
		Exchanger<Integer> exchanger = new Exchanger<>();
		new Thread(() -> {
			try {
				Integer exchangedValue = exchanger.exchange(5);
				out.println("Exchanged value in Thread 1 is " + exchangedValue);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		new Thread(() -> {
			try {
				Thread.sleep(10000);
				Integer exchangedValue = exchanger.exchange(10);
				out.println("Exchanged value in Thread 2 is " + exchangedValue);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		out.println("Please wait Thread is made to sleep for 10 seconds to understand exchanger concept ...");
		out.println();
		out.println();
		out.println();

	}

}
