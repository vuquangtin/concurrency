package com.rxjava3.schedulers.threads;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class SchedulerExample {
	public static void main(String[] args) throws InterruptedException {
		Observable<String> observable = Observable.just("Java", "Kotlin",
				"Scala");
		int runIt = 5;
		if (runIt == 1)
			// Aufruf 1
			observable
					.subscribeOn(Schedulers.newThread())
					.map(mapping)
					.subscribe(
							s -> {
								System.out.println(s + ":	"
										+ Thread.currentThread().getName());
							});
		if (runIt == 2)
			// Aufruf 2
			observable
					.subscribeOn(Schedulers.newThread())
					.map(mapping)
					.subscribeOn(Schedulers.newThread())
					.subscribe(
							s -> {
								System.out.println(s + ":	"
										+ Thread.currentThread().getName());
							});

		if (runIt == 3)
			// Aufruf 3
			observable
					.map(mapping)
					.observeOn(Schedulers.newThread())
					.subscribe(
							s -> {
								System.out.println(s + ":	"
										+ Thread.currentThread().getName());
							});
		if (runIt == 4)
			// Aufruf 4
			observable
					.observeOn(Schedulers.newThread())
					.map(mapping)
					.subscribe(
							s -> {
								System.out.println(s + ":	"
										+ Thread.currentThread().getName());
							});
		if (runIt == 5)
			// Aufruf 5
			observable
					.observeOn(Schedulers.newThread())
					.map(mapping)
					.observeOn(Schedulers.newThread())
					.subscribe(
							s -> {
								System.out.println(s + ":	"
										+ Thread.currentThread().getName());
							});

		Thread.sleep(5000);
	}

	static Function<String, String> mapping = new Function<String, String>() {

		@Override
		public String apply(String t) throws Exception {
			System.out.println("mapping:  " + Thread.currentThread().getName());
			return t.toLowerCase();
		}
	};
}