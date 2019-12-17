package com.hystrix.async.spring.services;

import reactor.core.publisher.Flux;

import com.hystrix.async.spring.models.Option;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public interface CoreOptionService {
	String getServiceUrl();
	Flux<Option> getOptions(String questionId);
}