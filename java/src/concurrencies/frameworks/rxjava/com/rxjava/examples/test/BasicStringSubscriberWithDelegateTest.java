package com.rxjava.examples.test;

import org.apache.log4j.Logger;

import rx.Observable;

import com.rxjava.examples.BasicStringSubscriberWithDelegate;
import com.rxjava.examples.IResult;

import concurrencies.utilities.Log4jUtils;

/***
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * 
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class BasicStringSubscriberWithDelegateTest {
	static Logger logger = Logger
			.getLogger(BasicStringSubscriberWithDelegateTest.class.getName());

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		Observable<String> o = Observable.just("1", "2", "3");

		BasicStringSubscriberWithDelegate delegate = new BasicStringSubscriberWithDelegate(
				new IResult() {

					@Override
					public void onNext(String s) {
						logger.debug(s);

					}
				});
		o.subscribe(delegate);
	}
}
