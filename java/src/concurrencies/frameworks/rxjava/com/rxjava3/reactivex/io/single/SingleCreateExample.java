package com.rxjava3.reactivex.io.single;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.rxjava3.utils.Utils;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class SingleCreateExample {

	public static void main(String[] args) {
		ScheduledExecutorService executor = Executors
				.newSingleThreadScheduledExecutor();

		ObservableOnSubscribe<String> handler = emitter -> {

			Future<Object> future = executor.schedule(() -> {
				emitter.onNext("Hello");
				emitter.onNext("World");
				emitter.onComplete();
				return null;
			}, 1, TimeUnit.SECONDS);

			emitter.setCancellable(() -> future.cancel(false));
		};

		Observable<String> observable = Observable.create(handler);

		observable.subscribe(item -> System.out.println(item),
				error -> error.printStackTrace(),
				() -> System.out.println("Done"));

		Utils.sleep10000();
		executor.shutdown();

	}

}
