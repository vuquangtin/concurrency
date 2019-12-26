package com.rxjava3.reactivex.io.flowable;

import io.reactivex.rxjava3.core.Flowable;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

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
public class FlowableSwitchCase {

	static Logger log = Logger.getLogger(LogTest.class.getName());

	public static void main(String[] args) {
		log = Log4jUtils.initLog4j();
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4);

		Flowable<String> switchCase = Flowable.fromIterable(numbers).flatMap(
				number -> {
					switch (number) {
					case 1:
						return Flowable.just("Case 1 - ".concat(String
								.valueOf(number)));
					case 2:
						return Flowable.just("Case 2 - ".concat(String
								.valueOf(number)));
					case 3:
						return Flowable.just("Case 3 - ".concat(String
								.valueOf(number)));
					default:
						return Flowable.empty();
					}
				});

		switchCase.subscribe(log::info);

	}

}
