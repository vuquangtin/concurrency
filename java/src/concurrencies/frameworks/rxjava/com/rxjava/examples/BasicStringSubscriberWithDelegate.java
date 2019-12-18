package com.rxjava.examples;

import rx.Subscriber;

/**
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * 
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class BasicStringSubscriberWithDelegate extends Subscriber<String> {
	IResult result;

	public BasicStringSubscriberWithDelegate(IResult result) {
		this.result = result;

	}

	@Override
	public void onCompleted() {
		System.out.println("Done!!!");

	}

	@Override
	public void onError(Throwable e) {

	}

	@Override
	public void onNext(String s) {
		result.onNext(s);
	}
}