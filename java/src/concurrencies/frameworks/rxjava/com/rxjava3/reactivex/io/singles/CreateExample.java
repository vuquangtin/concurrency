package com.rxjava3.reactivex.io.singles;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import org.apache.log4j.Logger;

import concurrencies.utilities.Log4jUtils;
import concurrencies.utilities.LogTest;

/**
 * 
 We can use Flowable.create(…) to implement the emissions of events by calling
 * onNext(val), onComplete(), onError(throwable)
 * 
 * When subscribing to the Observable / Flowable with flowable.subscribe(…) the
 * lambda code inside create(…) gets executed. Flowable.subscribe(…) can take 3
 * handlers for each type of event - onNext, onError and onCompleted.
 * 
 * When using Observable.create(…) you need to be aware of backpressure and that
 * Observables created with ‘create’ are not BackPressure aware
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class CreateExample {
	static Logger logger = Logger.getLogger(LogTest.class.getName());

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		Observable<Integer> stream = Observable.create(subscriber -> {
			logger.info("Started emitting");

			logger.info("Emitting 1st");
			subscriber.onNext(1);

			logger.info("Emitting 2nd");
			subscriber.onNext(2);

			//subscriber.onCompleted();
		});

		// Flowable version same Observable but with a BackpressureStrategy
		// that will be discussed separately.
		Flowable<Integer> streamFlowable = Flowable.create(subscriber -> {
			logger.info("Started emitting");

			logger.info("Emitting 1st");
			subscriber.onNext(1);

			logger.info("Emitting 2nd");
			subscriber.onNext(2);

			subscriber.onComplete();
		}, BackpressureStrategy.MISSING);

		stream.subscribeOn(Schedulers.io()).subscribe(val -> logger.info("Subscriber received: " + val),
				err -> logger.error("Subscriber received error", err),
				() -> logger.info("Subscriber got Completed event"));

		streamFlowable.subscribe(
				val -> logger.info("Subscriber received: " + val),
				err -> logger.error("Subscriber received error", err),
				() -> logger.info("Subscriber got Completed event"));

	}
}
