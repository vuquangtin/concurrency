package com.rxjava3.reactivex.io.operators.all;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Transforming {

	public static void main(String[] args) {
		map();
		flatMap();
		flatMapIterable();
		concatMap();
		switchMap();
		scan();
		groupBy();
		buffer();
		window();
		cast();
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * map 操作符，对数据进行简单的变换
	 */
	private static void map() {
		Observable.just(1, 2, 3, 4, 5, 6).map(new Func1<Integer, Integer>() {
			@Override
			public Integer call(Integer integer) {
				return integer * 10;
			}
		}).subscribe(new Subscriber<Integer>() {
			@Override
			public void onCompleted() {
				System.out.println("map() complete");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("map() error:" + e.getMessage());
			}

			@Override
			public void onNext(Integer integer) {
				System.out.println("map() onNext:" + integer);
			}
		});
	}

	/**
	 * flatMap 操作符,把Observable产生的结果转换成多个Observable，
	 * 然后把这多个Observable“扁平化”成一个Observable，并依次提交产生的结果给订阅者
	 * 在合并Observable结果时，有可能存在交叉的情况
	 */
	private static void flatMap() {
		Observable.just(1, 2, 3, 4, 5)
				.flatMap(new Func1<Integer, Observable<?>>() {
					@Override
					public Observable<?> call(Integer integer) {
						return Observable.just("flatMap后：" + integer * 10);
					}
				}).subscribe(new Subscriber<Object>() {
					@Override
					public void onCompleted() {
						System.out.println("flatMap() complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("flatMap() error:" + e.getMessage());
					}

					@Override
					public void onNext(Object o) {
						System.out.println("flatMap() onNext:" + o.toString());
					}
				});
	}

	/**
	 * FlatMapIterable 操作符，和FlatMap基本相同，不同之处为其转化的多个Observable是使用Iterable作为源数据的。
	 */
	private static void flatMapIterable() {
		Observable.just(2, 3, 4, 5)
				.flatMapIterable(new Func1<Integer, Iterable<?>>() {
					@Override
					public Iterable<?> call(Integer integer) {
						System.out.println();
						List<Integer> arrayList = new ArrayList<Integer>();
						for (int i = 1; i < integer; i++) {
							arrayList.add(i);
						}
						return arrayList;
					}
				}).subscribe(new Subscriber<Object>() {
					@Override
					public void onCompleted() {
						System.out.println("flatMapIterable() complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("flatMapIterable() error:"
								+ e.getMessage());
					}

					@Override
					public void onNext(Object o) {
						System.out.println("flatMapIterable() onNext:"
								+ o.toString());
					}
				});
	}

	/**
	 * cancatMap操作符, 与flatMap操作符类似，不同之处是 保证产生结果的顺序性
	 */

	private static void concatMap() {
		Observable.just(1, 2, 3, 4, 5)
				.flatMap(new Func1<Integer, Observable<?>>() {
					@Override
					public Observable<?> call(Integer integer) {
						return Observable.just("concatMap后：" + integer * 10);
					}
				}).subscribe(new Subscriber<Object>() {
					@Override
					public void onCompleted() {
						System.out.println("concatMap() complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("concatMap() error:"
								+ e.getMessage());
					}

					@Override
					public void onNext(Object o) {
						System.out.println("concatMap() onNext:" + o.toString());
					}
				});
	}

	/**
	 * switchMap 操作符，flatMap操作符类似，不同的是switchMap操作符会保存最新的Observable产生的结果而舍弃旧的结果，
	 * 比如源Observable产生A、B、C 三个结果，通过switchMap的映射规则，映射后应该会产生A1、A2、B1、B2、C1、C2，
	 * 但是在产生B2的同时，C1已经产生了，这样最后的结果就变成A1、A2、B1、C1、C2， B2被舍弃掉了
	 */
	private static void switchMap() {
		Observable.just(1, 2, 3, 4, 5, 6)
				.switchMap(new Func1<Integer, Observable<?>>() {
					@Override
					public Observable<?> call(Integer integer) {
						return Observable.just("switchMap后：" + integer).delay(
								100, TimeUnit.MILLISECONDS);
					}
				}).subscribe(new Subscriber<Object>() {
					@Override
					public void onCompleted() {
						System.out.println("switchMap() complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("switchMap() error:"
								+ e.getMessage());
					}

					@Override
					public void onNext(Object o) {
						System.out.println("switchMap() onNext:" + o.toString());
					}
				});
	}

	/**
	 * scan操作符，通过遍历源Observable产生的结果，依次对每一个结果项按照指定规则进行运算，
	 * 计算后的结果作为下一个迭代项参数，每一次迭代项都会把计算结果输出给订阅者
	 */
	private static void scan() {
		Observable.just(1, 2, 3, 4, 5, 6)
				.scan(new Func2<Integer, Integer, Integer>() {
					@Override
					public Integer call(Integer sum, Integer item) {
						System.out.println("上次计算的结果：" + sum
								+ " ，源Observable发出的数据：" + item);
						return sum + item;
					}
				}).subscribe(new Subscriber<Integer>() {
					@Override
					public void onCompleted() {
						System.out.println("scan() complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("scan() error:" + e.getMessage());
					}

					@Override
					public void onNext(Integer integer) {
						System.out.println("scan() onNext:" + integer);
					}
				});
	}

	/**
	 * groupBy操作符,对源Observable产生的结果进行分组，形成一个类型为GroupedObservable的结果集，
	 * GroupedObservable中存在一个方法为getKey(),可以通过该方法获取结果集的Key值（类似于HashMap的key)。
	 * 值得注意的是，由于结果集中的GroupedObservable是把分组结果缓存起来，
	 * 如果对每一个GroupedObservable不进行处理（既不订阅执行也不对其进行别的操作符运算），就有可能出现内存泄露
	 */
	private static void groupBy() {
		Observable
				.just(1, 2, 3, 4, 5, 6)
				.groupBy(new Func1<Integer, Integer>() {
					@Override
					public Integer call(Integer integer) {
						// 按照 key 为 0，1分组
						return integer % 2;
					}
				})
				.subscribe(
						new Subscriber<GroupedObservable<Integer, Integer>>() {
							@Override
							public void onCompleted() {
								System.out.println("groupBy() complete");
							}

							@Override
							public void onError(Throwable e) {
								System.out.println("groupBy() error:"
										+ e.getMessage());
							}

							@Override
							public void onNext(
									final GroupedObservable<Integer, Integer> integerIntegerGroupedObservable) {
								integerIntegerGroupedObservable
										.subscribe(new Action1<Integer>() {
											@Override
											public void call(Integer integer) {
												System.out.println("groupBy() key:"
														+ integerIntegerGroupedObservable
																.getKey()
														+ " "
														+ "value:" + integer);
											}
										});
							}
						});
	}

	/**
	 * Buffer操作符,要做的事情就是将数据安装规定的大小做一下缓存，然后将缓存的数据作为一个List集合发射出去 skip
	 * 参数可选，指定每次发射一个集合需要跳过几个数据 可以根据时间或者数目分组
	 */
	private static void buffer() {
		Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
		// .buffer(2)
				.buffer(2, 3).subscribe(new Subscriber<List<Integer>>() {
					@Override
					public void onCompleted() {
						System.out.println("buffer() complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("buffer() error:" + e.getMessage());
					}

					@Override
					public void onNext(List<Integer> integers) {
						System.out.println("buffer() onNext:"
								+ integers.toString());
					}
				});
	}

	/**
	 * Window操作符,类似于buffer，不同之处在于window发射的是一些小的Observable对象，
	 * 由这些小的Observable对象来发射内部包含的数据。 同buffer一样，window不仅可以通过数目来分组还可以通过时间等规则来分组
	 */
	private static void window() {
		Observable.interval(1, TimeUnit.SECONDS).take(12)
				.window(3, TimeUnit.SECONDS)
				.subscribe(new Subscriber<Observable<Long>>() {
					@Override
					public void onCompleted() {
						System.out.println("window() complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("window() error:" + e.getMessage());
					}

					@Override
					public void onNext(Observable<Long> longObservable) {
						System.out.println("begin....");
						longObservable.subscribe(new Action1<Long>() {
							@Override
							public void call(Long aLong) {
								System.out.println("window() next:" + aLong);
							}
						});
					}
				});
	}

	/**
	 * Cast操作符，将Observable发射的数据强制转化为另外一种类型，属于Map的一种具体的实现
	 */
	private static void cast() {
		Observable.just(1, 2, 3, 4, 5, 6).cast(String.class)
				.subscribe(new Subscriber<String>() {
					@Override
					public void onCompleted() {
						System.out.println("cast() complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("cast() error:" + e.getMessage());
					}

					@Override
					public void onNext(String s) {
						System.out.println("cast() onNext:" + s);
					}
				});
	}
}