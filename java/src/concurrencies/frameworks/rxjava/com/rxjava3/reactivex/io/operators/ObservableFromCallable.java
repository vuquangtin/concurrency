package com.rxjava3.reactivex.io.operators;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.Callable;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ObservableFromCallable {

	public void basic() {
		Callable<String> callable = () -> {
			Thread.sleep(1000);
			return "Hello Callable";
		};

		Observable<String> source = Observable.fromCallable(callable);
		source.subscribe(System.out::println);
	}

	public void withoutLambda() {
		Callable<String> callable = new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(1000);
				return "Hello Callable";
			}
		};
		Observable<String> source = Observable.fromCallable(callable);
		source.subscribe(System.out::println);
	}

	public static void main(String[] args) {
		ObservableFromCallable demo = new ObservableFromCallable();
		demo.basic();
		demo.withoutLambda();
	}
}
