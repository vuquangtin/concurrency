package com.rxjava3.reactivex.rxjava;

import io.reactivex.rxjava3.core.Observable;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class All {

	public static void main(String[] args) {
		Observable.range(0, 10).all(i -> i > -1).doOnSuccess(i -> System.out.println("All items larger than -1? " + i))
				.subscribe();
	}
}
