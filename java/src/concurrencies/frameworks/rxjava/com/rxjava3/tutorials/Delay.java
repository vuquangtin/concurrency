package com.rxjava3.tutorials;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

/**
 * The first variant of delay accepts parameters that define a duration of time
 * (a quantity of time, and a TimeUnit that this quantity is denominated
 * in).Each time the source Observable emits an item, delay starts a timer, and
 * when that timer reaches the given duration, the Observable returned from
 * delay emits the same item.
 * <p>
 * Note that delay will not time-shift an onError notification in this fashion
 * but it will forward such a notification immediately to its subscribers while
 * dropping any pending onNext notifications. It will however time shift an
 * onCompleted notification.
 */
public class Delay {

	public static void main(String[] args) {

		testDelaySubscription1();
	}

	private static void test1() {
		Observable.just(1).delay(1, TimeUnit.SECONDS).subscribe(integer -> {
			System.out.println(System.currentTimeMillis() + " " + integer);
		});
		System.out.println(System.currentTimeMillis() + " start.");
		Sleeper.sleep(5);
	}

	private static void test2() {
		System.out.println(System.currentTimeMillis());
		Observable.just(1, 4, 7).delay(itemValue -> {
			System.out.println("itemValue: " + itemValue);
			return Observable.timer(itemValue, TimeUnit.SECONDS);
		}).subscribe(integer -> {
			System.out.print("Delay.main: ");
			System.out.println(System.currentTimeMillis());
			System.out.println(integer);
		});

		Sleeper.sleep(10);
	}

	private static void test3() {
		Observable.just(1, 4, 7).delay(Observable.timer(1, TimeUnit.SECONDS),
				itemValue -> Observable.timer(itemValue, TimeUnit.SECONDS)).subscribe(integer -> {
					System.out.print("Delay.main: ");
					System.out.println(System.currentTimeMillis());
					System.out.println(integer);
				});
		System.out.println(System.currentTimeMillis());
		Sleeper.sleep(10);
	}

	private static void testDelaySubscription1() {
		Observable.just(1, 2).delaySubscription(Observable.timer(1, TimeUnit.SECONDS))
				.subscribe(new Observer<Integer>() {
					@Override
					public void onSubscribe(Disposable disposable) {
						System.out.print("onSubscribe: ");
						System.out.println(System.currentTimeMillis());
					}

					@Override
					public void onNext(Integer integer) {
						System.out.print("Delay.onNext: ");
						System.out.println(System.currentTimeMillis() + " value is: " + integer);
					}

					@Override
					public void onError(Throwable throwable) {

					}

					@Override
					public void onComplete() {

					}
				});
		System.out.println("start time: " + System.currentTimeMillis());
		Sleeper.sleep(10);
	}
}