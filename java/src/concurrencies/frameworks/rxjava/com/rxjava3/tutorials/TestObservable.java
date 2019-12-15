package com.rxjava3.tutorials;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public final class TestObservable {

	public static Observable<Integer> timeIncOb = Observable.create(emitter -> {
		int totalTime = 0;
		for (int i = 0; i < 20; i++) {
			int sleepTime = i * 10;
			TimeUnit.MILLISECONDS.sleep(sleepTime);
			totalTime += sleepTime;
			emitter.onNext(totalTime);
		}
		emitter.onComplete();
	});

	public static Observable<Long> intervalOb = Observable.interval(0, 1, TimeUnit.SECONDS).take(10);

}