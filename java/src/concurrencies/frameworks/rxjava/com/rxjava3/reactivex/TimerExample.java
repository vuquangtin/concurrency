package com.rxjava3.reactivex;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

/**
 * After the specified time, this reactive source signals a single 0L (then
 * completes for Flowable and Observable).
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class TimerExample {

	public static void main(String[] args) {
		Observable<Long> eggTimer = Observable.timer(5, TimeUnit.MINUTES);

		eggTimer.blockingSubscribe(v -> System.out.println("Egg is ready!"));
	}

}
