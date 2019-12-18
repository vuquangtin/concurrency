package com.rxjava.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import rx.Subscriber;

/**
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * 
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class BasicStringSubscriberWithLatch extends Subscriber<String> {
	private List<String> results;
	private CountDownLatch countDownLatch;

	public BasicStringSubscriberWithLatch(List<String> result,
			CountDownLatch latch) {
		this.results = result;
		this.countDownLatch = latch;
	}

	public BasicStringSubscriberWithLatch() {
		this.results = new ArrayList<>();
		this.countDownLatch = new CountDownLatch(1);
	}

	public List<String> getResults() {
		return results;
	}

	public void setResults(List<String> results) {
		this.results = results;
	}

	@Override
	public void onCompleted() {
		countDownLatch.countDown();
	}

	@Override
	public void onError(Throwable e) {

	}

	@Override
	public void onNext(String s) {
		results.add(s);
	}

	public void awaitTerminalEvent() {
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted", e);
		}
	}
}