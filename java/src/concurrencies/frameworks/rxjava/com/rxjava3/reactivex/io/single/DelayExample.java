package com.rxjava3.reactivex.io.single;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;

/**
 * With the help of the delay() operator emissions can be shifted forward in
 * time.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class DelayExample {

	public static void main(String[] args) {
		Observable<String> myObservable = Observable.just("a", "b", "c");
		CountDownLatch countDown = new CountDownLatch(1);

		myObservable.delay(2, TimeUnit.SECONDS).subscribe(System.out::println,
				(e) -> {
				}, () -> {
					countDown.countDown();
				});
		try {
			countDown.await();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Utils.sleep10000();

	}
}
