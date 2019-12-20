package com.rxjava3.reactivex.io.operators.all;
import rx.Observable;
import rx.functions.Func1;

import com.rxjava3.utils.SubscriberFactory;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Bool {
	public static void main(String[] args) throws InterruptedException {
		all();
		contains();
		exist();
		isEmpty();
		sequenceEqual();

		Thread.sleep(Long.MAX_VALUE);
	}

	/**
	 * All操作符根据一个函数对源Observable发射的所有数据进行判断，最终返回的结果就是这个判断结果。
	 * 这个函数使用发射的数据作为参数，内部判断所有的数据是否满足我们定义好的判断条件，如果全部都满足则返回true，否则就返回false
	 */
	private static void all() {
		Observable.just(1, 2, 3, 4, 5).all(new Func1<Integer, Boolean>() {
			@Override
			public Boolean call(Integer integer) {
				return integer < 5;
			}
		}).subscribe(SubscriberFactory.getBooleanSubscriber("all()"));
	}

	/**
	 * Contains操作符用来判断源Observable所发射的数据是否包含某一个数据，
	 * 如果包含会返回true，如果源Observable已经结束了却还没有发射这个数据则返回false
	 */
	private static void contains() {
		Observable
				.just(1, 2, 3, 4, 5)
				.contains(10)
				.subscribe(SubscriberFactory.getBooleanSubscriber("contains()"));
	}

	/**
	 * exist操作符用来判断源Observable所发射的数据是否包含满足条件的，
	 * 如果包含会返回true，如果源Observable已经结束了却还没有满足条件的返回false
	 */
	private static void exist() {
		Observable.just(1, 2, 3, 4, 5).exists(new Func1<Integer, Boolean>() {
			@Override
			public Boolean call(Integer integer) {
				return integer > 10;
			}
		}).subscribe(SubscriberFactory.getBooleanSubscriber("exist()"));
	}

	/**
	 * IsEmpty操作符用来判断源Observable是否发射过数据，
	 * 如果发射过就会返回false，如果源Observable已经结束了却还没有发射这个数据则返回true。
	 */
	private static void isEmpty() {
		Observable.empty().isEmpty()
				.subscribe(SubscriberFactory.getBooleanSubscriber("isEmpty()"));
	}

	/**
	 * sequenceEqual,判断两个Observables发射的序列是否相等
	 */
	private static void sequenceEqual() {
		Observable.sequenceEqual(Observable.just(1, 2, 3),
				Observable.just(1, 2, 3)).subscribe(
				SubscriberFactory.getBooleanSubscriber("sequenceEqual()"));
	}

}