package com.rxjava3.reactivex.io.operators.all;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Predicate;

import java.util.concurrent.TimeUnit;

public class FilterBooleanRxjava {

	public static void main(String[] args) {
		// allSymbol();
		// containSymbol();
		// ambSymbol();
		// defaultEmptySymbol();
		// sequenceSymbol();
		skipUntilSymbol();

	}

	public static void skipUntilSymbol() {
		Observable.intervalRange(1, 9, 0, 1, TimeUnit.MILLISECONDS)
				.skipUntil(Observable.timer(4, TimeUnit.MILLISECONDS))
				.subscribe(new Consumer<Long>() {
					@Override
					public void accept(Long aLong) throws Exception {
						System.out.println("onNext:" + aLong);
					}
				});

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void sequenceSymbol() {
		Observable.sequenceEqual(Observable.just(1, 2, 3),
				Observable.just(1, 2, 3)).subscribe(new Consumer<Boolean>() {
			@Override
			public void accept(Boolean aBoolean) throws Exception {
				System.out.println("sequence equal:" + aBoolean);
			}
		});

	}

	public static void defaultEmptySymbol() {
		Observable.empty().defaultIfEmpty(5).subscribe(new Consumer<Object>() {
			@Override
			public void accept(Object o) throws Exception {
				System.out.println("defaultIfEmpty():" + o);
			}
		});
	}

	public static void ambSymbol() {
		Observable.ambArray(
				Observable.just(1, 2, 3).delay(1, TimeUnit.SECONDS),
				Observable.just(4, 5, 6)).subscribe(new Consumer<Integer>() {
			@Override
			public void accept(Integer integer) throws Exception {
				System.out.println("amb value:" + integer);
			}
		});

	}

	public static void allSymbol() {
		Observable.just(1, 2, 3, 4, 5).all(new Predicate<Integer>() {
			@Override
			public boolean test(Integer integer) throws Exception {
				return integer < 10;
			}
		}).subscribe(new Consumer<Boolean>() {
			@Override
			public void accept(Boolean aBoolean) throws Exception {
				System.out.println("all boolean:" + aBoolean);
			}
		});
	}

	public static void containSymbol() {
		Observable.just(1, 2, 3, 4, 5).contains(2)
				.subscribe(new Consumer<Boolean>() {
					@Override
					public void accept(Boolean aBoolean) throws Exception {
						System.out.println("contain 2 :" + aBoolean);
					}
				});
	}

}