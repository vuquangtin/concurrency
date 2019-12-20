package com.rxjava3.reactivex.io.operators.all;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Filtering {
	public static void main(String[] args) {
		// filter();
		// take();
		// takeFirst();
		// takeLast();
		// takeLastBuffer();
		// last();
		// lastOrDefault();
		// first();
		// firstOrDefault();
		// skip();
		// skipLast();
		// elementAt();
		// elementAtOrDefault();
		// sample();
		// throttleLast();
		// throttleFirst();
		// debounce();
		// throttleWithTimeOut();
		// timeout();
		// distinct();
		// distinctUntilChanged();
		ofType();
		ignoreElements();
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * filter操作符,对源Observable产生的结果按照指定条件进行过滤 只有满足条件的结果才会提交给订阅者
	 */
	private static void filter() {
		Observable.just(1, 2, 3, 4, 5, 6).filter(new Func1<Integer, Boolean>() {
			@Override
			public Boolean call(Integer integer) {
				return integer < 4;
			}
		}).subscribe(getIntegerSubscriber("filter()"));
	}

	/**
	 * take操作符,把源Observable产生的结果的前n项提交给订阅者， 提交时机是Observable发布onCompleted通知之时
	 */
	private static void take() {
		Observable.just(1, 2, 3, 4, 5, 6).take(3)
				.subscribe(getIntegerSubscriber("take()"));
	}

	/**
	 * takeFirst 操作符，获取第一个满足条件的值
	 */
	private static void takeFirst() {
		Observable.just(1, 2, 3, 4, 5, 6)
				.takeFirst(new Func1<Integer, Boolean>() {
					@Override
					public Boolean call(Integer integer) {
						return integer > 3;
					}
				}).subscribe(getIntegerSubscriber("takeFirst()"));
	}

	/**
	 * takeLast操作符,把源Observable产生的结果的后n项提交给订阅者， 提交时机是Observable发布onCompleted通知之时
	 */
	private static void takeLast() {
		Observable.just(1, 2, 3, 4, 5, 6).takeLast(2)
				.subscribe(getIntegerSubscriber("takeLast()"));
	}

	/**
	 * takeLastBuffer 操作符，与takeLast 相似，不同的是最后封装成List返回 时间或者数量过滤
	 */
	private static void takeLastBuffer() {
		Observable.just(1, 2, 3, 4, 5, 6).takeLastBuffer(2)
				.subscribe(new Subscriber<List<Integer>>() {
					@Override
					public void onCompleted() {
						System.out.println("takeLastBuffer() complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("takeLastBuffer() error:"
								+ e.getMessage());
					}

					@Override
					public void onNext(List<Integer> integers) {
						System.out.println("takeLastBuffer() onNext:"
								+ integers.toString());
					}
				});
	}

	/**
	 * Last操作符,返回最后一条数据，或者最后一条满足条件的数据
	 */
	private static void last() {
		Observable.just(1, 2, 3, 4, 5, 6)
		// .last()
				.last(new Func1<Integer, Boolean>() {
					@Override
					public Boolean call(Integer integer) {
						return integer < 4;
					}
				}).subscribe(getIntegerSubscriber("last()"));
	}

	/**
	 * lastOrDefault 操作符，与last相似，不同的当所有条件不满足时，返回 defaultValue
	 */
	private static void lastOrDefault() {
		Observable.just(1, 2, 3, 4, 5, 6)
		// .lastOrDefault(0)
				.lastOrDefault(100, new Func1<Integer, Boolean>() {
					@Override
					public Boolean call(Integer integer) {
						return integer < 0;
					}
				}).subscribe(getIntegerSubscriber("lastOrDefault()"));
	}

	/**
	 * Skip操作符, 将源Observable发射的数据过滤掉前n项
	 */
	private static void skip() {
		Observable.just(1, 2, 3, 4, 5, 6).skip(3)
				.subscribe(getIntegerSubscriber("skip()"));
	}

	/**
	 * skipLast()操作符， 将源Observable发射的数据过滤掉后n项
	 */
	private static void skipLast() {
		Observable.just(1, 2, 3, 4, 5, 6).skipLast(3)
				.subscribe(getIntegerSubscriber("skipLast()"));
	}

	/**
	 * first 操作符，取第一个数或者第一个满足条件的数
	 */
	private static void first() {
		Observable.just(1, 2, 3, 4, 5, 6)
		// .first()
				.first(new Func1<Integer, Boolean>() {
					@Override
					public Boolean call(Integer integer) {
						return integer > 3;
					}
				}).subscribe(getIntegerSubscriber("first()"));
	}

	/**
	 * firstOrDefault 操作符，返回第一个满足条件的数，否则返回 defaultValue
	 */
	private static void firstOrDefault() {
		Observable.just(1, 2, 3, 4, 5, 6)
				.firstOrDefault(100, new Func1<Integer, Boolean>() {
					@Override
					public Boolean call(Integer integer) {
						return integer < 0;
					}
				}).subscribe(getIntegerSubscriber("firstOrDefault()"));
	}

	/**
	 * elementAt操作符,在源Observable产生的结果中，仅仅把指定索引的结果提交给订阅者，索引是从0开始的 超出范围会抛出异常
	 */
	private static void elementAt() {
		Observable.just(1, 2, 3, 4, 5, 6).elementAt(2)
				.subscribe(getIntegerSubscriber("element()"));
	}

	/**
	 * elementAtOrDefault 操作符，与elementAt 类似，不同的是出现异常会返回 defaultValue
	 */
	private static void elementAtOrDefault() {
		Observable.just(1, 2, 3, 4, 5, 6).elementAtOrDefault(10, 0)
				.subscribe(getIntegerSubscriber("elementAtOrDefault()"));
	}

	/**
	 * Sample操作符,定时地取发射源Observable最近发射的数据，其他的都会被过滤掉， 等效于ThrottleLast操作符。
	 */
	private static void sample() {
		Observable.interval(1, TimeUnit.SECONDS).sample(3, TimeUnit.SECONDS)
				.subscribe(new Subscriber<Long>() {
					@Override
					public void onCompleted() {
						System.out.println("sample() complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("sample() error:" + e.getMessage());
					}

					@Override
					public void onNext(Long aLong) {
						System.out.println("sample() onNext:" + aLong);
					}
				});
	}

	/**
	 * throttleLast 操作符 定期取源Observable最近发射的数据中的最后一个
	 */
	private static void throttleLast() {
		Observable.interval(1, TimeUnit.SECONDS)
				.throttleLast(3, TimeUnit.SECONDS)
				.subscribe(new Subscriber<Long>() {
					@Override
					public void onCompleted() {
						System.out.println("throttleLast() complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("throttleLast() error:"
								+ e.getMessage());
					}

					@Override
					public void onNext(Long aLong) {
						System.out.println("throttleLast() onNext:" + aLong);
					}
				});
	}

	/**
	 * throttleFirst 操作符 定期取源Observable最近发射的数据中的第一个
	 */
	private static void throttleFirst() {
		Observable.interval(1, TimeUnit.SECONDS)
				.throttleFirst(3, TimeUnit.SECONDS)
				.subscribe(new Subscriber<Long>() {
					@Override
					public void onCompleted() {
						System.out.println("throttleFirst() complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("throttleFirst() error:"
								+ e.getMessage());
					}

					@Override
					public void onNext(Long aLong) {
						System.out.println("throttleFirst() onNext:" + aLong);
					}
				});
	}

	/**
	 * debounce操作符，对源Observable每产生一个结果后，如果在规定的间隔时间内没有别的结果产生，
	 * 则把这个结果提交给订阅者处理，否则忽略该结果 如果源Observable产生的最后一个结果后在规定的时间间隔内调用了onCompleted，
	 * 那么通过debounce操作符也会把这个结果提交给订阅者
	 */
	private static void debounce() {
		Observable.create(new Observable.OnSubscribe<Integer>() {
			@Override
			public void call(Subscriber<? super Integer> subscriber) {
				if (subscriber.isUnsubscribed()) {
					return;
				}
				try {
					for (int i = 0; i < 11; i++) {
						subscriber.onNext(i);
						Thread.sleep(i * 100);
					}
					subscriber.onCompleted();
				} catch (InterruptedException e) {
					e.printStackTrace();
					subscriber.onError(e);
				}

			}
		}

		).debounce(400, TimeUnit.MILLISECONDS)
				.subscribe(getIntegerSubscriber("debounce()"));
	}

	/**
	 * throttleWithTimeOut 操作符，效果与debounce相同，发射间隔小于设定时间的都会被过滤掉
	 */
	private static void throttleWithTimeOut() {
		Observable.create(new Observable.OnSubscribe<Integer>() {
			@Override
			public void call(Subscriber<? super Integer> subscriber) {
				if (subscriber.isUnsubscribed()) {
					return;
				}
				try {
					for (int i = 0; i < 12; i++) {
						subscriber.onNext(i);
						Thread.sleep(i * 100);
					}
					subscriber.onCompleted();
				} catch (InterruptedException e) {
					e.printStackTrace();
					subscriber.onError(e);
				}

			}
		}

		).throttleWithTimeout(400, TimeUnit.MILLISECONDS)
				.subscribe(getIntegerSubscriber("debounce()"));
	}

	/**
	 * timeout 操作符，在规定时间内收不到源Observable 发出的数据，抛出异常
	 */
	private static void timeout() {
		Observable.create(new Observable.OnSubscribe<Integer>() {
			@Override
			public void call(Subscriber<? super Integer> subscriber) {
				if (subscriber.isUnsubscribed()) {
					return;
				}
				try {
					for (int i = 0; i < 10; i++) {
						subscriber.onNext(i);
						Thread.sleep(i * 110);
					}
					subscriber.onCompleted();
				} catch (InterruptedException e) {
					e.printStackTrace();
					subscriber.onError(e);
				}

			}
		}

		).timeout(400, TimeUnit.MILLISECONDS)
				.subscribe(getIntegerSubscriber("timeout()"));
	}

	/**
	 * distinct操作符,对源Observable产生的结果进行过滤，把所有重复的结果过滤掉，只输出不重复的结果给订阅者
	 */
	private static void distinct() {
		Observable.just(1, 1, 2, 3, 4, 4, 5, 6, 6).distinct()
				.subscribe(getIntegerSubscriber("distinct()"));
	}

	/**
	 * distinct操作符,对源Observable产生的结果进行过滤，过滤掉连续重复的数据
	 */
	private static void distinctUntilChanged() {
		Observable.just(1, 1, 2, 3, 1, 1, 2, 4, 4, 6).distinctUntilChanged()
				.subscribe(getIntegerSubscriber("distinctUntilChanged()"));
	}

	/**
	 * ofType 操作符，过滤指定的类型
	 */
	private static void ofType() {
		Observable.just(1, 2, 3.0, 4, 5, 6.0, 7).ofType(Integer.class)
				.subscribe(getIntegerSubscriber("ofType()"));
	}

	/**
	 * ignoreElement 操作符，丢弃源Observable发出的项目，只发送错误或者完成的通知
	 */
	private static void ignoreElements() {
		Observable.create(new Observable.OnSubscribe<Integer>() {
			@Override
			public void call(Subscriber<? super Integer> subscriber) {
				for (int i = 0; i < 10; i++) {
					subscriber.onNext(i);
				}
				subscriber.onError(new Throwable("测试发送错误"));
				subscriber.onCompleted();
			}
		}).ignoreElements().subscribe(getIntegerSubscriber("ignoreElements()"));
	}

	/**
	 * 获取 Subscriber
	 *
	 * @param funcName
	 *            调用的函数名称
	 * @return Subscriber
	 */
	private static Subscriber<Integer> getIntegerSubscriber(
			final String funcName) {
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
}