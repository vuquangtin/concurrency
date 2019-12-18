package com.rxjava3.utils;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class TaskUtils {

	/* 倒计时任务(s) */
	public static Observable<Integer> countdown(int time) {
		if (time < 0)
			time = 0;

		final int countTime = time;
		return Observable.interval(0, 1, TimeUnit.SECONDS)
				.subscribeOn(Schedulers.newThread())
				.map(new Function<Long, Integer>() {
					@Override
					public Integer apply(Long increaseTime) {
						return countTime - increaseTime.intValue();
					}
				}).take(countTime + 1);
	}

	/* 定时执行任务 */
	public static Observable<Long> interval(long delay, long minutes) {
		return Observable.interval(delay, minutes, TimeUnit.MINUTES)
				.subscribeOn(Schedulers.newThread());
	}

	/* 主线程执行任务 */
	public static Observable<Integer> runMainThread() {
		return Observable.just(1).observeOn(Schedulers.io());
	}

	/* 子线程执行任务 */
	public static Observable<Integer> runSubThread() {
		return Observable.just(1).observeOn(Schedulers.newThread());
	}

	/* 延时任务 */
	public static Observable<Long> delay2Do(int delaySeconds) {
		return Observable.timer(delaySeconds, TimeUnit.SECONDS).subscribeOn(
				Schedulers.newThread());
	}

	public static Observable<Long> delay2DoMainThread(int delaySeconds) {
		return Observable.timer(delaySeconds, TimeUnit.SECONDS)
				.subscribeOn(Schedulers.newThread()).observeOn(Schedulers.io());
	}
}