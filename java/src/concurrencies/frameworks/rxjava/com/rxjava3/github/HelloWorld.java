package com.rxjava3.github;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import org.apache.log4j.Logger;

import concurrencies.utilities.Log4jUtils;
import concurrencies.utilities.LogTest;
import concurrencies.utilities.TimeUtils;

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
	static Logger log = Logger.getLogger(LogTest.class.getName());

	public static void main(String[] args) throws InterruptedException {
		log = Log4jUtils.initLog4j();
		Flowable.just("Hello world").subscribe(System.out::println);
		Flowable<Integer> flow = Flowable.range(1, 5).map(v -> v * v)
				.filter(v -> v % 3 == 0);
		flow.subscribe(System.out::println);

		Observable.create(
				emitter -> {
					while (!emitter.isDisposed()) {
						long time = System.currentTimeMillis();
						emitter.onNext(time);
						if (time % 2 != 0) {
							emitter.onError(new IllegalStateException(
									"Odd millisecond!"));
							break;
						}else{
							System.out.println(time);
						}
					}
				}).observeOn(Schedulers.newThread()).subscribe(System.out::println, Throwable::printStackTrace);

		Flowable.fromCallable(() -> {
			Thread.sleep(1000); // imitate expensive computation
				return "Done";
			}).subscribeOn(Schedulers.io()).observeOn(Schedulers.single())
				.subscribe(System.out::println, Throwable::printStackTrace);
		Flowable.range(1, 10).observeOn(Schedulers.computation())
				.map(v -> v * v).blockingSubscribe(System.out::println);
		Thread.sleep(2000); // <--- wait for the flow to finish
		Observable<Long> observable = Observable.defer(() -> {
			long time = System.currentTimeMillis();
			return Observable.just(time);
		});

		observable.subscribe(time -> System.out.println(time));

		// Processing the numbers 1 to 10 in parallel is a bit more involved:

		Flowable.range(1, 10)
				.flatMap(
						v -> Flowable.just(v)
								.subscribeOn(Schedulers.computation())
								.map(w -> w * w))
				.blockingSubscribe(System.out::println);
		// Alternatively, the Flowable.parallel() operator and the
		// ParallelFlowable type help achieve the same parallel processing
		// pattern:
		Flowable.range(1, 10).parallel().runOn(Schedulers.computation())
				.map(v -> v * v).sequential()
				.blockingSubscribe(System.out::println);
		// Dependent sub-flows

		// flatMap is a powerful operator and helps in a lot of situations. For
		// example, given a service that returns a Flowable, we'd like to call
		// another service with values emitted by the first service:
		// Flowable<Inventory> inventorySource = warehouse.getInventoryAsync();
		//
		// inventorySource.flatMap(
		// inventoryItem -> erp.getDemandAsync(inventoryItem.getId()).map(
		// demand -> "Item " + inventoryItem.getName()
		// + " has demand " + demand)).subscribe(
		// System.out::println);

		// Simple operators to create Streams
		Flowable<Integer> flowable = Flowable.just(1, 5, 10);
		Flowable<Integer> flowable1 = Flowable.range(1, 10);
		Flowable<String> flowable2 = Flowable.fromArray(new String[] { "red",
				"green", "blue" });
		Flowable<String> flowable3 = Flowable.fromIterable(Arrays.asList("red",
				"green", "blue"));

		flowable.observeOn(Schedulers.newThread()).subscribe(
				System.out::println);
		flowable1.observeOn(Schedulers.io()).subscribe(System.out::println);
		flowable2.observeOn(Schedulers.newThread()).subscribe(
				System.out::println);
		flowable3.observeOn(Schedulers.io()).subscribe(System.out::println);

		// Flowable from Future
		CompletableFuture<String> completableFuture = CompletableFuture
				.supplyAsync(() -> { // starts a background thread the ForkJoin
										// common pool
					log.info("CompletableFuture work starts");
					TimeUtils.sleepMiliSeconds(100, "100");
					return "red";
				});

		Single<String> observable1 = Single.fromFuture(completableFuture);
		observable1.subscribe(val -> log.info("Stream completed successfully :"
				+ val));

	}
}
