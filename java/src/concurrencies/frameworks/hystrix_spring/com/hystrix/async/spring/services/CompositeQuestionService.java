package com.hystrix.async.spring.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.hystrix.async.spring.models.CompositeQuestion;
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
public interface CompositeQuestionService {
	Flux<CompositeQuestion> findAllQuestions();
	Mono<CompositeQuestion> findQuestionById(String id);
	Mono<CompositeQuestion> addNewQuestion(Question question);
	Mono<CompositeQuestion> updateQuestion(String questionId, Question question);
}