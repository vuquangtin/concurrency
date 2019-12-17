package com.hystrix.async.spring.services.impl;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

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

@Component
@Slf4j
public class BookStoreServiceImpl implements BookStoreService {
	static Logger logger = Logger.getLogger(BookStoreServiceImpl.class
			.getName());
	private final AtomicLong counter = new AtomicLong(0);

	@Override
	public long getBookCount(int sleepInMillseconds, boolean randomSleep,
			boolean shouldThrowException) {
		// 如果设置了模拟延时, 那么这里通过sleep来模拟这个方法的执行时间
		if (randomSleep) {
			// 如果设置了随机延迟, 那么随机生成一个在0到500之间的数字作为延迟的毫秒数
			sleepInMillseconds = new Random().nextInt(500);
		}
		if (sleepInMillseconds > 0) {
			try {
				TimeUnit.MILLISECONDS.sleep(sleepInMillseconds);
			} catch (InterruptedException e) {
				logger.error("sleep in getBookCount was interrupted");
			}
		}
		// 如果设置了模拟异常, 那么这里抛出异常
		if (shouldThrowException) {
			throw new RuntimeException("throw exception as asked");
		}
		return counter.incrementAndGet();
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			int kk = new Random().nextInt(5000);
			System.out.println(kk);
		}
	}
}