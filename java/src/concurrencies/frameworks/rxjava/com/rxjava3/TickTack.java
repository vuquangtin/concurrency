package com.rxjava3;

import io.reactivex.rxjava3.core.Observable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class TickTack {
	private static long start = System.currentTimeMillis();

	public static void main(String[] args) {
		clock().subscribe(tick -> System.out.println(new Date()));
		try {
			Thread.sleep(60_000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static Observable<Long> clock() {
		Observable<Long> fast = Observable.interval(1, TimeUnit.SECONDS);
		Observable<Long> slow = Observable.interval(3, TimeUnit.SECONDS);

		return Observable.merge(
				slow.filter(t -> isSlowTickTime()).doOnEach(
						w -> System.out.println("<SLOW>")),
				fast.filter(t -> !isSlowTickTime()).doOnEach(
						w -> System.out.println("<FAST>")));
	}

	private static Boolean isSlowTickTime() {
		return (System.currentTimeMillis() - start) % 30_000 >= 15_000;
	}

	private static boolean isSlowTickDate() {
		return LocalDate.now().getDayOfWeek() == DayOfWeek.SATURDAY
				|| LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY;
	}
}
