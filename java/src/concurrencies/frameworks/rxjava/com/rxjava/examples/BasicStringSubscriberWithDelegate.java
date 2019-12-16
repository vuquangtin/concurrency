package com.rxjava.examples;

import rx.Subscriber;

/**
 * Created by achaub001c on 7/12/2016.
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