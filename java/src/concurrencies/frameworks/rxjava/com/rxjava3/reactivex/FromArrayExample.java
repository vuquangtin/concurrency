package com.rxjava3.reactivex;

import io.reactivex.rxjava3.core.Observable;

/**
 * Signals the elements of the given array and then completes the sequence.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class FromArrayExample {

	public static void main(String[] args) {
		Integer[] array = new Integer[10];
		for (int i = 0; i < array.length; i++) {
			array[i] = i;
		}

		Observable<Integer> observable = Observable.fromArray(array);

		observable.subscribe(item -> System.out.println(item),
				error -> error.printStackTrace(),
				() -> System.out.println("Done"));

	}

}
