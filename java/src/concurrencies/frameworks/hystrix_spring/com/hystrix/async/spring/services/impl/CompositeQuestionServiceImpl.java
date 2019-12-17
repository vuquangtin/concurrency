package com.hystrix.async.spring.services.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import com.hystrix.async.spring.models.CompositeQuestion;
import com.hystrix.async.spring.models.Question;
import com.hystrix.async.spring.services.CompositeQuestionService;
import com.hystrix.async.spring.services.CoreCategoryService;
import com.hystrix.async.spring.services.CoreOptionService;
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
public class CompositeQuestionServiceImpl implements CompositeQuestionService {

	@Autowired
	private CoreQuestionService coreQuestionService;

	@Autowired
	private CoreOptionService coreOptionService;

	@Autowired
	private CoreCategoryService coreCategoryService;

	@Override
	public Flux<CompositeQuestion> findAllQuestions() {
		Flux<Question> questionsFromCoreQuestionService = coreQuestionService
				.findAllQuestions();

		return questionsFromCoreQuestionService.flatMap(
				question -> coreCategoryService.findById(
						question.getCategoryId()).flatMap(
						category -> coreOptionService
								.getOptions(question.getId())
								.collectList()
								.map(options -> new CompositeQuestion(question
										.getId(), question.getDescription(),
										category, options)))).subscribeOn(
				Schedulers.elastic()).dematerialize();
	}

	@Override
	public Mono<CompositeQuestion> findQuestionById(String id) {
		Mono<Question> questionsFromCoreQuestionService = coreQuestionService
				.findQuestionById(id);

		return questionsFromCoreQuestionService.flatMap(
				question -> coreCategoryService.findById(
						question.getCategoryId()).flatMap(
						category -> coreOptionService
								.getOptions(question.getId())
								.collectList()
								.map(options -> new CompositeQuestion(question
										.getId(), question.getDescription(),
										category, options)))).subscribeOn(
				Schedulers.elastic()).dematerialize();
	}

	@Override
	public Mono<CompositeQuestion> addNewQuestion(Question question) {
		return coreCategoryService
				.findById(question.getCategoryId())
				.flatMap(
						category -> coreQuestionService
								.addNewQuestion(question).map(
										q -> new CompositeQuestion(q.getId(), q
												.getDescription(), category,
												Collections.emptyList())))
				.subscribeOn(Schedulers.elastic()).dematerialize();
	}

	@Override
	public Mono<CompositeQuestion> updateQuestion(String questionId,
			Question question) {
		return coreCategoryService
				.findById(question.getCategoryId())
				.flatMap(
						category -> coreQuestionService.updateQuestion(
								questionId, question).flatMap(
								q -> coreOptionService
										.getOptions(q.getId())
										.collectList()
										.map(options -> new CompositeQuestion(q
												.getId(), q.getDescription(),
												category, options))))
				.subscribeOn(Schedulers.elastic()).dematerialize();
	}
}
