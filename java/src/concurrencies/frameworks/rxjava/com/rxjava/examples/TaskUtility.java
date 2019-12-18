package com.rxjava.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * utility class to create simple callables returning futures
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * 
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class TaskUtility {

	/**
	 * generate of list of numbers converted to string starting with 1 and
	 * inclusive of the provided count
	 *
	 * @param count
	 * @return
	 */
	public static List<String> getListOfNumbers(int count) {
		return IntStream.rangeClosed(1, count)
				.mapToObj(value -> String.valueOf(value))
				.collect(Collectors.toList());
	}

	/**
	 * create a future based on the given value.
	 *
	 * @param v
	 * @param delayMilli
	 * @return
	 */
	public static FutureTask<String> getSingleStringFuture(String v,
			long delayMilli) {
		FutureTask<String> f = new FutureTask<String>(() -> {
			Thread.sleep(delayMilli);
			return v;
		});
		return f;
	}

	/**
	 * based on the list of values passed, submit callable tasks via an executor
	 * service and return corresponding list of futures.
	 *
	 * @param values
	 * @return
	 */
	public static List<Future<String>> submitListOfTasks(List<String> values,
			long delayMilli) {
		List<Future<String>> futures = new ArrayList<>();
		for (String v : values) {
			futures.add(GenericUtil.SERVICE.submit(() -> {
				Thread.sleep(delayMilli);
				return v;
			}));
		}
		return futures;
	}

	/**
	 * create a futureTask out of a quote by optionally adding a delay.
	 *
	 * @param isDelay
	 * @return
	 */
	public static FutureTask<QuoteResource> getSingleQuoteFuture(boolean isDelay) {
		FutureTask<QuoteResource> fqTask;
		if (isDelay) {
			fqTask = new FutureTask<QuoteResource>(
					() -> RestUtility
							.getRandomQuoteWithDelay(RestUtility.SAFE_DELAY));
		} else {
			System.out.println("---> Running random quote no delay");
			fqTask = new FutureTask<QuoteResource>(
					() -> RestUtility.getRandomQuote());
		}

		return fqTask;
	}

	/**
	 * instead of {@link FutureTask} present a callable so that it can be run by
	 * observable
	 *
	 * @param isDelay
	 * @return
	 */
	public static Callable<QuoteResource> getSingleQuoteCallable(boolean isDelay) {
		Callable<QuoteResource> fqTask;
		if (isDelay) {
			return () -> RestUtility
					.getRandomQuoteWithDelay(RestUtility.SAFE_DELAY);
		} else {
			System.out.println("---> Running random quote no delay");
			return () -> RestUtility.getRandomQuote();
		}
	}

	/**
	 * a List of quote retrieving callables
	 *
	 * @param isDelay
	 * @param count
	 *            number of callables in the list
	 * @return
	 */
	public static List<Callable<QuoteResource>> getListQuoteCallable(
			boolean isDelay, int count) {
		List<Callable<QuoteResource>> callableList = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			if (isDelay) {
				callableList.add(() -> RestUtility
						.getRandomQuoteWithDelay(RestUtility.SAFE_DELAY));
			} else {
				callableList.add(() -> RestUtility.getRandomQuote());
			}
		}
		return callableList;
	}

	/**
	 * simply take a futureTask or {@link Runnable} and execute it using the
	 * {@link ExecutorService}
	 *
	 * @param futureTask
	 * @param <T>
	 */
	public static <T> void runFutureTask(FutureTask<T> futureTask) {
		GenericUtil.SERVICE.execute(futureTask);
	}

	public static Callable<String> getSingleStringCallable(String v) {
		Callable<String> c = new Callable<String>() {
			@Override
			public String call() throws Exception {
				return Thread.currentThread().getId() + ":"
						+ Thread.currentThread().getName() + " => " + v;
			}
		};
		return c;
	}
}