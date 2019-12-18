package com.rxjava.examples.test;

import rx.Observable;
import rx.schedulers.Schedulers;

import com.rxjava.examples.RxNIOSocketByteObservable;

/**
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * 
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class RxNIOSocketByteObservableTest {

	public static void main(String[] args) throws Exception {
		Observable<byte[]> observable = new RxNIOSocketByteObservable()
				.toObservable();

		observable.subscribeOn(Schedulers.computation())
				.map(bytes -> new String(bytes)).toBlocking()
				.subscribe(System.out::println);

	}
}