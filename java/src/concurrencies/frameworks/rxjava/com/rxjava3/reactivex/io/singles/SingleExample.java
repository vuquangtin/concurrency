package com.rxjava3.reactivex.io.singles;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.rxjava3.reactivex.io.BaseExample;

import concurrencies.utilities.Log4jUtils;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class SingleExample extends BaseExample {
	static Logger logger = Logger.getLogger(SingleExample.class.getName());

	private void runExampleOne() {
		long startTime = System.nanoTime();

		logger.info("BEGIN");
		Single<String> single = Single.just(desserts.get(0));
		// single.subscribe(logger::info);
		// logger.info("[END - " + calcTime(startTime) + "]");
	}

	private void runExampleTwo() {
		long startTime = System.nanoTime();

		logger.info("BEGIN");
		Single<List<String>> single = Observable.fromIterable(desserts)
		// .subscribeOn(Schedulers.io())
				.toList();
		// .subscribeOn(Schedulers.io())
		// .to(null);

		single.map(list -> {
			logger.info("map 1");

			List<String> result = new ArrayList<>();
			for (String str : list) {
				StringBuffer sb = new StringBuffer(str);
				String s = sb.reverse().toString();
				result.add(s);
			}

			return result;

		}).map(list -> {
			logger.info("map 2");

			List<String> result = new ArrayList<>();
			for (String str : list) {
				result.add(str.substring(0, str.length() / 2));
			}

			return result;

		}).subscribe(s -> {
			for (Object o : s) {
				logger.info(o.toString());
			}
		});

		logger.info("[END - " + calcTime(startTime) + "]");
	}

	private void runExampleThree() {
		long startTime = System.nanoTime();

		logger.info("BEGIN");
		Single<List<String>> single = Observable.fromIterable(desserts)
				.subscribeOn(Schedulers.io()).toList()
				.subscribeOn(Schedulers.io());

		single.flatMap(list -> {
			logger.info("map 1 - flatmap");

			return Single.create(sin -> {

				logger.info("map 1 - create");

				List<String> result = new ArrayList<>();
				for (String str : list) {
					StringBuffer sb = new StringBuffer(str);
					String s = sb.reverse().toString();
					result.add(s);
				}

				sin.onSuccess(result);

			}).subscribeOn(Schedulers.io());

		}).flatMap(list -> {
			logger.info("map 2 - flatmap");

			return Single.create(sin -> {

				logger.info("map 2 - create");

				List<String> result = new ArrayList<>();
				for (String str : (List<String>) list) {
					result.add(str.substring(0, str.length() / 2));
				}

				sin.onSuccess(result);

			}).subscribeOn(Schedulers.io());
		}).subscribe(s -> {
			for (String str : (List<String>) s) {
				logger.info(str);
			}
		});

		logger.info("[END - " + calcTime(startTime) + "]");
	}

	private void runExampleFour() {
		long startTime = System.nanoTime();

		logger.info("BEGIN");
		Single<List<String>> single = Observable.fromIterable(desserts)
				.subscribeOn(Schedulers.io()).toList()
				.subscribeOn(Schedulers.io());

		single.flatMapObservable(list -> {
			logger.info("map 1 - flatMapObservable");

			return Observable.fromIterable(list).flatMap(d -> {
				logger.info("map 1");
				StringBuffer sb = new StringBuffer(d);
				return Observable.just(sb.reverse().toString()); // .subscribeOn(Schedulers.io());
				}).flatMap(d -> {
				logger.info("map 2");
				return Observable.just(d.substring(0, d.length() / 2)); // .subscribeOn(Schedulers.io());
				}).subscribeOn(Schedulers.io());
		}).subscribe(logger::info);

		logger.info("[END - " + calcTime(startTime) + "]");
	}

	public static void main(String[] args) throws Exception {
		logger = Log4jUtils.initLog4j();
		SingleExample singleExample = new SingleExample();

		 singleExample.runExampleOne();
		 singleExample.runExampleTwo();
		 singleExample.runExampleThree();
		 singleExample.runExampleFour();

		Thread.sleep(10000);

	}

}