package com.rxjava3.reactivex.io.flowable.operators;

import io.reactivex.rxjava3.core.Flowable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlowableFlatMap {

	private static final Logger log = LoggerFactory
			.getLogger(FlowableFlatMap.class);

	public static void main(String[] args) {

		Flowable<Integer> flowable = Flowable.range(1, 10).flatMap(
				i -> (i % 2 == 0) ? Flowable.just(i) : Flowable.empty());

		flowable.subscribe(l -> log.info("Even numbers {}", l),
				ex -> log.error("Error {}", ex.getMessage()));

	}

}
