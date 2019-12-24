package com.rxjava3;

import io.reactivex.rxjava3.core.Observable;

import java.util.Arrays;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ReactivexSamples {
	private static final String[] STRINGS = { "the", "quick", "brown", "fox",
			"jumped", "over", "the", "lazy", "dog" };

	public static void main(String[] args) {
		oneJust();
		twoJust();
		justList();
		correctFromList();
		fromRange();
		zipWordsWithRange();
		zipLettersWithRange();
		zipDistinctLettersWithRange();
		zipDistinctSortedLettersWithRange();
		zipReplacedDistinctSortedLettersWithRange();
	}

	private static void zipReplacedDistinctSortedLettersWithRange() {
		printOn(Observable
				.fromIterable(Arrays.asList(STRINGS))
				.map(w -> w.equals("jumped") ? "jumps" : w)
				.flatMap(w -> Observable.fromArray(w.split("")))
				.distinct()
				.sorted()
				.zipWith(Observable.range(1, Integer.MAX_VALUE),
						ReactivexSamples::formatZipped));
	}

	private static void zipDistinctSortedLettersWithRange() {
		printOn(Observable
				.fromIterable(Arrays.asList(STRINGS))
				.flatMap(w -> Observable.fromArray(w.split("")))
				.distinct()
				.sorted()
				.zipWith(Observable.range(1, Integer.MAX_VALUE),
						ReactivexSamples::formatZipped));
	}

	private static void zipDistinctLettersWithRange() {
		printOn(Observable
				.fromIterable(Arrays.asList(STRINGS))
				.flatMap(w -> Observable.fromArray(w.split("")))
				.distinct()
				.zipWith(Observable.range(1, Integer.MAX_VALUE),
						ReactivexSamples::formatZipped));
	}

	private static void zipWordsWithRange() {
		printOn(Observable.fromIterable(Arrays.asList(STRINGS)).zipWith(
				Observable.range(1, Integer.MAX_VALUE),
				ReactivexSamples::formatZipped));
	}

	private static void zipLettersWithRange() {
		printOn(Observable
				.fromIterable(Arrays.asList(STRINGS))
				.flatMap(w -> Observable.fromArray(w.split("")))
				.zipWith(Observable.range(1, Integer.MAX_VALUE),
						ReactivexSamples::formatZipped));
	}

	private static String formatZipped(String w, Integer i) {
		return String.format("%2d. %s", i, w);
	}

	private static void fromRange() {
		printOn(Observable.range(1, 5));
	}

	private static void correctFromList() {
		printOn(Observable.fromIterable(Arrays.asList(STRINGS)));
	}

	private static void justList() {
		printOn(Observable.just(Arrays.asList(STRINGS)));
	}

	private static void twoJust() {
		printOn(Observable.just("Hello", "World"));
	}

	private static void oneJust() {
		printOn(Observable.just("Howdy!"));
	}

	private static void printOn(Observable<?> observable) {
		observable.subscribe(System.out::println);
	}
}