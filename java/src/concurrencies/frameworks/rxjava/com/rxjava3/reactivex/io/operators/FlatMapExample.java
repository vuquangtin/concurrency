package com.rxjava3.reactivex.io.operators;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;

import org.apache.log4j.Logger;

import concurrencies.utilities.Log4jUtils;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class FlatMapExample implements MarbleDiagram {
	static Logger logger = Logger.getLogger(FlatMapExample.class.getName());
	@Override
	public void marbleDiagram() {
		Function<String, Observable<String>> getDoubleDiamonds = ball -> Observable.just(ball + "<>", ball + "<>");

		String[] balls = { "1", "3", "5" };
		Observable<String> source = Observable.fromArray(balls).flatMap(getDoubleDiamonds);
		source.subscribe(logger::info);
	}

	public void flatMapLambda() {
		String[] balls = { "1", "3", "5" };
		Observable<String> source = Observable.fromArray(balls)
				.flatMap(ball -> Observable.just(ball + "<>", ball + "<>"));
		source.subscribe(logger::info);
	}

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		FlatMapExample demo = new FlatMapExample();
		demo.marbleDiagram();
		demo.flatMapLambda();
	}
}