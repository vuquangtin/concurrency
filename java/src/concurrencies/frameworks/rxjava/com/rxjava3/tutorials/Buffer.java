package com.rxjava3.tutorials;

import static com.rxjava3.tutorials.Sleeper.sleep;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;

import java.util.concurrent.TimeUnit;
public class Buffer {
	public static void main(String[] args) {
		openAndClosingSelector();
	}

	private static void bufferOfTimeSpan() {
		TestObservable.timeIncOb.buffer(50L, TimeUnit.MILLISECONDS).subscribe(System.out::println);
	}

	private static void bufferOfCombination() {
		TestObservable.timeIncOb.buffer(2).subscribe(System.out::println);
	}

	/**
	 * the skip param test
	 */
	private static void overlapping() {
		TestObservable.intervalOb.buffer(3, 1).subscribe(System.out::println);
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void standard() {
		TestObservable.intervalOb.buffer(3, 3).subscribe(System.out::println);
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void skip() {
		TestObservable.intervalOb.buffer(3, 4).subscribe(System.out::println);
		sleep(10);
	}

	/**
	 * a little advanced
	 */
	private static void boundary() {
		TestObservable.intervalOb.buffer(Observable.interval(4, 3, TimeUnit.SECONDS)).subscribe(System.out::println);
		sleep(10);
	}

	private static void openAndClosingSelector() {
		TestObservable.intervalOb.buffer(Observable.interval(2000, 1500, TimeUnit.MILLISECONDS),
				new Function<Long, ObservableSource<? extends Long>>() {
					@Override
					public ObservableSource<? extends Long> apply(Long times) throws Exception {
						// This 'times' parameter is the times of
						// openingSelector event was emitted
						return Observable.timer(2000, TimeUnit.MILLISECONDS);
					}
				}).subscribe(System.out::println);
		sleep(10);
	}
}