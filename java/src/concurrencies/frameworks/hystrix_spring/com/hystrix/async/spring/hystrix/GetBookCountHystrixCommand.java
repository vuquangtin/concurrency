package com.hystrix.async.spring.hystrix;

import com.hystrix.async.spring.services.BookStoreService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */


public class GetBookCountHystrixCommand extends HystrixCommand<Long> {

	private static final Setter commandSetter = Setter
			.withGroupKey(
					HystrixCommandGroupKey.Factory
							.asKey("bookStoreCommandGroup"))
			.andCommandKey(
					HystrixCommandKey.Factory.asKey("bookStoreServiceGetCount"));

	private final BookStoreService bookStoreService;
	private final int sleepInMillseconds;
	private final boolean randomSleep;
	private final boolean shouldThrowException;

	public GetBookCountHystrixCommand(BookStoreService bookStoreService,
			int sleepInMillseconds, boolean randomSleep,
			boolean shouldThrowException) {
		super(commandSetter);
		this.bookStoreService = bookStoreService;
		this.sleepInMillseconds = sleepInMillseconds;
		this.randomSleep = randomSleep;
		this.shouldThrowException = shouldThrowException;
	}

	@Override
	protected Long run() throws Exception {
		return bookStoreService.getBookCount(sleepInMillseconds, randomSleep,
				shouldThrowException);
	}

	@Override
	protected Long getFallback() {
		return -1L;
	}
}