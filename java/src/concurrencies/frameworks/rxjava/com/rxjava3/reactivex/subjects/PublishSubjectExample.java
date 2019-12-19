package com.rxjava3.reactivex.subjects;

import io.reactivex.rxjava3.subjects.PublishSubject;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class PublishSubjectExample {

	public void marbleDiagram() {
		PublishSubject<String> subject = PublishSubject.create();
		subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
		subject.onNext("1");
		subject.onNext("3");
		subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
		subject.onNext("5");
		subject.onComplete();
	}

	public static void main(String[] args) {
		PublishSubjectExample demo = new PublishSubjectExample();
		demo.marbleDiagram();
	}
}