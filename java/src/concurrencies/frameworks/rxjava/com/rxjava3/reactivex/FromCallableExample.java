package com.rxjava3.reactivex;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.Callable;

/**
 * When a consumer subscribes, the given java.util.concurrent.Callable is
 * invoked and its returned value (or thrown exception) is relayed to that
 * consumer.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class FromCallableExample {
	public static void main(String[] args) {
		Callable<String> callable = () -> {
			System.out.println("Hello World!");
			return "Hello World!";
		};
		Observable<String> observableString = Observable.fromCallable(callable);

		observableString.subscribe(item -> System.out.println(item),
				error -> error.printStackTrace(),
				() -> System.out.println("Done"));
	}
}
