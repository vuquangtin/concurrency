package com.rxjava3.reactivex;

import io.reactivex.rxjava3.core.Observable;

import java.io.IOException;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ErrorExample {

	public static void main(String[] args) {
		Observable<String> error = Observable.error(new IOException());

		error.subscribe(
				v -> System.out.println("This should never be printed!"),
				e -> e.printStackTrace(),
				() -> System.out.println("This neither!"));

		main1();

	}

	public static void main1() {
		Observable<String> observable = Observable.fromCallable(() -> {
			if (Math.random() < 0.5) {
				throw new IOException();
			}
			throw new IllegalArgumentException();
		});
		Observable<String> result = observable.onErrorResumeNext(error -> {
			if (error instanceof IllegalArgumentException) {
				return Observable.empty();
			}
			return Observable.error(error);
		});

		for (int i = 0; i < 10; i++) {
			result.subscribe(
					v -> System.out.println("This should never be printed!"),
					error -> error.printStackTrace(),
					() -> System.out.println("Done"));
		}

	}

}
