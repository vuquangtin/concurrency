package com.rxjava3.reactivex;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.functions.Action;

/**
 * When a consumer subscribes, the given io.reactivex.function.Action is invoked and the consumer completes or receives the exception the Action threw.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class FromActionExample {

	public static void main(String[] args) {
		Action action = () -> System.out.println("Hello World!");

		Completable completable = Completable.fromAction(action);

		completable.subscribe(() -> System.out.println("Done"),
				error -> error.printStackTrace());

	}

}
