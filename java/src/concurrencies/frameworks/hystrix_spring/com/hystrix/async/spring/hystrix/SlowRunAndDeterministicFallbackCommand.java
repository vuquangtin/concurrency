package com.hystrix.async.spring.hystrix;

import java.util.concurrent.ExecutionException;

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
public class SlowRunAndDeterministicFallbackCommand extends
		HystrixCommand<String> {
	static Logger logger = Logger
			.getLogger(SlowRunAndDeterministicFallbackCommand.class.getName());
	private static final String COMMAND_GROUP_KEY = "SLOW_RUN_DETERMINISTIC_FALLBACK";

	public SlowRunAndDeterministicFallbackCommand() {
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
		try {
			logger.info("Executing deterministic fallback by using a HystrixCommand");
			String result = new SlowRunAndImmediateFallbackCommand().queue()
					.get();
			logger.info("Returning result from the fallback...");
			return result;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return COMMAND_GROUP_KEY;
	}
}