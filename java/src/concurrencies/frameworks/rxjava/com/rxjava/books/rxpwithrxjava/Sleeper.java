package com.rxjava.books.rxpwithrxjava;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Sleeper {

	private static final Logger log = LoggerFactory.getLogger(Sleeper.class);
	public static final Random RAND = new Random();

	public static void sleep(Duration duration, Duration stdDev) {
		double randMillis = Math.max(0, duration.toMillis() + RAND.nextGaussian() * stdDev.toMillis());
		sleep(Duration.ofMillis((long) randMillis));
	}

	public static void sleep(Duration duration) {
		try {
			TimeUnit.MILLISECONDS.sleep(duration.toMillis());
		} catch (InterruptedException e) {
			log.warn("Sleep interrupted", e);
		}
	}

}
