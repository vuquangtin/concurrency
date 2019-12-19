package com.rxjava3.reactivex;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;

/**
 * Periodically generates an infinite, ever increasing numbers (of type Long).
 * The intervalRange variant generates a limited amount of such numbers.
 * interval example:
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class IntervalExample {
	public static void main(String[] args) {
		Observable<Long> clock = Observable.interval(1, TimeUnit.SECONDS);

		clock.subscribe(time -> {
			if (time % 2 == 0) {
				System.out.println("Tick");
			} else {
				System.out.println("Tock");
			}
		});
	}
}
