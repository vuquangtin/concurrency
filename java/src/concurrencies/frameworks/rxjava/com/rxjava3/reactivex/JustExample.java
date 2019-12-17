package com.rxjava3.reactivex;

import io.reactivex.rxjava3.core.Observable;

public class JustExample {

	public static void main(String[] args) {
		String greeting = "Hello world!";

		Observable<String> observable = Observable.just(greeting);

		observable.subscribe(item -> System.out.println(item));

		observable = Observable.just("1", "A", "3.2", "def");

		observable.subscribe(item -> System.out.print(item), error -> error.printStackTrace(),
				() -> System.out.println());
	}

}
