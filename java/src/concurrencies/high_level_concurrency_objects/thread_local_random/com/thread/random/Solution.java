package com.thread.random;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class Solution {
	public static int getRandomIntegerBetweenNumbers(int from, int to) {
		ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
		return threadLocalRandom.nextInt(from, to);
	}

	public static double getRandomDouble() {
		ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
		return threadLocalRandom.nextDouble();
	}

	public static long getRandomLongBetween0AndN(long n) {
		ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
		return threadLocalRandom.nextLong(n);
	}

	public static void main(String[] args) {
		System.out.println(getRandomIntegerBetweenNumbers(100, 1000));
		System.out.println(getRandomDouble());
		System.out.println(getRandomLongBetween0AndN(100039l));
	}
}
