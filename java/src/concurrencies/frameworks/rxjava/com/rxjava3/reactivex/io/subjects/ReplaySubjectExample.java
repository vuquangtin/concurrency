package com.rxjava3.reactivex.io.subjects;

import org.apache.log4j.Logger;

import concurrencies.utilities.Log4jUtils;
import concurrencies.utilities.LogTest;
import io.reactivex.rxjava3.subjects.ReplaySubject;

/**
 * push toàn bộ giá trị đã nhận được trong suốt vòng đời vào Observer.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ReplaySubjectExample {
	static Logger logger = Logger.getLogger(LogTest.class.getName());

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		ReplaySubject<Integer> source = ReplaySubject.create();

		source.subscribe(Common.getFirstObserver()); // it will get 1, 2, 3, 4

		source.onNext(1);
		source.onNext(2);
		source.onNext(3);
		source.onNext(4);
		source.onComplete();

		/*
		 * it will emit 1, 2, 3, 4 for second observer also as we have used
		 * replay
		 */
		source.subscribe(Common.getSecondObserver());

	}

}
