package com.rxjava3.tutorials;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

/**
 * switchMap() is similar to the flatMap(). The difference is switchMap()
 * contains some time control. switchMap() changes the upstream event to an
 * Observable, and the Observable will be automatically disposed when the next
 * Observable is emitted to the downstream and start its runtime.
 */
public class SwitchMap {

	public static void main(String[] args) {
		Observable.interval(0, 500, TimeUnit.MILLISECONDS).take(3).switchMap(s -> {
			System.out.println("SwitchMap.main: " + s + " " + System.currentTimeMillis());
			return Observable
					.just(new ElementDC(s.intValue(), s.toString()), new ElementDC(s.intValue() + 1, s.toString()))
					.delay(s, TimeUnit.SECONDS);
		}).blockingSubscribe(System.out::println);
	}

}