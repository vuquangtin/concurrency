package com.hystrix.async.spring.apps;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * The @EnableAsync annotation switches on Spring’s ability to run @Async
 * methods in a background thread pool. This class also customizes the Executor
 * by defining a new bean. Here, the method is named taskExecutor, since this is
 * the specific method name for which Spring searches. In our case, we want to
 * limit the number of concurrent threads to two and limit the size of the queue
 * to 500. There are many more things you can tune. If you do not define an
 * Executor bean, Spring creates a SimpleAsyncTaskExecutor and uses that.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 * @see https://spring.io/guides/gs/async-method/
 *
 */
@SpringBootApplication
//(scanBasePackages = {
//		"com.hystrix.async.spring.controllers",
//		"com.hystrix.async.spring.services" })
@EnableAsync
public class AsyncMethodApplication {

	public static void main(String[] args) {
		// close the application context to shut down the custom ExecutorService
		SpringApplication.run(AsyncMethodApplication.class, args);//.close();
	}

	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("GithubLookup-");
		executor.initialize();
		return executor;
	}
//	@Bean
//	public RestTemplate restTemplate(RestTemplateBuilder builder) {
//		return builder.build();
//	}
}
