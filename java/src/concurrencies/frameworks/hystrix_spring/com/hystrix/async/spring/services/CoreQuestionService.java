package com.hystrix.async.spring.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.hystrix.async.spring.models.Question;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public interface CoreQuestionService {
	 String getServiceUrl();
	 Flux<Question> findAllQuestions();
	 Mono<Question> findQuestionById(String id);
	 Mono<Question> addNewQuestion(Question question);
	 Mono<Question> updateQuestion(String questionId, Question question);
}