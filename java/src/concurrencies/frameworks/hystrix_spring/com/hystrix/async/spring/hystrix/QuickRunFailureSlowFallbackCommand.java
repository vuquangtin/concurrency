package com.hystrix.async.spring.hystrix;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

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
public class QuickRunFailureSlowFallbackCommand extends HystrixCommand<String> {
	static Logger logger = Logger
			.getLogger(QuickRunFailureSlowFallbackCommand.class.getName());
	private static final String COMMAND_GROUP_KEY = "SLOW_FALLBACK";

	public QuickRunFailureSlowFallbackCommand() {
		super(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP_KEY));
	}

	@Override
	protected String run() throws Exception {
		logger.info("Throwing exception in run() immediately");
		throw new RuntimeException("Throwing exception to initiate fallback");
	}

	@Override
	protected String getFallback() {
		int executionTime = 2500;
		logger.info("Executing fallback for "+executionTime+" ms");
		try {
			Thread.sleep(executionTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("Returning result from the fallback...");
		return COMMAND_GROUP_KEY;
	}
}