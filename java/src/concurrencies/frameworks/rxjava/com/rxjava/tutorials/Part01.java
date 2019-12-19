package com.rxjava.tutorials;

import org.apache.log4j.Logger;

import concurrencies.utilities.Log4jUtils;
import concurrencies.utilities.LogTest;
import rx.Observable;
import rx.Subscriber;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Part01 {
	static Logger logger = Logger.getLogger(LogTest.class.getName());

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		Observable.from(new Integer[] { 1, 2, 3 }).subscribe(
				new Subscriber<Integer>() {
					public void onCompleted() {

					}

					public void onError(Throwable e) {

					}

					public void onNext(Integer integer) {
						logger.info("onNext " + String.valueOf(integer));
					}
				});

	}

}
