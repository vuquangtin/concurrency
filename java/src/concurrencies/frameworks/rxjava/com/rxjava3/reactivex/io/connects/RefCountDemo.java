package com.rxjava3.reactivex.io.connects;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class RefCountDemo {
	public static void main(String[] args) {
		ConnectableObservable<Integer> connectableObservable = Observable.just(
				1, 2, 3, 4).publish();
		connectableObservable.subscribe(num -> System.out.println(num + "*"));
		connectableObservable.subscribe(num -> System.out.println(num + "+"));

		connectableObservable.refCount().subscribe(
				num -> System.out.println(num + "-"));
	}
}
