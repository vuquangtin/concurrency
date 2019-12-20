package com.rxjava3.reactivex.io.connects;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import java.util.concurrent.TimeUnit;

import com.rxjava3.utils.Utils;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ConnectableObservableExample {

	public void marbleDiagram() {
		String[] dt = { "1", "3", "5" };
		Observable<String> balls = Observable.interval(100L, TimeUnit.MILLISECONDS).map(Long::intValue).map(i -> dt[i])
				.take(dt.length);
		ConnectableObservable<String> source = balls.publish();
		source.subscribe(data -> System.out.println("Subscriber #1 => " + data));
		source.subscribe(data -> System.out.println("Subscriber #2 => " + data));
		source.connect();

		Utils.sleep(250);
		source.subscribe(data -> System.out.println("Subscriber #3 => " + data));
		Utils.sleep(100);
	}

	public static void main(String[] args) {
		ConnectableObservableExample demo = new ConnectableObservableExample();
		demo.marbleDiagram();
	}
}
