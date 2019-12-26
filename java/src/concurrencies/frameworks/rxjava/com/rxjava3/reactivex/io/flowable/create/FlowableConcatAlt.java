package com.rxjava3.reactivex.io.flowable.create;

import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rxjava3.utils.Utils;

public class FlowableConcatAlt {

	private static final Logger log = LoggerFactory
			.getLogger(FlowableConcatAlt.class);

	public static void main(String[] args) {

		Flowable<String> flowableOne = Flowable.interval(1, TimeUnit.SECONDS)
				.take(4).map(l -> l + 1) // emit elapsed seconds
				.map(l -> "One: " + l + " seconds");

		Flowable<String> flowableTwo = Flowable
				.interval(300, TimeUnit.MILLISECONDS).map(l -> (l + 1) * 300) // emit
																				// elapsed
																				// milliseconds
				.map(l -> "Two: " + l + " milliseconds");

		// Keep flowable concat order
		Flowable.concat(flowableOne, flowableTwo).subscribe(
				w -> log.info("{}", w));

		Utils.hold(10000);

	}

}
