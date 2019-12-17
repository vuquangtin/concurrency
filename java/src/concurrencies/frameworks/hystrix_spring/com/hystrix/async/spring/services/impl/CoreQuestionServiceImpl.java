package com.hystrix.async.spring.services.impl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.hystrix.async.spring.models.Question;
import com.hystrix.async.spring.services.CoreQuestionService;

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
public class CoreQuestionServiceImpl implements CoreQuestionService {
	@Value("${corequestionservice.url}")
	private String coreQuestionServiceUrl;

	@Override
	public String getServiceUrl() {
		return coreQuestionServiceUrl;
	}

	@Override
	public Flux<Question> findAllQuestions() {
		WebClient client = WebClient.builder().baseUrl(getServiceUrl()).build();

		WebClient.ResponseSpec responseSpec = client.get().uri("/question/all").retrieve();

		return responseSpec.bodyToFlux(Question.class);
	}
	@Override
	public Mono<Question> findQuestionById(String id) {
	    WebClient client = WebClient.builder()
	        .baseUrl(getServiceUrl())
	        .build();
	 
	    WebClient.ResponseSpec responseSpec = client.get()
	        .uri("/question/" + id)
	        .retrieve();
	 
	    return responseSpec.bodyToMono(Question.class);
	}
	@Override
	public Mono<Question> addNewQuestion(Question question) {
	    WebClient webClient = WebClient.builder()
	        .baseUrl(getServiceUrl())
	        .build();
	 
	    return webClient.post()
	        .uri("/question/add")
	        .body(BodyInserters.fromValue(question))
	        .retrieve()
	        .bodyToMono(Question.class);
	}
	@Override
	public Mono<Question> updateQuestion(String questionId, Question question) {
	    WebClient webClient = WebClient.builder()
	        .baseUrl(getServiceUrl())
	        .build();
	 
	    return webClient.put()
	        .uri("/question/" + questionId)
	        .body(BodyInserters.fromValue(question))
	        .retrieve()
	        .bodyToMono(Question.class);
	}
}