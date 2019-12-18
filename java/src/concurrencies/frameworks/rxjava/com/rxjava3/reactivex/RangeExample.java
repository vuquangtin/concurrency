package com.rxjava3.reactivex;

import io.reactivex.rxjava3.core.Observable;

/**
 * Generates a sequence of values to each individual consumer. The range()
 * method generates Integers, the rangeLong() generates Longs. range example:
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class RangeExample {

	public static void main(String[] args) {
		String greeting = "Hello World!";

		Observable<Integer> indexes = Observable.range(0, greeting.length());

		Observable<Character> characters = indexes.map(index -> greeting
				.charAt(index));

		characters.subscribe(character -> System.out.print(character),
				error -> error.printStackTrace(), () -> System.out.println());

	}

}
