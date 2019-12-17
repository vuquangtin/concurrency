package com.hystrix.async.spring.hystrix;

import java.util.function.Supplier;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

import com.hystrix.async.spring.models.Result;

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

@Slf4j
public class DeferredResult<T> implements Result<T> {
	static Logger logger = Logger.getLogger(LogTest.class.getName());
	private final Supplier<T> resultProvider;

	DeferredResult(Supplier<T> resultProvider) {
		this.resultProvider = resultProvider;
	}

	@Override
	public T get() {
		logger.info("Executing fallback by using a Lamda");
		return resultProvider.get();
	}
}