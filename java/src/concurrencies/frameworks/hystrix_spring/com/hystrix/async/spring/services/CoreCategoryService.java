package com.hystrix.async.spring.services;

import reactor.core.publisher.Mono;

import com.hystrix.async.spring.models.Category;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public interface CoreCategoryService {
	String getServiceUrl();
	Mono<Category> findById(String categoryId);
}