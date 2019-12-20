package com.rxjava3.reactivex.io.operators.all;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action2;
import rx.functions.Func0;
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

public class Aggregate {
	public static void main(String[] args) throws InterruptedException {
		concat();
		count();
		countLong();
		reduce();
		collect();
		toList();
		toSortList();
		toMap();
		toMultiMap();

		Thread.sleep(Long.MAX_VALUE);
	}

	/**
	 * Concat操作符将多个Observable结合成一个Observable并发射数据，并且严格按照先后顺序发射数据，
	 * 前一个Observable的数据没有发射完，是不能发射后面Observable的数据的。 有两个操作符跟它类似，但是有区别，分别是
	 * 1.startWith：仅仅是在前面插上一个数据。 2.merge:其发射的数据是无序的。
	 */
	private static void concat() {
		Observable<Integer> observable1 = Observable.just(1, 2, 3);
		Observable<Integer> observable2 = Observable.just(4, 5, 6);
		Observable<Integer> observable3 = Observable.just(7, 8, 9);
		Observable.concat(observable1, observable2, observable3).subscribe(
				SubscriberFactory.getIntegerSubscriber("concat()"));
	}

	/**
	 * Count操作符用来统计源Observable发射了多少个数据，最后将数目(整型值)给发射出来；
	 * 如果源Observable发射错误，则会将错误直接报出来；在源Observable没有终止前，count是不会发射统计数据的
	 * 只发最后的数目，中间发送数据的过程不走 onNext()
	 */
	private static void count() {
		Observable.interval(1, TimeUnit.SECONDS).take(10).count()
				.subscribe(SubscriberFactory.getIntegerSubscriber("count()"));
	}

	/**
	 * countLong操作符与count相似，不同的是最后返回的统计值是 Long 型
	 */
	private static void countLong() {
		Observable.interval(1, TimeUnit.SECONDS).take(3).countLong()
				.subscribe(SubscriberFactory.getLongSubscriber("countLong()"));
	}

	/**
	 * Reduce操作符应用一个函数接收Observable发射的数据和函数的计算结果作为下次计算的参数，输出最后的结果。
	 * 和scan操作符很类似，只是scan会输出每次计算的结果，而reduce只会输出最后的结果。
	 */
	private static void reduce() {
		Observable.just(1, 2, 3, 4, 5)
				.reduce(new Func2<Integer, Integer, Integer>() {
					@Override
					public Integer call(Integer integer, Integer integer2) {
						return integer + integer2;
					}
				})
				.subscribe(SubscriberFactory.getIntegerSubscriber("reduce()"));
	}

	/**
	 * collect用来将源Observable发射的数据给收集到一个数据结构里面， 需要使用两个参数： 一个产生收集数据结构的函数。
	 * 一个接收第一个函数产生的数据结构和源Observable发射的数据作为参数的函数。
	 */
	private static void collect() {
		Observable.interval(1, TimeUnit.SECONDS).take(10)
				.collect(new Func0<ArrayList<Long>>() {
					@Override
					public ArrayList<Long> call() {
						return new ArrayList<Long>();
					}
				}, new Action2<ArrayList<Long>, Long>() {
					@Override
					public void call(ArrayList<Long> arrayList, Long aLong) {
						arrayList.add(aLong);
					}
				}).subscribe(new Subscriber<ArrayList<Long>>() {
					@Override
					public void onCompleted() {
						System.out.println("collect() complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("collect() error:" + e.getMessage());
					}

					@Override
					public void onNext(ArrayList<Long> longs) {
						System.out.println("collect() onNext:"
								+ longs.toString());
					}
				});
	}

	/**
	 * toList，搜集所有源Observable发送的数据，转换为List
	 */
	private static void toList() {
		Observable.interval(1, TimeUnit.SECONDS).take(5).toList()
				.subscribe(new Subscriber<List<Long>>() {
					@Override
					public void onCompleted() {
						System.out.println("toList() complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("toList() error:" + e.getMessage());
					}

					@Override
					public void onNext(List<Long> longs) {
						System.out.println("toList() onNext:"
								+ longs.toString());
					}
				});
	}

	/**
	 * toSortList，搜集所有源Observable发送的数据，转换成一个排序后的List
	 */
	private static void toSortList() {
		Observable.just(1, 2, 0, 10, 4, 6, 5).toSortedList()
				.subscribe(new Subscriber<List<Integer>>() {
					@Override
					public void onCompleted() {
						System.out.println("toSortList() complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("toSortList() error:"
								+ e.getMessage());
					}

					@Override
					public void onNext(List<Integer> longs) {
						System.out.println("toSortList() onNext:"
								+ longs.toString());
					}
				});

	}

	/**
	 * 搜集所有源Observable发送的数据，转换成一个map
	 */
	private static void toMap() {
		Observable.just(1, 2, 3, 4).toMap(new Func1<Integer, String>() {
			@Override
			public String call(Integer integer) {
				// 生成对应的Key
				return integer + "";
			}
		}).subscribe(new Subscriber<Map<String, Integer>>() {
			@Override
			public void onCompleted() {
				System.out.println("toMap() complete");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("toMap() error:" + e.getMessage());
			}

			@Override
			public void onNext(Map<String, Integer> stringIntegerMap) {
				System.out.println("toMap() onNext:"
						+ stringIntegerMap.toString());
			}
		});
	}

	/**
	 * toMultiMap类似于toMap，不同的是，它生成的这个Map同时还是一个ArrayList
	 */
	private static void toMultiMap() {
		Observable.just(1, 2, 3, 4, 5).toMultimap(new Func1<Integer, String>() {
			@Override
			public String call(Integer integer) {
				return integer + "";
			}
		}).subscribe(new Subscriber<Map<String, Collection<Integer>>>() {
			@Override
			public void onCompleted() {
				System.out.println("toMultiMap() complete");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("toMultiMap() error:" + e.getMessage());
			}

			@Override
			public void onNext(
					Map<String, Collection<Integer>> stringCollectionMap) {
				System.out
						.println("toMultiMap() onNext:" + stringCollectionMap);
			}
		});
	}
}