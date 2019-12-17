package com.hystrix.async.spring.hystrix;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

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
public class SlowRunAndImmediateFallbackCommand extends HystrixCommand<String> {
	static Logger logger = Logger.getLogger(LogTest.class.getName());
	private static final String COMMAND_GROUP_KEY = "SLOW_RUN_IMMEDIATE_FALLBACK";

	public SlowRunAndImmediateFallbackCommand() {
		super(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP_KEY));
	}

	@Override
	protected String run() throws Exception {
		int executionTime = 2500;
		logger.info("About to execute run() for " + executionTime
				+ "ms which should be timed out");
		Thread.sleep(executionTime);
		logger.info("Should have timed out by this point!");
		return COMMAND_GROUP_KEY;
	}

	@Override
	protected String getFallback() {
		logger.info("Executing fallback immediately");
		return COMMAND_GROUP_KEY;
	}
}