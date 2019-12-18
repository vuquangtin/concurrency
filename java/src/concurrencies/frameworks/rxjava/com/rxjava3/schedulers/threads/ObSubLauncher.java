package com.rxjava3.schedulers.threads;

import io.reactivex.rxjava3.core.Observable;
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

public class ObSubLauncher {
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void subscribeOn() {
		Observable.just("122", "3444", "8888", "377777")
				.subscribeOn(Schedulers.io())
				// RxCachedThreadScheduler-1
				.subscribeOn(Schedulers.trampoline())
				// блокировать нечего - пропускает поток, переходит к следующему
				.subscribeOn(Schedulers.computation())
				// RxComputationThreadPool-1
				.subscribeOn(Schedulers.newThread())
				// RxNewThreadScheduler-1
				.observeOn(Schedulers.io())
				// RxCachedThreadScheduler-2
				.subscribeOn(Schedulers.computation())
				.observeOn(Schedulers.io()) // RxCachedThreadScheduler-3

				// .subscribeOn(Schedulers.newThread())
				// .subscribeOn(Schedulers.newThread())
				// .subscribeOn(Schedulers.newThread())

				.subscribe(
						s -> {
							System.out.println(Thread.currentThread().getName()
									+ ": " + s);
							sleep(500);

						});

	}

	public static void observeOn() {
		Observable
				.just("122", "3444", "8888", "377777")
				.observeOn(Schedulers.io())
				.observeOn(Schedulers.trampoline())
				.observeOn(Schedulers.computation())
				.observeOn(Schedulers.newThread())
				.observeOn(Schedulers.newThread())
				.observeOn(Schedulers.newThread())
				.subscribe(
						s -> {
							sleep(500);
							System.out.println(Thread.currentThread().getName()
									+ ": " + s);
						});

	}

	public static void main(String[] args) {
		observeOn();
		subscribeOn();
		sleep(10000);
	}
}
