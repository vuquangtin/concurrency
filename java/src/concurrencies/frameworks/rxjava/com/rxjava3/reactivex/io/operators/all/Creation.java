package com.rxjava3.reactivex.io.operators.all;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;

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
public class Creation {

	private static int num = 0;

	public static void main(String[] args) {
		// just();
		// from();
		// create();
		// fromEmitter();
		// defer();
		// range();
		// interval();
		// timer();
		// empty();
		// error();
		never();
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * just 操作符
	 */
	private static void just() {
		Observable.just(1, 2, 3).subscribe(
				SubscriberFactory.getIntegerSubscriber("just()"));
	}

	/**
	 * from 操作符
	 */
	private static void from() {
		Integer[] items = new Integer[] { 0, 1, 2, 3, 4, 5 };
		Observable.from(items).subscribe(new Action1<Integer>() {
			@Override
			public void call(Integer integer) {
				System.out.println(integer);
			}
		}, new Action1<Throwable>() {
			@Override
			public void call(Throwable throwable) {
				System.out.println("error:" + throwable.getMessage());
			}
		}, new Action0() {
			@Override
			public void call() {
				System.out.println("complete");
			}
		});
	}

	/**
	 * create 操作符
	 */
	private static void create() {
		Observable.create(new Observable.OnSubscribe<Integer>() {
			@Override
			public void call(Subscriber<? super Integer> subscriber) {
				try {
					if (!subscriber.isUnsubscribed()) {
						for (int i = 1; i < 5; i++) {
							subscriber.onNext(i);
						}
						subscriber.onCompleted();
					}
				} catch (Exception e) {
					subscriber.onError(e);
				}
			}
		}).subscribe(SubscriberFactory.getIntegerSubscriber("create()"));
	}

	/**
	 * fromEmitter 操作符
	 */
	private static void fromEmitter() {

	}

	/**
	 * defer 操作符，直到有订阅者时，才创建 Observable 并执行
	 */
	private static void defer() {
		num = 10;
		Observable deferObservable = Observable
				.defer(new Func0<Observable<Integer>>() {
					@Override
					public Observable<Integer> call() {
						return Observable.just(num);
					}
				});

		num = 15;

		deferObservable.subscribe(new Subscriber() {
			@Override
			public void onCompleted() {
				System.out.println("complete");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("error:" + e.getMessage());
			}

			@Override
			public void onNext(Object o) {
				System.out.println("next:" + o.toString());
			}
		});
	}

	/**
	 * range 操作符，发射一系列大于等于n的m个值
	 */
	private static void range() {
		Observable.range(10, 5).subscribe(
				SubscriberFactory.getIntegerSubscriber("range()"));
	}

	/**
	 * interval 操作符，从0开始，每隔固定时间发射一个数字
	 */
	private static void interval() {
		Observable.interval(1, TimeUnit.SECONDS).subscribe(
				new Subscriber<Long>() {
					@Override
					public void onCompleted() {
						System.out.println("complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("error:" + e.getMessage());
					}

					@Override
					public void onNext(Long aLong) {
						System.out.println("interval() onNext:" + aLong);
					}
				});
	}

	/**
	 * timer 操作符， timer(4, 2, TimeUnit.SECONDS),在 4 秒之后，每间隔 2 秒，发送一个数字 timer(4,
	 * TimeUnit.SECONDS) 在 4 秒后，发出一个数字
	 */
	private static void timer() {
		Observable.timer(4, 2, TimeUnit.SECONDS).subscribe(
				new Subscriber<Long>() {
					@Override
					public void onCompleted() {
						System.out.println("complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("error:" + e.getMessage());
					}

					@Override
					public void onNext(Long aLong) {
						System.out.println("time() onNext:" + aLong);
					}
				});
	}

	/**
	 * 创建一个 Observable,不发送任何数字，直接执行 onComplete() 方法
	 */
	private static void empty() {
		Observable.empty().subscribe(new Subscriber<Object>() {
			@Override
			public void onCompleted() {
				System.out.println("empty() complete");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("error:" + e.getMessage());
			}

			@Override
			public void onNext(Object o) {
				System.out.println("onNext:" + o.toString());
			}
		});
	}

	/**
	 * 创建一个 Observable,不发送任何数字，直接执行 onError() 方法
	 */
	private static void error() {
		Observable.error(new Throwable("这是一个Error")).subscribe(
				new Subscriber<Object>() {
					@Override
					public void onCompleted() {
						System.out.println("error() complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("error(): error " + e.getMessage());
					}

					@Override
					public void onNext(Object o) {
						System.out.println("onNext()");
					}
				});
	}

	/**
	 * 创建一个 Observable,不做任何事情
	 */
	private static void never() {
		Observable.never().subscribe(new Subscriber<Object>() {
			@Override
			public void onCompleted() {
				System.out.println("never() complete");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("never() error" + e.getMessage());
			}

			@Override
			public void onNext(Object o) {
				System.out.println("never() onNext" + o.toString());
			}
		});
	}
}
