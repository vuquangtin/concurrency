package com.rxjava3.reactivex.io.connects;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.observables.ConnectableObservable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Connectable {
	public static void main(String[] args) throws InterruptedException {
		connect();
		refCount();
		replay();

		Thread.sleep(Long.MAX_VALUE);
	}

	/**
	 * Connectable Observable: 是一种特殊的Observable对象，
	 * 并不是Subscrib的时候就发射数据，而是只有对其应用connect操作符的时候才开始发射数据，所以可以用来更灵活的控制数据发射的时机。
	 * 而Publish操作符就是用来将一个普通的Observable对象转化为一个Connectable Observable。
	 * 需要注意的是如果发射数据已经开始了再进行订阅只能接收以后发射的数据。
	 *
	 * @return Connectable Observable
	 */
	private static ConnectableObservable<Long> publish() {
		return Observable.interval(1, TimeUnit.SECONDS)
				.observeOn(Schedulers.newThread()).take(10).publish();
	}

	/**
	 * Connect操作符就是用来触发Connectable Observable发射数据的。
	 * 应用Connect操作符后会返回一个Subscription对象
	 * ，通过这个Subscription对象，我们可以调用其unsubscribe方法来终止数据的发射。
	 * 另外，如果还没有订阅者订阅的时候就应用Connect操作符也是可以使其开始发射数据的。
	 */
	private static void connect() {
		final ConnectableObservable<Long> connectableObservable = publish();
		final Consumer<Long> action2 = new Consumer<Long>() {
			@Override
			public void accept(Long o) {
				System.out.println("connect()  action2: " + o.toString());
			}

		};
		final Consumer<Long> action1 = new Consumer<Long>() {
			@Override
			public void accept(Long o) {
				System.out.println("connect()  action1: " + o.toString());
				if ((long) o == 5) {
					connectableObservable.subscribe(action2);
				}
			}
		};
		connectableObservable.subscribe(action1);
		connectableObservable.connect();
	}

	/**
	 * RefCount操作符就是将一个Connectable Observable 对象再重新转化为一个普通的Observable对象，
	 * 这时候如果由订阅者进行订阅将会触发数据的发射
	 */
	private static void refCount() {
		ConnectableObservable<Long> connectableObservable = publish();
		Disposable disposable = connectableObservable.refCount().subscribe(
				new Consumer<Long>() {
					@Override
					public void accept(Long aLong) {
						System.out.println("refCount() call:" + aLong);
					}
				});
	}

	/**
	 * Replay操作符返回一个Connectable Observable 对象并且可以缓存其发射过的数据，
	 * 这样即使有订阅者在其发射数据之后进行订阅也能收到其之前发射过的数据。
	 * 不过使用Replay操作符我们最好还是限定其缓存的大小，否则缓存的数据太多了可会占用很大的一块内存。 对缓存的控制可以从空间和时间两个方面来实现。
	 */
	private static void replay() {
		final ConnectableObservable<Long> countLimitObservable = Observable
				.interval(1, TimeUnit.SECONDS)
				.observeOn(Schedulers.newThread()).replay(2);

		ConnectableObservable<Long> timeLimitObservable = Observable
				.interval(1, TimeUnit.SECONDS)
				.observeOn(Schedulers.newThread()).replay(3, TimeUnit.SECONDS);

		// final ConnectableObservable<Long> observable = countLimitObservable;
		final ConnectableObservable<Long> observable = timeLimitObservable;

		final Consumer<Long> action2 = new Consumer<Long>() {
			@Override
			public void accept(Long o) {
				System.out.println("replay() action2:" + o.toString());
			}
		};
		Consumer<Long> action1 = new Consumer<Long>() {
			@Override
			public void accept(Long o) {
				System.out.println("replay() action1:" + o.toString());
				if ((long) o == 5) {
					observable.subscribe(action2);
				}
			}
		};
		observable.subscribe(action1);
		observable.connect();

	}

}
