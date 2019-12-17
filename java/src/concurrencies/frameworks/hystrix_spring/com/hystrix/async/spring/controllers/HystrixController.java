package com.hystrix.async.spring.controllers;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hystrix.async.spring.hystrix.QuickRunFailureSlowFallbackCommand;
import com.hystrix.async.spring.hystrix.SlowRunAndDeterministicFallbackCommand;
import com.hystrix.async.spring.hystrix.SlowRunAndFallbackExecutedOnCallingThreadCommand;
import com.hystrix.async.spring.hystrix.SlowRunAndImmediateFallbackCommand;
import com.hystrix.async.spring.hystrix.SlowRunAndSlowFallbackCommand;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

@RestController
@RequestMapping("/hystrix")
@Slf4j
public class HystrixController {
	static Logger logger = Logger.getLogger(HystrixController.class.getName());

	@RequestMapping(value = "/delay-in-fallback", method = RequestMethod.GET)
	@ResponseBody
	public String delayinFallback() throws ExecutionException,
			InterruptedException {
		logger.info("Entering controller...");
		String result = new QuickRunFailureSlowFallbackCommand().execute();
		logger.info("Exiting controller...");
		return result;
	}

	@RequestMapping(value = "/timeout-in-run", method = RequestMethod.GET)
	@ResponseBody
	public String timeoutInDoRun() throws ExecutionException,
			InterruptedException {
		logger.info("Entering controller...");
		String reksult = new SlowRunAndImmediateFallbackCommand().execute();
		logger.info("Exiting controller...");
		return reksult;
	}

	@RequestMapping(value = "/slow-run-and-slow-fallback", method = RequestMethod.GET)
	@ResponseBody
	public String slowExecutionAndFallback() throws ExecutionException,
			InterruptedException {
		logger.info("Entering controller...");
		String result = new SlowRunAndSlowFallbackCommand().execute();
		logger.info("Exiting controller...");
		return result;
	}

	@RequestMapping(value = "/slow-run-and-deterministic-fallback", method = RequestMethod.GET)
	@ResponseBody
	public String slowExecutionAndDeterministicFallback()
			throws ExecutionException, InterruptedException {
		logger.info("Entering controller...");
		String result = new SlowRunAndDeterministicFallbackCommand().execute();
		logger.info("Exiting controller...");
		return result;
	}

	@RequestMapping(value = "/slow-run-and-fallback-occurring-on-calling-thread", method = RequestMethod.GET)
	@ResponseBody
	public String slowExecutionAndFallbackOccurringOnCallingThread()
			throws ExecutionException, InterruptedException, TimeoutException {
		logger.info("Entering controller...");
		String result = new SlowRunAndFallbackExecutedOnCallingThreadCommand()
				.queue().get().get();
		logger.info("Exiting controller...");
		return result;
	}

}