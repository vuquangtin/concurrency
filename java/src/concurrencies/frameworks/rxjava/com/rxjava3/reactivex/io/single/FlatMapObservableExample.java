package com.rxjava3.reactivex.io.single;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class FlatMapObservableExample{
	public static void main(String[] args) {
		Single<String> source = Single.just("Kirk, Spock, Chekov, Sulu");
		Observable<String> names = source.flatMapObservable(text -> {
			return Observable.fromArray(text.split(",")).map(
					String::toUpperCase);
		});

		names.subscribe(name -> System.out.println("onNext: " + name));

		// prints:
		// onNext: KIRK
		// onNext: SPOCK
		// onNext: CHEKOV
		// onNext: SULU
	}
}
