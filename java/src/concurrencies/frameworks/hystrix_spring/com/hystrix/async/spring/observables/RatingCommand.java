package com.hystrix.async.spring.observables;

import io.reactivex.netty.RxNetty;

import java.nio.charset.Charset;

import rx.Observable;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class RatingCommand extends HystrixObservableCommand<String> {
	private int id;

	public RatingCommand(int id) {
		super(HystrixCommandGroupKey.Factory.asKey("RatingGroup"));
		this.id = id;
	}

	@Override
	protected Observable<String> construct() {
		return RxNetty.createHttpGet("http://localhost:9001/" + id)
				.flatMap(response -> response.getContent())
				.map(data -> data.toString(Charset.defaultCharset()));
	}

	@Override
	protected Observable<String> resumeWithFallback() {
		return Observable.just("{\"id\":" + id + ",\"rating\":\"3\"}");
	}
}