package com.hystrix.async.spring.services.impl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

import com.hystrix.async.spring.models.Option;
import com.hystrix.async.spring.services.CoreOptionService;
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
public class CoreOptionServiceImpl implements CoreOptionService {
	@Value("${coreoptionservice.url}")
	private String coreOptionServiceUrl;

	@Override
	public String getServiceUrl() {
		return coreOptionServiceUrl;
	}

	@Override
	public Flux<Option> getOptions(String questionId) {
		WebClient client = WebClient.builder().baseUrl(getServiceUrl()).build();

		WebClient.ResponseSpec responseSpec = client.get().uri("/option?questionId=" + questionId).retrieve();

		return responseSpec.bodyToFlux(Option.class);
	}
}