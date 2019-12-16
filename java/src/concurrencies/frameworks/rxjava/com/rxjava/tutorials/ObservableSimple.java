package com.rxjava.tutorials;

import org.apache.log4j.Logger;

import rx.Observable;
import concurrencies.utilities.Log4jUtils;
import concurrencies.utilities.LogTest;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ObservableSimple {
	static Logger logger = Logger.getLogger(LogTest.class.getName());

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		Observable.from(new String[] { "url1", "url2", "url3" }).subscribe(
				url -> System.out.println(url));

		Observable.just(1, 2, 3).map(i -> 10 * i)
				.subscribe(i -> logger.debug("Result " + "" + i));

	}
}
