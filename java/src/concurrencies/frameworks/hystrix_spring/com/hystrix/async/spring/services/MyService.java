package com.hystrix.async.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */


@Service
public class MyService {

	@Autowired
	ExternalServiceOne externalServiceOne;

	@Autowired
	ExternalServiceTwo externalServiceTwo;

	public List<String> getStatesList() throws InterruptedException,
			ExecutionException {

		List<String> states = new ArrayList<String>();
		Observable
				.merge(externalServiceOne.observe()
						.subscribeOn(Schedulers.io()),
						externalServiceTwo.observe().subscribeOn(
								Schedulers.io())).toBlocking()
				.subscribe(new Action1<List<String>>() {

					@Override
					public void call(List<String> arg0) {
						states.addAll(arg0);

					}
				});
		return states;

	}
}