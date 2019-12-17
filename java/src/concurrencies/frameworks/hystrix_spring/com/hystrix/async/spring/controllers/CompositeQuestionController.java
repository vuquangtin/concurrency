package com.hystrix.async.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.hystrix.async.spring.models.CompositeQuestion;
import com.hystrix.async.spring.models.Question;
import com.hystrix.async.spring.services.CompositeQuestionService;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

@RestController
@RequestMapping("/question")
public class CompositeQuestionController {

	@Autowired
	private CompositeQuestionService compositeQuestionService;

	@GetMapping("/all")
	public Flux<CompositeQuestion> findAllQuestions() {
		return compositeQuestionService.findAllQuestions();
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<CompositeQuestion>> findQuestionById(
			@PathVariable(value = "id") String id) {
		return compositeQuestionService.findQuestionById(id)
				.map(compositeQuestion -> ResponseEntity.ok(compositeQuestion))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping("/add")
	public Mono<ResponseEntity<CompositeQuestion>> addNewQuestion(
			@RequestBody Question question) {
		return compositeQuestionService.addNewQuestion(question)
				.map(compositeQuestion -> ResponseEntity.ok(compositeQuestion))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<CompositeQuestion>> updateQuestion(
			@PathVariable(value = "id") String questionId,
			@RequestBody Question question) {
		return compositeQuestionService.updateQuestion(questionId, question)
				.map(compositeQuestion -> ResponseEntity.ok(compositeQuestion))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
}