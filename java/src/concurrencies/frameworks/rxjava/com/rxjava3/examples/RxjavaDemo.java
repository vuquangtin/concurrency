package com.rxjava3.examples;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * <pre>
 * 描述
 * </pre>
 *
 * @author hui2.chen
 * @date 2019/8/29
 */
public class RxjavaDemo {

	public static void main(String[] args) {
		// Flowable.just("Hello world").subscribe(System.out::println);

		Flowable.just("say hi").subscribe(new Consumer<String>() {
			@Override
			public void accept(String s) throws Throwable {
				System.out.println(s);
			}
		});

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

	}
}