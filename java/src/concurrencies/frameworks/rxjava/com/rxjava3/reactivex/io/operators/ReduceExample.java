package com.rxjava3.reactivex.io.operators;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.BiFunction;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ReduceExample implements MarbleDiagram {

	@Override
	public void marbleDiagram() {
		String[] balls = { "1", "3", "5" };
		Maybe<String> source = Observable.fromArray(balls).reduce((ball1, ball2) -> ball2 + "(" + ball1 + ")");
		source.subscribe(System.out::println);
	}

	public void biFunction() {
		BiFunction<String, String, String> mergeBalls = (ball1, ball2) -> ball2 + "(" + ball1 + ")";
		String[] balls = { "1", "3", "5" };
		Maybe<String> source = Observable.fromArray(balls).reduce(mergeBalls);
		source.subscribe(System.out::println);
	}

	public static void main(String[] args) {
		ReduceExample demo = new ReduceExample();
		demo.marbleDiagram();
		demo.biFunction();
	}
}