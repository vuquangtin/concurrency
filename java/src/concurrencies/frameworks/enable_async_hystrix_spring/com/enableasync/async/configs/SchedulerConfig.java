package com.enableasync.async.configs;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
// @Configuration
// @Component
public class SchedulerConfig {

	// @Bean("taskExecutor")
	public ConcurrentTaskScheduler taskScheduler() {
		ScheduledExecutorService localExecutor = Executors
				.newSingleThreadScheduledExecutor();
		return new ConcurrentTaskScheduler(localExecutor);
	}

}