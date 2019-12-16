package com.rxjava.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import rx.Subscriber;

/**
 * Created by achaub001c on 7/12/2016.
 */
public class QuoteSubscriberWithLatch extends Subscriber<QuoteResource> {
	private List<QuoteResource> results;
	private CountDownLatch countDownLatch;

	public QuoteSubscriberWithLatch(List<QuoteResource> result,
			CountDownLatch latch) {
		this.results = result;
		this.countDownLatch = latch;
	}

	public QuoteSubscriberWithLatch() {
		this.results = new ArrayList<>();
		this.countDownLatch = new CountDownLatch(1);
	}

	public List<QuoteResource> getResults() {
		return results;
	}

	public void setResults(List<QuoteResource> results) {
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
	public void onNext(QuoteResource quoteResource) {
		System.out.println("!!!! received onNext");
		results.add(quoteResource);
	}

	public void awaitTerminalEvent() {
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted", e);
		}
	}
}