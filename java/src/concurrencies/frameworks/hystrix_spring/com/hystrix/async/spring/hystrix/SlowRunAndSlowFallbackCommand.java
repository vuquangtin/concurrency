package com.hystrix.async.spring.hystrix;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

@Slf4j
public class SlowRunAndSlowFallbackCommand extends HystrixCommand<String> {
	static Logger logger = Logger.getLogger(SlowRunAndSlowFallbackCommand.class
			.getName());
	private static final String COMMAND_GROUP_KEY = "SLOW_BOTH";

	public SlowRunAndSlowFallbackCommand() {
		super(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP_KEY));
	}

	@Override
	protected String run() throws Exception {
		int executionTime = 2500;
		logger.info("About to execute run() for " + executionTime
				+ "ms which should be timed out");
		Thread.sleep(executionTime);
		logger.info("Should have timed out by this point!");
		return "Should have timed out by this point!";
	}

	@Override
	protected String getFallback() {
		int executionTime = 2500;
		logger.info("Executing slow fallback for " + executionTime);
		try {
			Thread.sleep(executionTime);
		} catch (InterruptedException e) {
			logger.error("Fallback was interrupted!");
		}
		logger.info("Returning result from the fallback...");
		return COMMAND_GROUP_KEY;
	}
}