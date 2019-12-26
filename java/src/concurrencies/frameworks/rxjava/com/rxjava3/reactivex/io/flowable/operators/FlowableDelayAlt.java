package com.rxjava3.reactivex.io.flowable.operators;

import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rxjava3.utils.Utils;
import static io.reactivex.rxjava3.core.Flowable.timer;

public class FlowableDelayAlt {

	private static final Logger log = LoggerFactory
			.getLogger(FlowableDelayAlt.class);

	public static void main(String[] args) {

		Flowable<String> flowable = Flowable.just("Alpha", "Beta", "Gamma",
				"Delta", "Epsilon", "Pi").delay(
				word -> timer(word.length(), TimeUnit.SECONDS));

		flowable.subscribe(
				w -> log.info("{} seconds for word {}", w.length(), w),
				ex -> log.error("Error {}", ex.getMessage()));

		Utils.hold(8000);

	}

}
