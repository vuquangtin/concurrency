package com.hystrix.async.spring.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

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
public class ExternalServiceTwo extends HystrixCommand<List<String>> /*
																	 * HystrixObservableCommand
																	 * <
																	 * List<String
																	 * >>
																	 */{

	protected ExternalServiceTwo() {
		super(HystrixCommandGroupKey.Factory.asKey(ExternalServiceTwo.class
				.getSimpleName()));

	}

	@Autowired
	RestTemplate restTemplate;

	// @Override
	// protected Observable<List<String>> construct() {
	// return Observable.create(new Observable.OnSubscribe<List<String>>() {
	// @Override
	// public void call(Subscriber<? super List<String>> observer) {
	// try {
	//
	// String[] response =
	// restTemplate.getForObject("http://localhost:8089/serviceTwo/states",
	// String[].class);
	//
	// List<String> stateList= Arrays.asList(response);
	//
	// if (!observer.isUnsubscribed()) {
	//
	// observer.onNext(stateList);
	//
	// observer.onCompleted();
	// }
	// } catch (Exception e) {
	// observer.onError(e);
	// }
	// }
	//
	// });
	//
	// }

	protected List<String> getFallback() {
		return new ArrayList<String>();
	}

	@Override
	protected List<String> run() throws Exception {
		String[] response = restTemplate.getForObject(
				"http://localhost:8089/serviceTwo/states", String[].class);
		List<String> stateList = Arrays.asList(response);
		return stateList;
	}
}
