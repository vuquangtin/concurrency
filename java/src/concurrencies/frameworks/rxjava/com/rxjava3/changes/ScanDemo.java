package com.rxjava3.changes;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiFunction;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ScanDemo {
	public static void main(String[] args) {
		Observable.just(1, 2, 3, 4, 5)
				.scan(new BiFunction<Integer, Integer, Integer>() {
					@Override
					public Integer apply(Integer sum, Integer item)
							throws Throwable {
						return sum + item;
					}
				}).subscribe(new Observer<Integer>() {
					@Override
					public void onSubscribe(Disposable disposable) {

					}

					@Override
					public void onNext(Integer s) {
						System.out.print(s);
						for (int i = 0; i < s; i++) {
							System.out.print("*");
						}
						System.out.println();
					}

					@Override
					public void onError(Throwable throwable) {

					}

					@Override
					public void onComplete() {
						System.out.println("完成");
					}
				});
	}
}
