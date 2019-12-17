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

import com.hystrix.async.spring.models.Result;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

@Slf4j
public class SlowRunAndFallbackExecutedOnCallingThreadCommand extends
		HystrixCommand<Result<String>> {
	static Logger logger = Logger.getLogger(SlowRunAndFallbackExecutedOnCallingThreadCommand.class.getName());
	private static final String COMMAND_GROUP_KEY = "SLOW_RUN_FALLBACK_IN_LAMBDA";

	public SlowRunAndFallbackExecutedOnCallingThreadCommand() {
		super(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP_KEY));
	}

	@Override
	protected Result<String> run() throws Exception {
		int executionTime = 2500;
		logger.info("About to execute run() for " + executionTime
				+ "ms which should be timed out");
		Thread.sleep(executionTime);
		logger.info("Should have timed out by this point!");
		return () -> "Should have timed out by this point!";
	}

	@Override
	protected Result<String> getFallback() {
		logger.info("Returning a lambda to execute the fallback");
		return new DeferredResult<>(() -> {
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			logger.info("Returning result from the fallback...");
			return COMMAND_GROUP_KEY;
		});
	}
}