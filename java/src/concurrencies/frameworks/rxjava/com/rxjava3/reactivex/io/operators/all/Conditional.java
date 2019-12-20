package com.rxjava3.reactivex.io.operators.all;
import java.util.concurrent.TimeUnit;

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
public class Conditional {
	public static void main(String[] args) throws InterruptedException {
		// amb();
		// defaultIfEmpty();
		// doWhile();
		// whileDo();
		// ifThen();
		// skipUntil();
		// skipWhile();
		// switchCase();
		// takeUntil();
		takeWhile();

		Thread.sleep(Long.MAX_VALUE);
	}

	/**
	 * Amb操作符可以将至多9个Observable结合起来，让他们竞争。
	 * 哪个Observable首先发射了数据（包括onError和onComplete)就会继续发射这个Observable的数据，
	 * 其他的Observable所发射的数据都会别丢弃。
	 */
	private static void amb() {
		Observable<Integer> observable1 = Observable.just(1, 2, 3).delay(3,
				TimeUnit.SECONDS);
		Observable<Integer> observable2 = Observable.just(4, 5, 6).delay(2,
				TimeUnit.SECONDS);
		Observable<Integer> observable3 = Observable.just(7, 8, 9).delay(1,
				TimeUnit.SECONDS);

		Observable.amb(observable1, observable2, observable3).subscribe(
				SubscriberFactory.getIntegerSubscriber("amb()"));
	}

	/**
	 * DefaultIfEmpty操作符会判断源Observable是否发射数据，
	 * 如果源Observable发射了数据则正常发射这些数据，如果没有则发射一个默认的数据
	 */
	private static void defaultIfEmpty() {
		Integer[] items = new Integer[] { 1, 2, 3 };
		Observable
				.from(items)
				.defaultIfEmpty(100)
				.subscribe(
						SubscriberFactory
								.getIntegerSubscriber("defaultIfEmpty()"));

	}

	/**
	 * doWhile,发射原始Observable的数据序列，然后重复发射这个序列直到不满足这个条件为止
	 */
	private static void doWhile() {

	}

	/**
	 * whileDo,如果条件为true，则发射源Observable数据序列，并且只要条件保持为true就重复发射此数据序列
	 */
	private static void whileDo() {

	}

	/**
	 * ifThen,只有当某个条件为真时才发射原始Observable的数据序列，否则发射一个空的或默认的序列
	 */
	private static void ifThen() {

	}

	/**
	 * SkipUntil,丢弃原始Observable发射的数据，直到第二个Observable发射了一个数据，
	 * 然后发射原始Observable的剩余数据
	 * <p>
	 * 数字 0 和 1 没有发出来
	 */
	private static void skipUntil() {
		Observable.interval(1, TimeUnit.SECONDS).take(10)
				.skipUntil(Observable.timer(3, TimeUnit.SECONDS))
				.subscribe(SubscriberFactory.getLongSubscriber("skipUntil()"));
	}

	/**
	 * skipWhile,丢弃原始Observable发射的数据，直到一个特定的条件为假，然后发射原始Observable剩余的数据
	 */
	private static void skipWhile() {
		Observable.interval(1, TimeUnit.SECONDS)
				.skipWhile(new Func1<Long, Boolean>() {
					@Override
					public Boolean call(Long aLong) {
						return aLong < 5;
					}
				}).take(10)
				.subscribe(SubscriberFactory.getLongSubscriber("skipWhile()"));
	}

	/**
	 * 基于一个计算结果，发射一个指定Observable的数据序列
	 */
	private static void switchCase() {

	}

	/**
	 * takeUntil,发射来自原始Observable的数据，直到第二个Observable发射了一个数据或一个通知
	 */
	private static void takeUntil() {
		Observable.interval(1, TimeUnit.SECONDS)
				.takeUntil(Observable.timer(5, TimeUnit.SECONDS))
				.subscribe(SubscriberFactory.getLongSubscriber("takeUntil()"));
	}

	/**
	 * takeWhile,发射原始Observable的数据，直到一个特定的条件为真，然后跳过剩余的数据
	 */
	private static void takeWhile() {
		Observable.interval(1, TimeUnit.SECONDS)
				.takeWhile(new Func1<Long, Boolean>() {
					@Override
					public Boolean call(Long aLong) {
						return aLong < 10;
					}
				})
				.subscribe(SubscriberFactory.getLongSubscriber("takeWhile()"));
	}

}