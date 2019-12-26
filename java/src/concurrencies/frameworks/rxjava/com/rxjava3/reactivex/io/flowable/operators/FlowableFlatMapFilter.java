package com.rxjava3.reactivex.io.flowable.operators;

import io.reactivex.rxjava3.core.Flowable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlowableFlatMapFilter {

	private static final Logger log = LoggerFactory
			.getLogger(FlowableFlatMapFilter.class);

	public static void main(String[] args) {

		Flowable<Integer> flowable = Flowable
				.just("521934/2342/FOXTROT", "21962/12112/78886/TANGO",
						"283242/4542/WHISKEY/2348562")
				.flatMap(s -> Flowable.fromArray(s.split("/")))
				.filter(s -> s.matches("[0-9]+")) // use regex to filter
													// integers
				.map(Integer::valueOf);

		flowable.subscribe(l -> log.info("{}", l),
				ex -> log.error("Error {}", ex.getMessage()));

	}

}
