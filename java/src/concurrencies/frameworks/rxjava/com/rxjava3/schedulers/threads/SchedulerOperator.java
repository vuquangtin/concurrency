package com.rxjava3.schedulers.threads;

import org.apache.log4j.Logger;

import concurrencies.utilities.Log4jUtils;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

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
public class SchedulerOperator {
	static Logger logger = Logger.getLogger(SchedulerOperator.class.getName());

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		SchedulerOperator instance = new SchedulerOperator();
		instance.schedule2();
	}

	public void schedule() {
		Single.just("")
				.doOnSuccess(s -> logger.info(s))
				.observeOn(Schedulers.newThread())
				.doOnSuccess(s -> logger.info(s))
				.subscribeOn(Schedulers.newThread())
				.doOnSuccess(s -> logger.info(s))
				.flatMap(
						s -> Single.fromCallable(() -> {
							logger.info(s);
							return s;
						}).subscribeOn(Schedulers.newThread())
								.observeOn(Schedulers.newThread())
								.doOnSuccess(ss -> logger.info(ss)))
				.doOnSuccess(s -> logger.info(s)).subscribe();
	}

	public void schedule2() {
		Single.just("")
				.observeOn(Schedulers.newThread())
				.doOnSuccess(s -> logger.info(s))
				.subscribeOn(Schedulers.newThread())
				.flatMap(
						s -> Single.fromCallable(() -> {
							logger.info(s);
							return s;
						}).subscribeOn(Schedulers.newThread())
								.doOnSuccess(ss -> logger.info(ss)))
				.doOnSuccess(s -> logger.info(s)).subscribe();
	}

}