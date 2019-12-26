package com.rxjava3.reactivex.io.schedulers;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import concurrencies.utilities.Log4jUtils;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class NewThreadExample {
	static Logger logger = Logger.getLogger(NewThreadExample.class.getName());

	private static void runAll(int i) {
		if (i <= 0) {
			logger.debug("---------------------------------sample0-------------------------------------------");
			sample0();
		}
		if (i <= 1) {
			logger.debug("---------------------------------sample1-------------------------------------------");
			sample1();
		}
		if (i <= 2) {
			logger.debug("---------------------------------sample11-------------------------------------------");
			sample11();
		}
		if (i <= 2) {
			logger.debug("---------------------------------sample2-------------------------------------------");
			sample2();
		}
		if (i <= 3) {
			logger.debug("---------------------------------sample3-------------------------------------------");
			sample3();
		}
		if (i <= 4) {
			logger.debug("---------------------------------sample4-------------------------------------------");
			sample4();
		}
		if (i <= 5) {
			logger.debug("---------------------------------sample5-------------------------------------------");
			sample5();
		}
		if (i <= 6) {
			logger.debug("---------------------------------sample6-------------------------------------------");
			sample6();
			logger.debug("---------------------------------sample6Fixed-------------------------------------------");
			sample6Fixed();
		}
		if (i <= 7) {
			logger.debug("---------------------------------sample7-------------------------------------------");
			sample7();
			logger.debug("---------------------------------sample7Fixed-------------------------------------------");
			sample7Fixed();
		}
		if (i <= 8) {
			logger.debug("---------------------------------sample8-------------------------------------------");
			sample8();
		}
		if (i <= 9) {
			logger.debug("---------------------------------sample9-------------------------------------------");
			sample9();
		}
		if (i <= 10) {
			logger.debug("---------------------------------sample10-------------------------------------------");
			sample10();
		}
	}

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		int i = 10;
		runAll(i);
	}

	//
	// from() を利用
	//
	private static void sample10() {
		Observable<Integer> observable = Observable.create(e -> {
			printCurrentThread("subscribe");
			e.onNext(1);
			e.onNext(2);
			e.onNext(3);
			e.onComplete();
		});

		Scheduler scheduler = Schedulers.from(Executors.newFixedThreadPool(4));
		observable.subscribeOn(scheduler).observeOn(scheduler).map(integer -> {
			printCurrentThread("map1-" + integer);
			return integer;
		}).observeOn(scheduler).map(integer -> {
			printCurrentThread("map2-" + integer);
			return integer;
		}).observeOn(scheduler).map(integer -> {
			printCurrentThread("map3-" + integer);
			return integer;
		}).observeOn(scheduler).subscribe(integer -> {
			printCurrentThread("onNext");
		});

		wait(500);
	}

	//
	// trampoline() を利用
	//
	private static void sample9() {
		Observable<Integer> observable = Observable.create(e -> {
			printCurrentThread("subscribe");
			e.onNext(1);
			e.onNext(2);
			e.onNext(3);
			e.onComplete();
		});

		observable.subscribeOn(Schedulers.trampoline()) // single
				.observeOn(Schedulers.trampoline()) // single
				.map(integer -> {
					printCurrentThread("map1-" + integer);
					return integer;
				}).observeOn(Schedulers.trampoline()) // single
				.map(integer -> {
					printCurrentThread("map2-" + integer);
					return integer;
				}).observeOn(Schedulers.trampoline()) // single
				.map(integer -> {
					printCurrentThread("map3-" + integer);
					return integer;
				}).observeOn(Schedulers.trampoline()) // single
				.subscribe(integer -> {
					printCurrentThread("onNext");
				});

		wait(500);
	}

	// //
	// single() を利用
	//
	private static void sample8() {
		Observable<Integer> observable = Observable.create(e -> {
			printCurrentThread("subscribe");
			e.onNext(1);
			e.onNext(2);
			e.onNext(3);
			e.onComplete();
		});

		observable.subscribeOn(Schedulers.single()) // single
				.observeOn(Schedulers.single()) // single
				.map(integer -> {
					printCurrentThread("map1-" + integer);
					return integer;
				}).observeOn(Schedulers.single()) // single
				.map(integer -> {
					printCurrentThread("map2-" + integer);
					return integer;
				}).observeOn(Schedulers.single()) // single
				.map(integer -> {
					printCurrentThread("map3-" + integer);
					return integer;
				}).observeOn(Schedulers.single()) // single
				.subscribe(integer -> {
					printCurrentThread("onNext");
				});

		wait(500);
	}

	// io() 破綻ケース
	//
	private static void sample7() {
		int times = 1000;
		for (int i = 0; i < times; i++) {
			mining(Schedulers.io());
		}

		wait(25000);
	}

	private static int cpuBoundedProcess(long times) {
		int sum = 0;
		for (int i = 0; i < times; i++) {
			for (int j = 0; j < times; j++) {
				sum += (i + j);
			}
		}
		return sum;
	}

	private static void mining(Scheduler scheduler) {
		Observable<Integer> observable = Observable.create(e -> {
			printCurrentThread("subscribe");
			cpuBoundedProcess(40000);
			e.onNext(1);
			e.onComplete();
		});

		observable.subscribeOn(scheduler).observeOn(scheduler)
				.subscribe(integer -> {
					printCurrentThread("completed: " + integer);
				});
	}

	private static void sample7Fixed() {
		int times = 1000;
		for (int i = 0; i < times; i++) {
			mining(Schedulers.computation());
		}

		wait(25000);
	}

	//
	// computation() 破綻ケース
	//
	private static void sample6() {
		// computation()
		int times = 50;
		for (int i = 0; i < times; i++) {
			fetchDataFromNetwork(Schedulers.computation());
		}

		wait(30000);
	}

	private static void fetchDataFromNetwork(Scheduler scheduler) {
		Observable<Integer> observable = Observable.create(e -> {
			printCurrentThread("subscribe");
			ioBoundedProcess(1000);
			e.onNext(1);
			e.onComplete();
		});

		observable.subscribeOn(scheduler).observeOn(scheduler)
				.subscribe(integer -> {
					printCurrentThread("completed: " + integer);
				});
	}

	private static void ioBoundedProcess(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ignore) {
		}
	}

	private static void sample6Fixed() {
		int times = 50;
		for (int i = 0; i < times; i++) {
			fetchDataFromNetwork(Schedulers.io());
		}

		wait(1000);
	}

	//
	// io() を利用
	//
	private static void sample5() {
		// 一つ目の Observable
		Observable<Integer> observable1 = Observable.create(e -> {
			printCurrentThread("subscribe");
			e.onNext(1);
			e.onNext(2);
			e.onNext(3);
			e.onComplete();
		});

		observable1.subscribeOn(Schedulers.io()) // io
				.observeOn(Schedulers.io()) // io
				.map(integer -> {
					printCurrentThread("map1-" + integer);
					return integer;
				}).observeOn(Schedulers.io()) // io
				.map(integer -> {
					printCurrentThread("map2-" + integer);
					return integer;
				}).observeOn(Schedulers.io()) // io
				.map(integer -> {
					printCurrentThread("map3-" + integer);
					return integer;
				}).observeOn(Schedulers.io()) // io
				.subscribe(integer -> {
					printCurrentThread("onNext");
				});

		wait(1000);

		// 二つ目の Observable
		Observable<Integer> observable2 = Observable.create(e -> {
			printCurrentThread("subscribe");
			e.onNext(1);
			e.onNext(2);
			e.onNext(3);
			e.onComplete();
		});

		observable2.subscribeOn(Schedulers.io()) // io
				.observeOn(Schedulers.io()) // io
				.map(integer -> {
					printCurrentThread("map1-" + integer);
					return integer;
				}).observeOn(Schedulers.io()) // io
				.map(integer -> {
					printCurrentThread("map2-" + integer);
					return integer;
				}).observeOn(Schedulers.io()) // io
				.map(integer -> {
					printCurrentThread("map3-" + integer);
					return integer;
				}).observeOn(Schedulers.io()) // io
				.subscribe(integer -> {
					printCurrentThread("onNext");
				});

		wait(1000);
	}

	//
	// computation() を利用
	//
	private static void sample4() {
		// initialization
		System.setProperty("rx2.computation-threads", "2");

		Observable<Integer> observable = Observable.create(e -> {
			printCurrentThread("subscribe");
			e.onNext(1);
			e.onNext(2);
			e.onNext(3);
			e.onComplete();
		});

		observable.subscribeOn(Schedulers.computation()) // computation
				.observeOn(Schedulers.computation()) // computation
				.map(integer -> {
					printCurrentThread("map1-" + integer);
					return integer;
				}).observeOn(Schedulers.computation()) // computation
				.map(integer -> {
					printCurrentThread("map2-" + integer);
					return integer;
				}).observeOn(Schedulers.computation()) // computation
				.map(integer -> {
					printCurrentThread("map3-" + integer);
					return integer;
				}).observeOn(Schedulers.computation()) // computation
				.subscribe(integer -> {
					printCurrentThread("onNext");
				});

		wait(1000);

		// finalization
		System.clearProperty("rx2.computation-threads");
	}

	//
	// newThread による割り当てを一部コメントアウト
	//
	private static void sample3() {
		Observable<Integer> observable = Observable.create(e -> {
			printCurrentThread("subscribe");
			e.onNext(1);
			e.onNext(2);
			e.onNext(3);
			e.onComplete();
		});

		observable.subscribeOn(Schedulers.newThread()) // newThread
				.observeOn(Schedulers.newThread()) // newThread
				.map(integer -> {
					printCurrentThread("map1-" + integer);
					return integer;
				})
				// .observeOn(Schedulers.newThread()) // newThread
				.map(integer -> {
					printCurrentThread("map2-" + integer);
					return integer;
				}).observeOn(Schedulers.newThread()) // newThread
				.map(integer -> {
					printCurrentThread("map3-" + integer);
					return integer;
				}).observeOn(Schedulers.newThread()) // newThread
				.subscribe(integer -> {
					printCurrentThread("onNext");
				});

		wait(500);
	}

	//
	// newThread による割り当て
	//
	private static void sample2() {
		Observable<Integer> observable = Observable.create(e -> {
			printCurrentThread("subscribe");
			e.onNext(1);
			e.onNext(2);
			e.onNext(3);
			e.onComplete();
		});

		observable.subscribeOn(Schedulers.newThread()) // newThread
				.observeOn(Schedulers.newThread()) // newThread
				.map(integer -> {
					printCurrentThread("map1-" + integer);
					return integer;
				}).observeOn(Schedulers.newThread()) // newThread
				.map(integer -> {
					printCurrentThread("map2-" + integer);
					return integer;
				}).observeOn(Schedulers.newThread()) // newThread
				.map(integer -> {
					printCurrentThread("map3-" + integer);
					return integer;
				}).observeOn(Schedulers.newThread()) // newThread
				.subscribe(integer -> {
					printCurrentThread("onNext");
				});

		wait(500);
	}

	private static void sample11() {
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.submit(NewThreadExample::sample0);
		es.shutdown();
	}

	public static void sample1() {
		Observable<Integer> observable = Observable.create(e -> {
			printCurrentThread("subscribe");
			e.onNext(1);
			e.onNext(2);
			e.onNext(3);
			e.onComplete();
		});

		observable.subscribeOn(Schedulers.newThread()) // newThread
				.observeOn(Schedulers.newThread()) // newThread
				.map(integer -> {
					printCurrentThread("map1-" + integer);
					return integer;
				}).observeOn(Schedulers.newThread()) // newThread
				.map(integer -> {
					printCurrentThread("map2-" + integer);
					return integer;
				}).observeOn(Schedulers.newThread()) // newThread
				.map(integer -> {
					printCurrentThread("map3-" + integer);
					return integer;
				}).observeOn(Schedulers.newThread()) // newThread
				.subscribe(integer -> {
					printCurrentThread("onNext");
				});

		wait(500);
	}

	public static void sample0() {
		Observable<Integer> observable = Observable.create(e -> {
			printCurrentThread("subscribe");
			e.onNext(1);
			e.onNext(2);
			e.onNext(3);
			e.onComplete();
		});

		observable.map(integer -> {
			printCurrentThread("map1-" + integer);
			return integer;
		}).map(integer -> {
			printCurrentThread("map2-" + integer);
			return integer;
		}).map(integer -> {
			printCurrentThread("map3-" + integer);
			return integer;
		}).subscribe(integer -> {
			printCurrentThread("onNext-" + integer);
		});
	}

	private static void wait(int milles) {
		try {
			// Schedulers の実行スレッドがデーモンスレッドなので完了を待たないと終了してしまう
			// 終了を待ついい方法が見つからない。newThread() 内の ExecutorService を見ることができないし。
			Thread.sleep(milles);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void printCurrentThread(String tag) {
		Thread current = Thread.currentThread();
		System.out.println(tag + ": " + current.getName());
	}
}