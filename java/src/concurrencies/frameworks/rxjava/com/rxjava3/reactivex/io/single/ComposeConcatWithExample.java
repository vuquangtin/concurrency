package com.rxjava3.reactivex.io.single;

import com.rxjava3.utils.Utils;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
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
public class ComposeConcatWithExample {
	public static void main(String[] args) throws InterruptedException {
		Observable.just(foo()).concatWith(bar().map(new IntMapper()))
				.subscribe(System.out::println);
		main2(args);
	}

	public static void main2(String[] args) throws InterruptedException {
		Observable<String> mySource1 = Observable.just("a", "b", "c");
		Observable<String> mySource2 = Observable.just("x", "y", "z");
		mySource1.subscribeOn(Schedulers.newThread()).concatWith(mySource2)
				.subscribe(System.out::println);
		Utils.sleep10000();
	}

	static int foo() {
		System.out.println("foo");
		return 0;
	}

	static Observable<Integer> bar() {
		System.out.println("bar");
		return Observable.just(1, 2);
	}

	static class IntMapper implements Function<Integer, Integer> {

		@Override
		public Integer apply(Integer integer) throws Throwable {
			System.out.println("IntMapper " + integer);
			return integer + 5;
		}
	}
}
