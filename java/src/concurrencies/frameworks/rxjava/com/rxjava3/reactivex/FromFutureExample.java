package com.rxjava3.reactivex;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;

/**
 * Given a pre-existing, already running or already completed
 * java.util.concurrent.Future, wait for the Future to complete normally or with
 * an exception in a blocking fashion and relay the produced value or exception
 * to the consumers.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class FromFutureExample {

	public static void main(String[] args) {
		ScheduledExecutorService executor = Executors
				.newSingleThreadScheduledExecutor();

		Future<String> future = executor.schedule(() -> "Hello world!", 1,
				TimeUnit.SECONDS);

		Observable<String> observable = Observable.fromFuture(future);

		observable.subscribe(item -> System.out.println(item),
				error -> error.printStackTrace(),
				() -> System.out.println("Done"));

		executor.shutdown();

	}

}
