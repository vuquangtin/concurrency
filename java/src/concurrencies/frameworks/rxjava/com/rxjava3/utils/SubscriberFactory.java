package com.rxjava3.utils;

import rx.Subscriber;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class SubscriberFactory {
	/**
	 * 获取 Integer 类型 Subscriber
	 *
	 * @param funcName
	 *            调用的函数名称
	 * @return Subscriber
	 */
	public static Subscriber<Integer> getIntegerSubscriber(final String funcName) {
		return new Subscriber<Integer>() {
			@Override
			public void onCompleted() {
				System.out.println(funcName + " complete");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(funcName + " error:" + e.getMessage());
			}

			@Override
			public void onNext(Integer integer) {
				System.out.println(funcName + " onNext:" + integer);
			}
		};
	}

	/**
	 * 获取 Long 类型的 Subscribe
	 *
	 * @param funcName
	 * @return
	 */
	public static Subscriber<Long> getLongSubscriber(final String funcName) {
		return new Subscriber<Long>() {
			@Override
			public void onCompleted() {
				System.out.println(funcName + " complete");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(funcName + " error:" + e.getMessage());
			}

			@Override
			public void onNext(Long aLong) {
				System.out.println(funcName + " onNext:" + aLong);
			}
		};
	}

	/**
	 * 获取 Boolean 类型的 Subscribe
	 *
	 * @param funcName
	 * @return
	 */
	public static Subscriber<Boolean> getBooleanSubscriber(final String funcName) {
		return new Subscriber<Boolean>() {
			@Override
			public void onCompleted() {
				System.out.println(funcName + " complete");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(funcName + " error:" + e.getMessage());
			}

			@Override
			public void onNext(Boolean bool) {
				System.out.println(funcName + " onNext:" + bool);
			}
		};
	}

}