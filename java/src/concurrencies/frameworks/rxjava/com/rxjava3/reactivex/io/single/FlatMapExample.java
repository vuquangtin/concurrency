package com.rxjava3.reactivex.io.single;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;
import com.rxjava3.utils.Utils;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Lets start with a simple Java unit test that is testing flatMap operator.
 * First we create list of Strings, then transform each object of this list into
 * an Observable using operator from. Then each item is flapMapped into an
 * observable that adds “x” letter at the end of the string. Next the observable
 * is delayed by na random number of seconds (line 9). Last thing is advancing
 * in time by 1 minute (line 17), just to be sure that everything will have the
 * time to emit. (If we would not do this, the test would end before any
 * emission).
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class FlatMapExample {

	public static void main(String[] args) {
		final List<String> items = Lists.newArrayList("a", "b", "c", "d", "e",
				"f");

		Observable
				.fromIterable(items)
				.flatMap(
						s -> {
							final int delay = new java.util.Random()
									.nextInt(10);
							return Observable.just(s + "x").delay(delay,
									TimeUnit.SECONDS, Schedulers.io());
						}).toList().subscribe(System.out::println);

		Utils.sleep10000();

	}
}
