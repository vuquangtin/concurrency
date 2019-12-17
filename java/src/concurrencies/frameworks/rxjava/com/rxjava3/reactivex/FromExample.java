package com.rxjava3.reactivex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Action;

public class FromExample {

	public static void main(String[] args) {
		// fromIterable
		// Signals the items from a java.lang.Iterable source (such as Lists,
		// Sets or Collections or custom Iterables) and then completes the
		// sequence.

		List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));

		Observable<Integer> observable = Observable.fromIterable(list);

		observable.subscribe(item -> System.out.println(item), error -> error.printStackTrace(),
				() -> System.out.println("Done"));

		// fromArray
		// Available in: image Flowable, image Observable, image Maybe, image
		// Single, image Completable

		// Signals the elements of the given array and then completes the
		// sequence.

		// fromArray example:
		Integer[] array = new Integer[10];
		for (int i = 0; i < array.length; i++) {
			array[i] = i;
		}

		observable = Observable.fromArray(array);
		Flowable flowable = Flowable.fromArray(array);
		flowable.subscribe(item -> System.out.println(item));

		observable.subscribe(item -> System.out.println(item), error -> error.printStackTrace(),
				() -> System.out.println("Done"));

		observable = Observable.fromArray(array);

		observable.subscribe(item -> System.out.println(item), error -> error.printStackTrace(),
				() -> System.out.println("Done"));

		Callable<String> callable = () -> {
			System.out.println("Hello World!");
			return "Hello World!";
		};

		Observable<String> observableString = Observable.fromCallable(callable);

		observableString.subscribe(item -> System.out.println(item), error -> error.printStackTrace(),
				() -> System.out.println("Done"));

		Action action = () -> System.out.println("Hello World!");

		Completable completable = Completable.fromAction(action);

		completable.subscribe(() -> System.out.println("Done"), error -> error.printStackTrace());

		Runnable runnable = () -> System.out.println("Hello World!");

		completable = Completable.fromRunnable(runnable);

		completable.subscribe(() -> System.out.println("-->Done"), error -> error.printStackTrace());

		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

		Future<String> future = executor.schedule(() -> "Hello world!", 1, TimeUnit.SECONDS);

		observableString = Observable.fromFuture(future);

		observableString.subscribe(item -> System.out.println(item), error -> error.printStackTrace(),
				() -> System.out.println("Done"));

		executor.shutdown();
	}

}
