package com.rxjava3.reactivex.io.operators.all;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;

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
public class Combining {
	public static void main(String[] args) throws InterruptedException {
		 startWith();
		 merge();
		 mergeDelayError();
		 zip();
		 zipWith();
		 combineLatest();
		 join();
		 groupJoin();
		switchOnNext();
		Thread.sleep(Long.MAX_VALUE);
	}

	/**
	 * StartWith操作符,在源Observable发射的数据前面插上一些数据,Iterable或者Observable
	 */
	private static void startWith() {
		List<Integer> integerList = new ArrayList<>();
		integerList.add(100);
		integerList.add(99);

		Observable<Integer> ob = Observable.just(111, 222, 333);

		Observable
				.just(4, 5, 6)
				.startWith(0, 1, 2, 3)
				.startWith(integerList)
				.startWith(ob)
				.subscribe(
						SubscriberFactory.getIntegerSubscriber("startWith()"));
	}

	/**
	 * Merge操作符将多个Observable发射的数据整合起来发射，但是其发射的数据有可能是交错的， 如果想要没有交错，可以使用concat操作符。
	 * 当某一个Observable发出onError的时候，merge的过程会被停止并将错误分发给Subscriber
	 * 如果不想让错误终止merge的过程，可以使用MergeDelayError操作符，会将错误在merge结束后再分发
	 */
	private static void merge() {
		Observable.merge(
				Observable.create(new Observable.OnSubscribe<Integer>() {
					@Override
					public void call(Subscriber<? super Integer> subscriber) {
						for (int i = 0; i < 10; i++) {
							if (i == 5) {
								subscriber.onError(new Throwable("出现error"));
							}
							subscriber.onNext(i);
						}
					}
				}), Observable.create(new Observable.OnSubscribe<Integer>() {
					@Override
					public void call(Subscriber<? super Integer> subscriber) {
						for (int i = 10; i < 20; i++) {
							subscriber.onNext(i);
						}
						subscriber.onCompleted();
					}
				}))
				.subscribe(SubscriberFactory.getIntegerSubscriber("merge()"));
	}

	/**
	 * mergeDelayError 操作符，与merge类似，不同的是发出onError的时机，在merge结束后再分发
	 */
	private static void mergeDelayError() {
		Observable.mergeDelayError(
				Observable.create(new Observable.OnSubscribe<Integer>() {
					@Override
					public void call(Subscriber<? super Integer> subscriber) {
						for (int i = 0; i < 10; i++) {
							if (i == 5) {
								subscriber.onError(new Throwable("出现error"));
							}
							subscriber.onNext(i);
						}
					}
				}), Observable.create(new Observable.OnSubscribe<Integer>() {
					@Override
					public void call(Subscriber<? super Integer> subscriber) {
						for (int i = 10; i < 20; i++) {
							subscriber.onNext(i);
						}
						subscriber.onCompleted();
					}
				})).subscribe(
				SubscriberFactory.getIntegerSubscriber("mergeDelayError()"));
	}

	/**
	 * Zip操作符，将多个Observable发射的数据按顺序组合起来，每个数据只能组合一次，而且都是有序的。
	 * 最终组合的数据的数量由发射数据最少的Observable来决定
	 */
	private static void zip() {
		Observable<Integer> observable1 = Observable.just(10, 20, 30);
		Observable<Integer> observable2 = Observable.just(1, 2);
		Observable.zip(observable1, observable2,
				new Func2<Integer, Integer, Integer>() {
					@Override
					public Integer call(Integer integer, Integer integer2) {
						return integer * integer2;
					}
				}).subscribe(SubscriberFactory.getIntegerSubscriber("zip()"));
	}

	/**
	 * zipWith 操作符，组合一个Observable，或者Iterable
	 */
	private static void zipWith() {
		Observable<Integer> observable = Observable.just(10, 20, 30);
		Observable.just(1, 2, 3)
				.zipWith(observable, new Func2<Integer, Integer, String>() {
					@Override
					public String call(Integer integer, Integer integer2) {
						return "" + integer + "--" + integer2;
					}
				}).subscribe(new Subscriber<String>() {
					@Override
					public void onCompleted() {
						System.out.println("zipWith() complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("zipWith() error:" + e.getMessage());
					}

					@Override
					public void onNext(String s) {
						System.out.println("zipWith() onNext:" + s);
					}
				});
	}

	// TODO: 16/12/29 and() , then() , when()

	/**
	 * combineLatest操作符把两个Observable产生的结果进行合并，合并的结果组成一个新的Observable。
	 * 这两个Observable中任意一个Observable产生的结果，都和另一个Observable最后产生的结果，按照一定的规则进行合并
	 */
	private static void combineLatest() {
		Observable<Long> observable1 = Observable.timer(0, 1000,
				TimeUnit.MILLISECONDS).take(5);
		Observable<Long> observable2 = Observable.timer(0, 2100,
				TimeUnit.MILLISECONDS).take(5);
		Observable.combineLatest(observable1, observable2,
				new Func2<Long, Long, String>() {
					@Override
					public String call(Long aLong, Long aLong2) {
						return "" + aLong + "--" + aLong2;
					}
				}).subscribe(new Subscriber<String>() {
			@Override
			public void onCompleted() {
				System.out.println("combineLatest() complete");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("combineLatest() error:" + e.getMessage());
			}

			@Override
			public void onNext(String s) {
				System.out.println("combineLatest() onNext:" + s);
			}
		});
	}

	/**
	 * join操作符把类似于combineLatest操作符，也是两个Observable产生的结果进行合并，合并的结果组成一个新的Observable
	 * ， 但是join操作符可以控制每个Observable产生结果的生命周期，
	 * 在每个结果的生命周期内，可以与另一个Observable产生的结果按照一定的规则进行合并
	 * <p>
	 * <p>
	 * join方法的用法如下： observableA.join(observableB, observableA产生结果生命周期控制函数，
	 * observableB产生结果生命周期控制函数， observableA产生的结果与observableB产生的结果的合并规则）
	 */
	private static void join() {
		Observable<Long> observable1 = Observable.timer(0, 500,
				TimeUnit.MILLISECONDS).take(5);
		Observable<Long> observable2 = Observable.timer(0, 1000,
				TimeUnit.MILLISECONDS).take(5);

		observable1.join(observable2, new Func1<Long, Observable<Long>>() {
			@Override
			public Observable<Long> call(Long along) {
				return Observable.just(along).delay(400, TimeUnit.MILLISECONDS);
			}
		}, new Func1<Long, Observable<Long>>() {
			@Override
			public Observable<Long> call(Long along) {
				return Observable.just(along).delay(600, TimeUnit.MILLISECONDS);
			}
		}, new Func2<Long, Long, String>() {
			@Override
			public String call(Long long1, Long long2) {
				return "" + long1 + "--" + long2;
			}
		}).subscribe(new Subscriber<String>() {
			@Override
			public void onCompleted() {
				System.out.println("join() complete");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("join() error:" + e.getMessage());
			}

			@Override
			public void onNext(String string) {
				System.out.println("join() onNext:" + string);
			}
		});
	}

	/**
	 * 搞不懂。。。。。
	 */
	private static void groupJoin() {
		Observable<Long> observable1 = Observable.timer(0, 500,
				TimeUnit.MILLISECONDS).take(5);
		Observable<Long> observable2 = Observable.timer(0, 1000,
				TimeUnit.MILLISECONDS).take(5);

		observable1.groupJoin(observable2, new Func1<Long, Observable<Long>>() {
			@Override
			public Observable<Long> call(Long along) {
				return Observable.just(along).delay(400, TimeUnit.MILLISECONDS);
			}
		}, new Func1<Long, Observable<Long>>() {
			@Override
			public Observable<Long> call(Long along) {
				return Observable.just(along).delay(600, TimeUnit.MILLISECONDS);
			}
		}, new Func2<Long, Observable<Long>, Observable<Long>>() {
			@Override
			public Observable<Long> call(final Long aLong1,
					Observable<Long> longObservable) {
				return longObservable.map(new Func1<Long, Long>() {
					@Override
					public Long call(Long aLong2) {
						return aLong1 + aLong2;
					}
				});
			}
		}).subscribe(new Subscriber<Observable<Long>>() {
			@Override
			public void onCompleted() {
				System.out.println("groupJoin() complete");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("groupJoin() error:" + e.getMessage());
			}

			@Override
			public void onNext(Observable<Long> longObservable) {
				longObservable.subscribe(new Subscriber<Long>() {
					@Override
					public void onCompleted() {
						System.out.println("groupJoin()-onNext() complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("groupJoin()-onNext() error:"
								+ e.getMessage());
					}

					@Override
					public void onNext(Long aLong) {
						System.out.println("groupJoin()-onNext() onNext:"
								+ aLong);
					}
				});
			}
		});
	}

	/**
	 * switchOnNext操作符是把一组Observable转换成一个Observable，
	 * 转换规则为：对于这组Observable中的每一个Observable所产生的结果
	 * ，如果在同一个时间内存在两个或多个Observable提交的结果， 只取最后一个Observable提交的结果给订阅者
	 */
	private static void switchOnNext() {
		Observable<Observable<Long>> observable = Observable
				.timer(0, 500, TimeUnit.MILLISECONDS)
				.map(new Func1<Long, Observable<Long>>() {
					@Override
					public Observable<Long> call(Long aLong) {
						return Observable.timer(0, 200, TimeUnit.MILLISECONDS)
								.take(5);
					}
				}).take(2);

		Observable.switchOnNext(observable).subscribe(new Subscriber<Long>() {
			@Override
			public void onCompleted() {
				System.out.println("switchOnNext() complete");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("switchOnNext() error:" + e.getMessage());
			}

			@Override
			public void onNext(Long aLong) {
				System.out.println("switchOnNext() onNext():" + aLong);
			}
		});
	}

}