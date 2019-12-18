package com.rxjava3.reactivex;

import io.reactivex.rxjava3.core.Observable;

/***
 * Constructs a reactive type by taking a pre-existing object and emitting that
 * specific object to the downstream consumer upon subscription.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class JustExample {

	public static void main(String[] args) {
		String greeting = "Hello world!";

		Observable<String> observable = Observable.just(greeting);

		observable.subscribe(item -> System.out.println(item));

		observable = Observable.just("1", "A", "3.2", "def");

		observable.subscribe(item -> System.out.print(item),
				error -> error.printStackTrace(), () -> System.out.println());
	}

}
