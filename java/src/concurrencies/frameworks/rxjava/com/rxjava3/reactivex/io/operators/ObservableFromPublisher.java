package com.rxjava3.reactivex.io.operators;


import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import io.reactivex.rxjava3.core.Observable;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ObservableFromPublisher {

	public void basic() {
		Publisher<String> publisher = (Subscriber<? super String> s) -> {
			s.onNext("Hello Observable.fromPublisher()");
			s.onComplete();
		};
		Observable<String> source = Observable.fromPublisher(publisher);
		source.subscribe(System.out::println);
	}

	public void withoutLambda() {
		Publisher<String> publisher = new Publisher<String>() {
			@Override
			public void subscribe(Subscriber<? super String> s) {
				s.onNext("Hello Observable.fromPublisher()");
				s.onComplete();
			}
		};
		Observable<String> source = Observable.fromPublisher(publisher);
		source.subscribe(System.out::println);
	}

	public static void main(String[] args) {
		ObservableFromPublisher demo = new ObservableFromPublisher();
		demo.basic();
		demo.withoutLambda();
	}
}
