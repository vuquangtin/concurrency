package com.rxjava3.reactivex;

import io.reactivex.rxjava3.core.Flowable;

/**
 * Creates a cold, synchronous and stateful generator of values. generate
 * example:
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class GenerateExample {

	public static void main(String[] args) {
		int startValue = 1;
		int incrementValue = 1;
		Flowable<Integer> flowable = Flowable.generate(() -> startValue, (s,
				emitter) -> {
			int nextValue = s + incrementValue;
			emitter.onNext(nextValue);
			return nextValue;
		});
		flowable.subscribe(value -> System.out.println(value));
	}

}
