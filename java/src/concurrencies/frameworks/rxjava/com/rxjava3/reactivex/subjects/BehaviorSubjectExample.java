package com.rxjava3.reactivex.subjects;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class BehaviorSubjectExample {

	public void marbleDiagram() {
		BehaviorSubject<String> subject = BehaviorSubject.createDefault("6");
		// BehaviorSubject<String> subject = BehaviorSubject.create();
		subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
		subject.onNext("1");
		subject.onNext("3");
		subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
		subject.onNext("5");
		subject.subscribe(data -> System.out.println("Subscriber #3 => " + data));
		subject.subscribe(data -> System.out.println("Subscriber #3 => " + data));
		subject.subscribe(data -> System.out.println("Subscriber #3 => " + data));
		subject.onNext("9");
		subject.onComplete();
	}

	public static void main(String[] args) {
		BehaviorSubjectExample demo = new BehaviorSubjectExample();
		demo.marbleDiagram();
	}
}
