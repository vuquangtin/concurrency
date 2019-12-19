package com.rxjava3.tutorials;

import java.util.concurrent.CountDownLatch;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class Demo {

	public static void main(String[] args) {
		// 信号量锁
		final CountDownLatch countDownLatch = new CountDownLatch(1);

		// 模拟的数据流, subData 如果为 null, 会引起 map 操作符报错
		Data data = new Data("");

		Observable
				.just(data)
				// map-1
				.map(new Function<Data, Data>() {
					public Data apply(Data data) throws Exception {
						return data;
					}
				})
				// subscribeOn-1
				.subscribeOn(Schedulers.newThread())
				// map-2
				.map(new Function<Data, Data>() {
					public Data apply(Data data) throws Exception {
						return data;
					}
				})
				// observeOn-1
				.observeOn(Schedulers.newThread())
				// map-3
				.map(new Function<Data, Object>() {
					public Object apply(Data data) throws Exception {
						return data.subData;
					}
				})
				// observeOn-2
				.observeOn(Schedulers.newThread())
				.flatMap(new Function<Object, ObservableSource<?>>() {
					public ObservableSource<?> apply(Object o) throws Exception {
						return Observable.create(
								new ObservableOnSubscribe<Object>() {
									public void subscribe(
											ObservableEmitter<Object> emitter)
											throws Exception {
										emitter.onNext(new Object());
										emitter.onComplete();
									}
								}).subscribeOn(Schedulers.newThread());
					}
				})
				// observeOn-3
				.observeOn(Schedulers.newThread())
				// map-4
				.map(new Function<Object, Object>() {
					public Object apply(Object o) throws Exception {
						return new String("123");
					}
				})
				// observeOn-4
				.observeOn(Schedulers.newThread())
				// subscribeOn-2
				.subscribeOn(Schedulers.newThread())

				.compose(new ObservableTransformer<Object, Object>() {
					public ObservableSource<Object> apply(
							Observable<Object> upstream) {
						return upstream.doOnError(new Consumer<Throwable>() {
							public void accept(Throwable throwable)
									throws Exception {

							}
						}).doFinally(new Action() {
							public void run() throws Exception {

							}
						});
					}
				})

				.doOnSubscribe(new Consumer<Disposable>() {
					public void accept(Disposable disposable) throws Exception {

					}
				})
				.doFinally(new Action() {
					public void run() throws Exception {

					}
				})
				.doOnDispose(new Action() {
					public void run() throws Exception {

					}
				})
				.doOnTerminate(new Action() {
					public void run() throws Exception {

					}
				})
				.doOnError(new Consumer<Throwable>() {
					public void accept(Throwable throwable) throws Exception {

					}
				})

				.map(new Function<Object, Object>() {
					public Object apply(Object o) throws Exception {
						if (1 == 1)
							throw new RuntimeException("");
						return o;
					}
				})

				.compose(new ObservableTransformer<Object, Object>() {
					public ObservableSource<Object> apply(
							Observable<Object> upstream) {
						return upstream.doOnError(new Consumer<Throwable>() {
							public void accept(Throwable throwable)
									throws Exception {

							}
						}).doFinally(new Action() {
							public void run() throws Exception {

							}
						});
					}
				})

				// subscribeOn-3
				.subscribeOn(Schedulers.newThread())
				.subscribe(new Observer<Object>() {
					public void onSubscribe(Disposable d) {

					}

					public void onNext(Object o) {

					}

					public void onError(Throwable e) {
						countDownLatch.countDown();
					}

					public void onComplete() {
						countDownLatch.countDown();
					}
				});

		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Over!");
	}

	public static class Data {

		private Object subData;

		public Data() {
		}

		public Data(Object subData) {
			this.subData = subData;
		}

		public Object getSubData() {
			return subData;
		}

		public void setSubData(Object subData) {
			this.subData = subData;
		}
	}
}