package com.hystrix.async.spring.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import com.hystrix.async.spring.models.Category;
import com.hystrix.async.spring.services.CoreCategoryService;

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
public class CoreCategoryServiceImpl implements CoreCategoryService {
	@Value("${corecategoryservice.url}")
	
	private String coreCategoryServiceUrl;

	@Override
	public String getServiceUrl() {
		return coreCategoryServiceUrl;
	}

	@Override
	public Mono<Category> findById(String categoryId) {
		WebClient client = WebClient.builder().baseUrl(getServiceUrl()).build();

		WebClient.ResponseSpec responseSpec = client.get()
				.uri("/category/" + categoryId).retrieve();

		return responseSpec.bodyToMono(Category.class);
	}

}