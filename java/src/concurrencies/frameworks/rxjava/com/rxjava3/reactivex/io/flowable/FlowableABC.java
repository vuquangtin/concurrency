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
public class FlowableABC {

	static Logger logger = Logger.getLogger(LogTest.class.getName());

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		List<String> words = Arrays.asList("alpha", "beta", "gamma", "delta",
				"epsilon");

		Flowable<String> result = Flowable
				.fromIterable(words)
				.flatMap(word -> Flowable.fromArray(word.split("")))
				.distinct()
				.sorted()
				.zipWith(
						Flowable.range(1, Integer.MAX_VALUE),
						(string, count) -> String.format("%2d. %s", count,
								string));

		result.subscribe(l -> logger.info(l));

	}

}