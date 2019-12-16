package com.rxjava.examples;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import rx.Subscriber;

/**
 * Created by achaub001c on 7/12/2016.
 */
public class BasicStringSubscriber extends Subscriber<String> {
	private List<String> results;
	private CountDownLatch latch;

	public BasicStringSubscriber(List<String> result, CountDownLatch latch) {
		this.results = result;
		this.latch = latch;
	}

	@Override
	public void onCompleted() {
		System.out.println("Done!!!");
		latch.countDown();
	}

	@Override
	public void onError(Throwable e) {

	}

	@Override
	public void onNext(String s) {
		results.add(s);
	}
}