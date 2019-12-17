package com.hystrix.async.spring.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hystrix.async.spring.hystrix.GetBookCountHystrixCommand;
import com.hystrix.async.spring.services.BookStoreService;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

/**
 * @author stliu @ apache.org
 */
@RestController
@Slf4j
public class RestDemoController {
	static Logger logger = Logger.getLogger(RestDemoController.class.getName());
	private final BookStoreService bookStoreService;

	@Autowired
	public RestDemoController(BookStoreService bookStoreService) {
		this.bookStoreService = bookStoreService;
	}

	/**
	 * 这里提供了一个rest api GET /count-with-hystrix
	 *
	 * 并且提供了两个参数, 具体含义参见
	 * {@link BookStoreService#getBookCount(int, boolean, boolean)}
	 */

	@RequestMapping(value = "/count-with-hystrix", method = GET)
	public long countWithHystrix(
			@RequestParam(value = "sleep", defaultValue = "0") Integer sleep,
			@RequestParam(value = "randomsleep", defaultValue = "false") Boolean randomSleep,
			@RequestParam(value = "exception", defaultValue = "false") Boolean shouldThrowException) {

		GetBookCountHystrixCommand command = new GetBookCountHystrixCommand(
				bookStoreService, sleep, randomSleep, shouldThrowException);

		long count = command.execute();

		boolean successfulExecution = command.isSuccessfulExecution();
		boolean isCircuitBreakerOpen = command.isCircuitBreakerOpen();
		boolean isResponseFromFallback = command.isResponseFromFallback();
		boolean isResponseTimeout = command.isResponseTimedOut();
		int executionTimeInMilliseconds = command
				.getExecutionTimeInMilliseconds();

		logger.debug(String.format(
				"running into /count-with-hystrix api call, \n\t 这个command是否成功执行 : %s"
						+ " \n\t 当前断路器是否打开: %s " + "\n\t 返回值是否走的fallback: %s "
						+ "\n\t 请求是否超时: %s" + "\n\t 这个command的执行时间是 %s 毫秒",
				successfulExecution, isCircuitBreakerOpen,
				isResponseFromFallback, isResponseTimeout,
				executionTimeInMilliseconds));

		return count;
	}

}