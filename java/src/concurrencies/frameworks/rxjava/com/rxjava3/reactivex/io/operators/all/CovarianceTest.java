package com.rxjava3.reactivex.io.operators.all;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import rx.Observable;
import rx.functions.Func2;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class CovarianceTest {

	/**
	 * This won't compile if super/extends isn't done correctly on generics
	 */
	@Test
	public void testCovarianceOfFrom() {
		Observable.<Movie> from(new HorrorMovie[] { new HorrorMovie() });
		Observable.<Movie> from(new ArrayList<HorrorMovie>());
		// Observable.<HorrorMovie>from(new Movie()); // may not compile
	}

	@Test
	public void testSortedList() {
		Func2<Media, Media, Integer> SORT_FUNCTION = new Func2<Media, Media, Integer>() {

			@Override
			public Integer call(Media t1, Media t2) {
				return 1;
			}
		};

		// this one would work without the covariance generics
		Observable<Media> o = Observable.from(Arrays.asList(new Movie(),
				new TVSeason(), new Album()));
		o.toSortedList(SORT_FUNCTION);

		// this one would NOT work without the covariance generics
		Observable<Movie> o2 = Observable.from(Arrays.asList(new Movie(),
				new ActionMovie(), new HorrorMovie()));
		o2.toSortedList(SORT_FUNCTION);
	}

	/*
	 * Most tests are moved into their applicable classes such as
	 * [Operator]Tests.java
	 */

	static class Media {
	}

	static class Movie extends Media {
	}

	static class HorrorMovie extends Movie {
	}

	static class ActionMovie extends Movie {
	}

	static class Album extends Media {
	}

	static class TVSeason extends Media {
	}

	static class Rating {
	}

	static class CoolRating extends Rating {
	}

	static class Result {
	}

	static class ExtendedResult extends Result {
	}
}