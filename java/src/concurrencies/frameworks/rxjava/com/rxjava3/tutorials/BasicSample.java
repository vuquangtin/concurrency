package com.rxjava3.tutorials;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class BasicSample {
	public static void println(String s) {
		System.out.println(s);
	}

	public static void testBasic() {
		Observer<String> observer = new Observer<String>() {

			@Override
			public void onCompleted() {
				println("Completed");
			}

			@Override
			public void onError(Throwable e) {
				println("Error");
			}

			@Override
			public void onNext(String s) {
				println(s);
			}
		};
		// 使用Observable.create()创建被观察者
		Observable observable1 = Observable
				.create(new Observable.OnSubscribe<String>() {
					@Override
					public void call(Subscriber<? super String> subscriber) {
						subscriber.onNext("Hello");
						subscriber.onNext("Wrold");
						// onError , onCompleted are mutual exclusion; can only
						// one execute
						subscriber.onError(new Throwable("test err"));
						subscriber.onCompleted();
					}
				});
		// 订阅
		observable1.subscribe(observer);
	}

	/**
	 * short Observable create
	 */
	public static void testJust() {
		Observable.just("Hello", "World").subscribe(new Subscriber<String>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(String s) {
				println(s);
			}
		});
	}

	/**
	 * short observer create
	 */
	public static void testAction1() {
		Observable.just("Hello2", "World2").subscribe(new Action1<String>() {
			@Override
			public void call(String s) {
				println(s);
			}
		});
	}

	public static final void main(String[] args) {
		testBasic();
		println("111111111111111111111");
		testJust();
		println("22222222222222222222222");
		testAction1();
		println("333333333333333");
	}
}