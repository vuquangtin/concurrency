package com.rxjava3;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */

public class HelloWorld {
	public static void main(String[] args) throws InterruptedException {
		Flowable.just("Hello world").subscribe(System.out::println);
		Flowable<Integer> flow = Flowable.range(1, 5).map(v -> v * v).filter(v -> v % 3 == 0);
		flow.subscribe(System.out::println);

		Observable.create(emitter -> {
			while (!emitter.isDisposed()) {
				long time = System.currentTimeMillis();
				emitter.onNext(time);
				if (time % 2 != 0) {
					emitter.onError(new IllegalStateException("Odd millisecond!"));
					break;
				}
			}
		}).subscribe(System.out::println, Throwable::printStackTrace);

		Flowable.fromCallable(() -> {
			Thread.sleep(1000); // imitate expensive computation
			return "Done";
		}).subscribeOn(Schedulers.io()).observeOn(Schedulers.single()).subscribe(System.out::println,
				Throwable::printStackTrace);
		Flowable.range(1, 10).observeOn(Schedulers.computation()).map(v -> v * v)
				.blockingSubscribe(System.out::println);
		Thread.sleep(2000); // <--- wait for the flow to finish
		Observable<Long> observable = Observable.defer(() -> {
			long time = System.currentTimeMillis();
			return Observable.just(time);
		});

		observable.subscribe(time -> System.out.println(time));
	}
}
