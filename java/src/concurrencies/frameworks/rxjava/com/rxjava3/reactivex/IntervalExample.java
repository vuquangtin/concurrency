package com.rxjava3.reactivex;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Periodically generates an infinite, ever increasing numbers (of type Long).
 * The intervalRange variant generates a limited amount of such numbers.
 * interval example:
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class IntervalExample {
	public static void main(String[] args) throws InterruptedException {
		Observable<Long> clock = Observable.interval(1, TimeUnit.SECONDS);
		
		CountDownLatch latch = new CountDownLatch(1);
		clock.subscribe(time -> {
			if (time % 2 == 0) {
				System.out.println("Tick");
			} else {
				System.out.println("Tock");
			}
		}, e -> {
			latch.countDown();
		}, () -> {
			latch.countDown();
		});
		latch.await();
	}
}
