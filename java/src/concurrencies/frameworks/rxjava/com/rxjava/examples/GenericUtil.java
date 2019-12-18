package com.rxjava.examples;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * utility methods common to all
 *
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * 
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class GenericUtil {
	public static ExecutorService SERVICE = Executors.newFixedThreadPool(10);

	public static long getMilliElapsed(long start) {
		return System.currentTimeMillis() - start;
	}
}