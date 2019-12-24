package com.rxjava3.reactivex.io.observable;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import org.apache.log4j.Logger;

import com.rxjava3.reactivex.io.BaseExample;

import concurrencies.utilities.Log4jUtils;

public class ObservableExample extends BaseExample {
	static Logger logger = Logger.getLogger(ObservableExample.class.getName());

	private void runExampleOne() {
		long startTime = System.nanoTime();

		logger.info("BEGIN");
		Observable<String> observable = Observable.just(desserts.get(0));
		observable.subscribe(logger::info);
		logger.info("[END - " + calcTime(startTime) + "]");
	}

	private void runExampleTwo() {
		long startTime = System.nanoTime();

		logger.info("BEGIN");
		Observable<String> observable = Observable.just(desserts.get(0),
				desserts.get(1), desserts.get(2));
		observable.subscribe(logger::info);
		logger.info("[END - " + calcTime(startTime) + "]");

	}

	private void runExampleThree() {
		long startTime = System.nanoTime();

		logger.info("BEGIN");
		Observable<String> observable = Observable.fromIterable(desserts);
		observable.subscribe(logger::info);
		logger.info("[END - " + calcTime(startTime) + "]");

	}

	private void runExampleFour() {
		long startTime = System.nanoTime();

		logger.info("BEGIN");
		Observable<String> observable = Observable.fromIterable(desserts);
		observable.map(d -> {
			logger.info("map 1");
			StringBuffer sb = new StringBuffer(d);
			return sb.reverse().toString();
		}).map(d -> {
			logger.info("map 2");
			return d.substring(0, d.length() / 2);
		}).subscribe(logger::info);
		logger.info("[END - " + calcTime(startTime) + "]");

	}

	private void runExampleFive() {
		long startTime = System.nanoTime();

		logger.info("BEGIN");

		Observer<Integer> subscriber = new Observer<Integer>() {

			@Override
			public void onNext(Integer i) {
				logger.info("Next " + i);
			}

			@Override
			public void onError(Throwable e) {
				logger.error("Error: " + e);
			}

			@Override
			public void onSubscribe(Disposable d) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onComplete() {
				// TODO Auto-generated method stub

			}
		};

		Observable<Integer> observable = Observable.range(1, 5); // .subscribeOn(Schedulers.io());
		observable.subscribe(subscriber);
		logger.info("[END - " + calcTime(startTime) + "]");

	}

	private void runExampleSix() {
		long startTime = System.nanoTime();

		logger.info("BEGIN");

		Observable.fromIterable(desserts).skip(3).take(2)
				.subscribe(logger::info);

		logger.info("[END - " + calcTime(startTime) + "]");

	}

	private void runExampleSeven() {
		long startTime = System.nanoTime();

		logger.info("BEGIN");

		Observable.fromIterable(desserts).flatMap(d -> {
			logger.info("map 1");
			StringBuffer sb = new StringBuffer(d);
			return Observable.just(sb.reverse().toString()); // .subscribeOn(Schedulers.io());
			}).flatMap(d -> {
			logger.info("map 2");
			return Observable.just(d.substring(0, d.length() / 2)); // .subscribeOn(Schedulers.io());
			}).subscribe(logger::info);

		logger.info("[END - " + calcTime(startTime) + "]");

	}

	private void runExampleEight() {
		long startTime = System.nanoTime();

		logger.info("BEGIN");

		Observable
				.fromIterable(desserts)
				.flatMapSingle(str -> {
					return Single.create(s -> {
						logger.info("map 1");
						StringBuffer sb = new StringBuffer(str);
						s.onSuccess(sb.reverse().toString());
					}).subscribeOn(Schedulers.io());
				})
				.flatMapSingle(
						str -> {
							return Single.create(
									s -> {
										logger.info("map 2");
										s.onSuccess(str.toString().substring(0,
												str.toString().length() / 2));
									}).subscribeOn(Schedulers.io());
						}).toList().subscribe(list -> {
					for (Object str : list) {
						logger.info(str.toString());
					}
				});

		logger.info("[END - " + calcTime(startTime) + "]");

	}

	private void runExampleNine() {
		long startTime = System.nanoTime();

		logger.info("BEGIN");

		Observable.fromIterable(desserts).flatMapSingle(str -> {
			return Single.create(s -> {
				String result = str.concat(" - map 1");
				logger.info(result);
				s.onSuccess(result);
			}).subscribeOn(Schedulers.io());
		}).flatMapSingle(str -> {
			return Single.create(s -> {
				String result = str.toString().concat(" - map 2");
				logger.info(result);
				s.onSuccess(result);
			}).subscribeOn(Schedulers.io());
		}).toList().subscribe(list -> {
			for (Object str : list) {
				logger.info(str.toString());
			}
		});

		logger.info("[END - " + calcTime(startTime) + "]");

	}

	public static void main(String[] args) throws Exception {
		logger = Log4jUtils.initLog4j();
		ObservableExample observableExample = new ObservableExample();

		observableExample.runExampleOne();
		observableExample.runExampleTwo();
		observableExample.runExampleThree();
		observableExample.runExampleFour();
		observableExample.runExampleFive();
		observableExample.runExampleSix();
		observableExample.runExampleSeven();
		observableExample.runExampleEight();
		observableExample.runExampleNine();

		Thread.sleep(10000);

	}

}