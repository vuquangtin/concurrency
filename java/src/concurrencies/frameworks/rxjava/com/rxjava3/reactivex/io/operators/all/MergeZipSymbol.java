package com.rxjava3.reactivex.io.operators.all;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */



public class MergeZipSymbol {

	public static void main(String[] args) {
		// mergeSymbol();
		// zipSymbol();
		// joinSymbol();
		// startwithSymbol();
		// publishSymbol();
		// refcountSymbol();
		replySymbol();
	}

	public static void replySymbol() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Observable<Long> obs = Observable.interval(1, TimeUnit.SECONDS).take(6);
		ConnectableObservable<Long> connectableObservable = obs.replay();

		connectableObservable.subscribe(new Observer<Long>() {
			@Override
			public void onSubscribe(Disposable d) {

			}

			@Override
			public void onNext(Long aLong) {
				System.out.println("onNext:" + aLong);
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("onError:" + e.getMessage());
			}

			@Override
			public void onComplete() {
				System.out.println("subscriber1: onComplete");
			}
		});

		connectableObservable.delaySubscription(3, TimeUnit.SECONDS).subscribe(
				new Observer<Long>() {
					@Override
					public void onSubscribe(Disposable d) {

					}

					@Override
					public void onNext(Long aLong) {
						System.out.println("subscriber2:onNext " + aLong
								+ "->time:" + sdf.format(new Date()));
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("subscriber2:onError");
					}

					@Override
					public void onComplete() {
						System.out.println("subscriber2:onCompleted");
					}
				});

		connectableObservable.connect();

		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void refcountSymbol() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Observable<Long> obs = Observable.interval(1, TimeUnit.SECONDS).take(6);
		Observable<Long> refCount = obs.publish().refCount();

		refCount.subscribe(new Observer<Long>() {
			@Override
			public void onSubscribe(Disposable d) {

			}

			@Override
			public void onNext(Long aLong) {
				System.out.println("onNext:" + aLong);
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("onError:" + e.getMessage());
			}

			@Override
			public void onComplete() {
				System.out.println("subscriber1: onComplete");
			}
		});

		refCount.delaySubscription(3, TimeUnit.SECONDS).subscribe(
				new Observer<Long>() {
					@Override
					public void onSubscribe(Disposable d) {

					}

					@Override
					public void onNext(Long aLong) {
						System.out.println("subscriber2:onNext " + aLong
								+ "->time:" + sdf.format(new Date()));
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("subscriber2:onError");
					}

					@Override
					public void onComplete() {
						System.out.println("subscriber2:onCompleted");
					}
				});

		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void publishSymbol() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Observable<Long> obs = Observable.interval(1, TimeUnit.SECONDS).take(6);
		ConnectableObservable<Long> connectableObservable = obs.publish();

		connectableObservable.subscribe(new Observer<Long>() {
			@Override
			public void onSubscribe(Disposable d) {

			}

			@Override
			public void onNext(Long aLong) {
				System.out.println("onNext:" + aLong);
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("onError:" + e.getMessage());
			}

			@Override
			public void onComplete() {
				System.out.println("subscriber1: onComplete");
			}
		});

		connectableObservable.delaySubscription(3, TimeUnit.SECONDS).subscribe(
				new Observer<Long>() {
					@Override
					public void onSubscribe(Disposable d) {

					}

					@Override
					public void onNext(Long aLong) {
						System.out.println("subscriber2:onNext " + aLong
								+ "->time:" + sdf.format(new Date()));
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("subscriber2:onError");
					}

					@Override
					public void onComplete() {
						System.out.println("subscriber2:onCompleted");
					}
				});

		connectableObservable.connect();

		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void startwithSymbol() {
		Observable<Integer> odds = Observable.just(1, 3, 5);
		odds.startWithItem(0).startWithArray(-1, -3, -5)
				.subscribe(new Consumer<Integer>() {
					@Override
					public void accept(Integer integer) throws Exception {
						System.out.println("onNext:" + integer);
					}
				});

	}

	public static void joinSymbol() {
		Observable<Integer> odds = Observable.just(1, 3, 5);
		Observable<Integer> evens = Observable.just(2, 4, 6);
		odds.join(evens, new Function<Integer, ObservableSource<String>>() {
			@Override
			public ObservableSource<String> apply(Integer integer)
					throws Exception {
				return Observable.just(integer + ":" + integer).delay(200,
						TimeUnit.MILLISECONDS);
			}
		}, new Function<Integer, ObservableSource<String>>() {
			@Override
			public ObservableSource<String> apply(Integer integer)
					throws Exception {
				return Observable.just(integer + ":" + integer).delay(200,
						TimeUnit.MILLISECONDS);
			}
		}, new BiFunction<Integer, Integer, String>() {
			@Override
			public String apply(Integer integer, Integer integer2)
					throws Exception {
				return integer + ":" + integer2;
			}
		}).subscribe(new Consumer<String>() {
			@Override
			public void accept(String s) throws Exception {
				System.out.println("onNext:" + s);
			}
		});

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static void zipSymbol() {
		Observable<Integer> odds = Observable.just(1, 3, 5);
		Observable<Integer> evens = Observable.just(2, 4, 6, 8);

		Observable.zip(odds, evens,
				new BiFunction<Integer, Integer, Integer>() {
					@Override
					public Integer apply(Integer integer, Integer integer2)
							throws Exception {
						return integer + integer2;
					}
				}).subscribe(new Consumer<Integer>() {
			@Override
			public void accept(Integer integer) throws Exception {
				System.out.println("onNext:" + integer);
			}
		});

	}

	public static void mergeSymbol() {
		Observable<Integer> odds = Observable.just(1, 3, 5);
		Observable<Integer> evens = Observable.just(2, 4, 6);

		Observable.merge(odds, evens).subscribe(new Consumer<Integer>() {
			@Override
			public void accept(Integer integer) throws Exception {
				System.out.println("onNext:" + integer);
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				System.out.println("onError:" + throwable.getMessage());
			}
		}, new Action() {
			@Override
			public void run() throws Exception {
				System.out.println("onCompleted");
			}
		});

	}

}