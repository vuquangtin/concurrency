package com.rxjava3.reactivex;

import io.reactivex.rxjava3.core.Observable;

/**
 * This type of source does not signal any onNext, onSuccess, onError or
 * onComplete. This type of reactive source is useful in testing or "disabling"
 * certain sources in combinator operators.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class NeverExample {

	public static void main(String[] args) {
		Observable<String> never = Observable.never();

		never.subscribe(
				v -> System.out.println("This should never be printed!"),
				error -> System.out.println("Or this!"),
				() -> System.out.println("This neither!"));

	}

}
