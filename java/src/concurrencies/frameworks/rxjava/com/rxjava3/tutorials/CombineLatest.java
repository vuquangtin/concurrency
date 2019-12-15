package com.rxjava3.tutorials;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.BiFunction;

import java.util.concurrent.TimeUnit;

public class CombineLatest {

	public static void main(String[] args) throws InterruptedException {
		Observable<Long> ob1 = Observable.interval(0, 1, TimeUnit.SECONDS);
		Observable<String> ob2 = Observable.interval(0, 2, TimeUnit.SECONDS).map(i -> "No." + i);

		Observable.combineLatest(ob1, ob2, new BiFunction<Long, String, Element>() {

			@Override
			public Element apply(Long aLong, String s) throws Exception {
				return new Element(aLong, s);
			}
		}).subscribe(System.out::println);

		TimeUnit.SECONDS.sleep(10);
	}
}

class Element {
	private final long id;
	private final String ser;

	Element(long id, String ser) {
		this.id = id;
		this.ser = ser;
	}

	@Override
	public String toString() {
		return "Element{" + "id=" + id + ", ser='" + ser + '\'' + '}';
	}
}