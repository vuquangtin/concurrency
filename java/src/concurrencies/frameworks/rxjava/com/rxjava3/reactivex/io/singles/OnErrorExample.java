package com.rxjava3.reactivex.io.singles;

import rx.Observable;
import rx.Subscriber;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class OnErrorExample {
	public static void main(String[] args) {

		// 观察者
		Observable<String> observable = (Observable<String>) Observable
				.create(new Observable.OnSubscribe<String>() {

					public void call(Subscriber<? super String> sub) {
						sub.onNext("--- Hello world ---");
						sub.onError(new Exception());
						sub.onCompleted();
					}
				});

		// 订阅者
		Subscriber<String> sub = new Subscriber<String>() {
			public void onNext(String arg0) {
				System.out.println(arg0);
			}

			public void onError(Throwable arg0) {
				System.out.println(arg0);
			}

			public void onCompleted() {
				System.out.println("---completed---");
			}
		};

		observable.subscribe(sub);
	}

}
