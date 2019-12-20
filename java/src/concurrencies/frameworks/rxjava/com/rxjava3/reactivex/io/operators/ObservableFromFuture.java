package com.rxjava3.reactivex.io.operators;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ObservableFromFuture {

	public void basic() {
		Future<String> future = Executors.newSingleThreadExecutor().submit(() -> {
			Thread.sleep(1000);
			return "Hello Future";
		});

		Observable<String> source = Observable.fromFuture(future);
		source.subscribe(System.out::println);
	}

	public static void main(String[] args) {
		ObservableFromFuture demo = new ObservableFromFuture();
		demo.basic();
	}
}
