package com.rxjava3.reactivex.io.operators;

import org.apache.log4j.Logger;

import concurrencies.utilities.Log4jUtils;
import concurrencies.utilities.LogTest;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class MapExample implements MarbleDiagram {
	static Logger logger = Logger.getLogger(LogTest.class.getName());
	@Override
	public void marbleDiagram() {
		String[] balls = { "1", "2", "3", "5" };
		Observable<String> source = Observable.fromArray(balls).map(ball -> ball + "<>");
		source.subscribe(logger::info);
	}

	public void mappingType() {
		Function<String, Integer> ballToIndex = ball -> {
			switch (ball) {
			case "RED":
				return 1;
			case "YELLOW":
				return 2;
			case "GREEN":
				return 3;
			case "BLUE":
				return 5;
			default:
				return -1;
			}
		};

		String[] balls = { "RED", "YELLOW", "GREEN", "BLUE" };
		Observable<Integer> source = Observable.fromArray(balls).map(ballToIndex);
		source.subscribe(logger::info);
	}

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		MapExample demo = new MapExample();
		demo.marbleDiagram();
		demo.mappingType();
	}
}