package com.rxjava3.schedulers.threads;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

/**
 * Created by chae on 2/16/2016.
 */
public class TransformObserablesWithOperators {
	private static final Integer COUNT = 100;

	private static void triggerOnNext1(Subscriber<? super Integer> subscriber) {
		System.out.println("************************************");
		for (int i = 1; i < COUNT; i += 2) {
			if (!subscriber.isUnsubscribed()) {
				subscriber.onNext(i);
			}
		}
		if (!subscriber.isUnsubscribed()) {
			subscriber.onCompleted();
		}
		System.out.println("#####################################");
	}

	private static void triggerOnNext2(Subscriber<? super Integer> subscriber) {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		for (int i = 100; i < 100 + COUNT; i += 2) {
			if (!subscriber.isUnsubscribed()) {
				subscriber.onNext(i);
			}
		}
		if (!subscriber.isUnsubscribed()) {
			subscriber.onCompleted();
		}
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
	}

	/**
	 * returns a simple observable
	 *
	 * @return
	 */
	public static Observable<Integer> getObservable1() {
		return Observable.create(new Observable.OnSubscribe<Integer>() {
			public void call(Subscriber<? super Integer> subscriber) {
				triggerOnNext1(subscriber);
			}
		});
	}

	public static Observable<Integer> getObservable2() {
		return Observable.create(new Observable.OnSubscribe<Integer>() {
			public void call(Subscriber<? super Integer> subscriber) {
				triggerOnNext2(subscriber);
			}
		});
	}

	public static void subscribeFirst() {
		Observable<Integer> observable = getObservable1();
		observable.first().subscribe((i) -> System.out.println(i));
	}

	public static void subscribeLast() {
		Observable<Integer> observable = getObservable1();
		observable.last().subscribe((i) -> System.out.println(i));
	}

	public static void subscribeSkipAndTake() {
		Observable<Integer> observable = getObservable1();
		observable.skip(30).take(5).subscribe((i) -> System.out.println(i));
	}

	public static void subscribeLastThenFirst() {
		Observable<Integer> observable = getObservable1();
		observable.last().subscribe((i) -> System.out.println(i));
		observable.first().subscribe((i) -> System.out.println(i));
		observable.skip(30).take(5).subscribe((i) -> System.out.println(i));
		observable.skip(30).take(5).subscribe((i) -> System.out.println(i));
	}

	public static void transformWithMap() {
		Observable<Integer> observable = getObservable1();
		observable.map((i) -> i * -1).observeOn(Schedulers.newThread())
				.subscribe((i) -> {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(i);
				});
	}

	public static void transformWithFlatMap() {
		Observable<Integer> observable = getObservable1();
		observable.flatMap(new Func1<Integer, Observable<String>>() {
			@Override
			public Observable<String> call(Integer i) {
				return Observable.just("Value is " + String.valueOf(i));
			}
		}).observeOn(Schedulers.newThread()).subscribe((s) -> {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(s);
		});
	}

	public static void transformWithCastMap() {
		Observable<Integer> observable = getObservable1();
		observable.concatMap(new Func1<Integer, Observable<String>>() {
			@Override
			public Observable<String> call(Integer i) {
				return Observable.just("Value is " + String.valueOf(i));
			}
		}).subscribe((s) -> {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(s);
		});
	}

	public static void transformWithZip() {
		Observable<Integer> observable1 = getObservable1();
		Observable<Integer> observable2 = getObservable2();

		Observable.zip(observable2.observeOn(Schedulers.newThread()),
				observable1.observeOn(Schedulers.newThread()),
				new Func2<Integer, Integer, String>() {
					@Override
					public String call(Integer i1, Integer i2) {
						return String.valueOf(i1) + " - " + String.valueOf(i2);
					}
				}).subscribe((s) -> System.out.println(s));
	}

	public static void transformWithCombileLatest() {
		Observable<Integer> observable1 = getObservable1();
		Observable<Integer> observable2 = getObservable2();

		Observable
				.combineLatest(observable1.observeOn(Schedulers.newThread()),
						observable2.observeOn(Schedulers.newThread()),
						new Func2<Integer, Integer, String>() {
							@Override
							public String call(Integer i1, Integer i2) {
								return String.valueOf(i1) + " - "
										+ String.valueOf(i2);
							}
						}).observeOn(Schedulers.newThread())
				.subscribe((s) -> System.out.println(s));
	}

	public static void transformWithCombineLatestWithFlatMap() {
		Observable<Integer> observable1 = getObservable1()
				.observeOn(Schedulers.newThread())
				.flatMap(new Func1<Integer, Observable<Integer>>() {
					@Override
					public Observable<Integer> call(Integer i) {
						return Observable.just(i);
					}
				}).observeOn(Schedulers.newThread());

		Observable<Integer> observable2 = getObservable2()
				.observeOn(Schedulers.newThread())
				.flatMap(new Func1<Integer, Observable<Integer>>() {
					@Override
					public Observable<Integer> call(Integer i) {
						return Observable.just(i);
					}
				}).observeOn(Schedulers.newThread());

		Observable
				.combineLatest(observable1, observable2,
						new Func2<Integer, Integer, String>() {
							@Override
							public String call(Integer i1, Integer i2) {
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								return String.valueOf(i1) + " - "
										+ String.valueOf(i2);
							}
						}).observeOn(Schedulers.newThread())
				.subscribeOn(Schedulers.newThread())
				.subscribe((s) -> System.out.println(s));
	}

	public static void main(String[] args) {
		// TransformObserablesWithOperators.subscribeFirst();
		// TransformObserablesWithOperators.subscribeLast();
		// TransformObserablesWithOperators.subscribeSkipAndTake();
		// TransformObserablesWithOperators.subscribeDefer();
		// TransformObserablesWithOperators.subscribeLastThenFirst();
		// TransformObserablesWithOperators.transformWithMap();
		// TransformObserablesWithOperators.transformWithFlatMap();

		// TransformObserablesWithOperators.transformWithZip();
		// TransformObserablesWithOperators.transformWithCombileLatest();

		TransformObserablesWithOperators
				.transformWithCombineLatestWithFlatMap();

		for (int i = 0; i < 200; i++) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}