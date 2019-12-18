package com.rxjava3.reactivex;

import io.reactivex.rxjava3.core.Observable;

/**
 * This type of source signals completion immediately upon subscription.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class EmptyExample {

	public static void main(String[] args) {
		Observable<String> empty = Observable.empty();

		empty.subscribe(
		    v -> System.out.println("This should never be printed!"), 
		    error -> System.out.println("Or this!"),
		    () -> System.out.println("Done will be printed."));

	}

}
