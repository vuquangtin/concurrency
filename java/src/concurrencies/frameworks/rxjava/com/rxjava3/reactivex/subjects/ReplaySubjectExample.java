package com.rxjava3.reactivex.subjects;

import io.reactivex.rxjava3.subjects.ReplaySubject;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ReplaySubjectExample {

	public void marbleDiagram() {
		ReplaySubject<String> subject = ReplaySubject.create();
		subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
		subject.onNext("1");
		subject.onNext("3");
		subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
		subject.onNext("5");
	}

	public static void main(String[] args) {
		ReplaySubjectExample demo = new ReplaySubjectExample();
		demo.marbleDiagram();
	}
}
