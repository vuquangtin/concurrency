package com.rxjava3.reactivex;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Construct a safe reactive type instance which when subscribed to by a
 * consumer, runs an user-provided function and provides a type-specific Emitter
 * for this function to generate the signal(s) the designated business logic
 * requires. This method allows bridging the non-reactive, usually
 * listener/callback-style world, with the reactive world.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class CreateExample {

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

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executor.shutdown();

	}

}
