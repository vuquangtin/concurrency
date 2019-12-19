package com.rxjava3.reactivex.io.operators;

import java.util.stream.IntStream;

import io.reactivex.rxjava3.core.Observable;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ObservableFromArray {

	public void integerArray() {
		Integer[] arr = { 100, 200, 300 };
		Observable<Integer> source = Observable.fromArray(arr);
		source.subscribe(System.out::println);
	}

	public void intArrayWrong() {
		int[] intArray = { 400, 500, 600 };
		Observable.fromArray(intArray).subscribe(System.out::println);
	}

	public void intArray() {
		int[] intArray = { 400, 500, 600 };
		Observable<Integer> source = Observable.fromArray(toIntegerArray(intArray));
		source.subscribe(System.out::println);
	}

	public static Integer[] toIntegerArray(int[] intArray) {
		return IntStream.of(intArray).boxed().toArray(Integer[]::new);
	}

	public static void main(String[] args) {
		ObservableFromArray demo = new ObservableFromArray();
		demo.integerArray();
		demo.intArrayWrong();
		demo.intArray();
	}
}