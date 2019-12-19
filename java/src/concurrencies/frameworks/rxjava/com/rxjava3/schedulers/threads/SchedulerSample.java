package com.rxjava3.schedulers.threads;

import java.util.concurrent.Callable;

import com.rxjava3.utils.Utils;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
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

public class SchedulerSample {
	static void rxJavaTest3() {
		Observable.just("Hello", "Word").subscribeOn(Schedulers.newThread())// 指定
																			// subscribe()
																			// 发生在新的线程
				/**
				 * rxandroid use: AndroidSchedulers.mainThread()
				 *
				 */
				.observeOn(Schedulers.newThread())// 指定 Subscriber 的回调发生在主线程
				.subscribe(new Action1<String>() {
					@Override
					public void call(String s) {
						Utils.println("rx3:" + s);
					}
				});
	}

	private static Callable<Integer> thatReturnsNumberOne() {
		return () -> {
			System.out.println("Observable thread: "
					+ Thread.currentThread().getName());
			return 1;
		};
	}

	private static Func1<Integer, String> numberToString() {
		return number -> {
			System.out.println("Operator thread: "
					+ Thread.currentThread().getName());
			return String.valueOf(number);
		};
	}

	private static Action1<String> printResult() {
		return result -> {
			System.out.println("Subscriber thread: "
					+ Thread.currentThread().getName());
			System.out.println("Result: " + result);
		};
	}

	static void printThread0() {
		Observable.fromCallable(thatReturnsNumberOne()) // the Observable
				.map(numberToString()) // the Operator
				.subscribe(printResult()); // the Subscriber
	}

	static void printThread1() {
		Observable.fromCallable(thatReturnsNumberOne())
				.subscribeOn(Schedulers.newThread()) // <<<<
				.map(numberToString()).subscribe(printResult());
	}

	static void printThread2() {
		Observable.fromCallable(thatReturnsNumberOne()).map(numberToString())
				.observeOn(Schedulers.newThread()) // subscriber on different
													// thread
				.subscribe(printResult());
	}

	static void printThread3() {
		Observable.fromCallable(thatReturnsNumberOne())
				.subscribeOn(Schedulers.newThread()) // <<<<
				.map(numberToString()).observeOn(Schedulers.newThread()) // subscriber
																			// on
																			// different
																			// thread
				.subscribe(printResult());
	}

	static void printThread4() {
		Observable.fromCallable(thatReturnsNumberOne())
				.subscribeOn(Schedulers.newThread()) // <<<<
				.map(numberToString()).observeOn(Schedulers.io()) // subscriber
																	// on
																	// different
																	// thread
				.subscribe(printResult());
	}

	public static final void main(String[] args) {
		/**
		 * http://www.jianshu.com/p/ecfb9d68d2a2
		 */
		rxJavaTest3();
		Utils.println(":----------------");
		sleep();
		/**
		 * https://praveer09.github.io/technology/2016/02/29/rxjava-part-3-
		 * multithreading/
		 */
		printThread0();
		Utils.println("0----------------");
		sleep();
		printThread1();
		Utils.println("1----------------");
		sleep();
		printThread2();
		Utils.println("2----------------");
		sleep();
		printThread3();
		Utils.println("3----------------");
		printThread4();
		Utils.println("4----------------");

	}

	static void sleep() {
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}