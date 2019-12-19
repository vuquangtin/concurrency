package com.rxjava3.reactivex.io.intro;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ObservableCreateExample {

	public void basic() {
		Observable<Integer> source = Observable.create((ObservableEmitter<Integer> emitter) -> {
			emitter.onNext(100);
			emitter.onNext(200);
			emitter.onNext(300);
			emitter.onComplete();
		});
		source.subscribe(System.out::println);
	}

	public void notSubscribed() {
		Observable<Integer> source = Observable.create((ObservableEmitter<Integer> emitter) -> {
			emitter.onNext(100);
			emitter.onNext(200);
			emitter.onNext(300);
			emitter.onComplete();
		});
	}

	public void subscribeLambda() {
		Observable<Integer> source = Observable.create((ObservableEmitter<Integer> emitter) -> {
			emitter.onNext(100);
			emitter.onNext(200);
			emitter.onNext(300);
			emitter.onComplete();
		});
		source.subscribe(data -> System.out.println("Result : " + data));
	}

	public void subscribeAnonymously() {
		Observable<Integer> source = Observable.create((ObservableEmitter<Integer> emitter) -> {
			emitter.onNext(100);
			emitter.onNext(200);
			emitter.onNext(300);
			emitter.onComplete();
		});

		source.subscribe(new Consumer<Integer>() {
			@Override
			public void accept(Integer integer) throws Exception {
				System.out.println("Result : " + integer);
			}
		});
	}

	public static void main(String[] args) {
		ObservableCreateExample demo = new ObservableCreateExample();
		demo.basic();
		demo.notSubscribed();
		demo.subscribeLambda();
		demo.subscribeAnonymously();
	}
}
