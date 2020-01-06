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
public class ThreadLocalRandomTest {

	public static void main(String[] args) {
		new ThreadLocalRandomTest().testStress();
	}

	public void testStress() {
		ThreadLocalRandom.current().nextLong(Math.abs(ThreadLocalRandom.current().nextLong()));
		ThreadLocalRandom.current().nextInt();
		ThreadLocalRandom.current().nextLong();
		ThreadLocalRandom.current().nextDouble(212D, 345D);
		ThreadLocalRandom.current().nextInt(Math.abs(ThreadLocalRandom.current().nextInt()));
		ThreadLocalRandom.current().nextLong(89281837L, 2183721089L);
		ThreadLocalRandom.current().nextLong(89281837L, 2183721089L);
		ThreadLocalRandom.current().nextBytes(new byte[20]);
		ThreadLocalRandom.current().nextDouble(212D, 345D);
		ThreadLocalRandom.current().nextInt(47);
		ThreadLocalRandom.current().nextFloat();
		ThreadLocalRandom.current().nextInt(47);
		ThreadLocalRandom.current().nextDouble();
		ThreadLocalRandom.current().nextBytes(new byte[20]);
		ThreadLocalRandom.current().nextInt(-2982, 2019);
		ThreadLocalRandom.current().nextInt(-2982, 2019);
		ThreadLocalRandom.current().nextInt(-2982, 2019);
		ThreadLocalRandom.current().nextBytes(new byte[20]);
		ThreadLocalRandom.current().nextBytes(new byte[20]);
		ThreadLocalRandom.current().nextLong(89281837L, 2183721089L);
		ThreadLocalRandom.current().nextFloat();
		ThreadLocalRandom.current().nextDouble(47D);

		// Assessing nextInt() return value, because Integer.MIN_VALUE isn't
		// made positive by Math.abs, and
		// passing a negative value to nextInt(int) results in an exception
		// being thrown.
		int mustNotBeNegative = Math.abs(ThreadLocalRandom.current().nextInt());
		if (mustNotBeNegative == Integer.MIN_VALUE)
			mustNotBeNegative = 1;
		ThreadLocalRandom.current().nextInt(mustNotBeNegative);

		ThreadLocalRandom.current().nextDouble(212D, 345D);
		ThreadLocalRandom.current().nextLong(89281837L, 2183721089L);
		ThreadLocalRandom.current().nextLong(21344812L);
		ThreadLocalRandom.current().nextInt(47);
		ThreadLocalRandom.current().nextFloat();
		ThreadLocalRandom.current().nextLong();
		ThreadLocalRandom.current().nextInt(Math.abs(ThreadLocalRandom.current().nextInt()));
		ThreadLocalRandom.current().nextGaussian();
		ThreadLocalRandom.current().nextBytes(new byte[20]);
		ThreadLocalRandom.current().nextDouble(47D);
		ThreadLocalRandom.current().nextInt(-2982, 2019);
		ThreadLocalRandom.current().nextInt(Math.abs(ThreadLocalRandom.current().nextInt()));
		ThreadLocalRandom.current().nextLong(Math.abs(ThreadLocalRandom.current().nextLong()));
		ThreadLocalRandom.current().nextInt(Math.abs(ThreadLocalRandom.current().nextInt()));
		ThreadLocalRandom.current().nextGaussian();
		ThreadLocalRandom.current().nextLong(89281837L, 2183721089L);
		ThreadLocalRandom.current().nextLong(89281837L, 2183721089L);
		ThreadLocalRandom.current().nextInt(-2982, 2019);
		ThreadLocalRandom.current().nextLong();
		ThreadLocalRandom.current().nextInt(-2982, 2019);
		ThreadLocalRandom.current().nextInt(Math.abs(ThreadLocalRandom.current().nextInt()));
		ThreadLocalRandom.current().nextInt(47);
		ThreadLocalRandom.current().nextLong(Math.abs(ThreadLocalRandom.current().nextLong()));
		ThreadLocalRandom.current().nextFloat();
		ThreadLocalRandom.current().nextInt(Math.abs(ThreadLocalRandom.current().nextInt()));
		ThreadLocalRandom.current().nextFloat();
		ThreadLocalRandom.current().nextInt(Math.abs(ThreadLocalRandom.current().nextInt()));
		ThreadLocalRandom.current().nextDouble();
		ThreadLocalRandom.current().nextDouble(ThreadLocalRandom.current().nextDouble());
		ThreadLocalRandom.current().nextLong(21344812L);
		ThreadLocalRandom.current().nextDouble(47D);
		ThreadLocalRandom.current().nextLong(89281837L, 2183721089L);
		ThreadLocalRandom.current().nextInt(Math.abs(ThreadLocalRandom.current().nextInt()));
		ThreadLocalRandom.current().nextBytes(new byte[20]);
		ThreadLocalRandom.current().nextLong(21344812L);
		ThreadLocalRandom.current().nextBytes(new byte[20]);
		ThreadLocalRandom.current().nextGaussian();
		ThreadLocalRandom.current().nextDouble();
		ThreadLocalRandom.current().nextDouble(212D, 345D);
		ThreadLocalRandom.current().nextDouble(47D);
		ThreadLocalRandom.current().nextInt();
		ThreadLocalRandom.current().nextLong();
		ThreadLocalRandom.current().nextGaussian();
		ThreadLocalRandom.current().nextDouble(ThreadLocalRandom.current().nextDouble());
		ThreadLocalRandom.current().nextFloat();
		ThreadLocalRandom.current().nextGaussian();
		ThreadLocalRandom.current().nextDouble(47D);
		ThreadLocalRandom.current().nextFloat();
		ThreadLocalRandom.current().nextInt(-2982, 2019);
		ThreadLocalRandom.current().nextBoolean();
		ThreadLocalRandom.current().nextInt(-2982, 2019);
		ThreadLocalRandom.current().nextBytes(new byte[20]);
		ThreadLocalRandom.current().nextLong(Math.abs(ThreadLocalRandom.current().nextLong()));
		ThreadLocalRandom.current().nextBoolean();
		ThreadLocalRandom.current().nextDouble(ThreadLocalRandom.current().nextDouble());
		ThreadLocalRandom.current().nextInt(47);
		ThreadLocalRandom.current().nextLong();
		ThreadLocalRandom.current().nextGaussian();
		ThreadLocalRandom.current().nextFloat();
		ThreadLocalRandom.current().nextLong();
		ThreadLocalRandom.current().nextInt();
		ThreadLocalRandom.current().nextLong(89281837L, 2183721089L);
		ThreadLocalRandom.current().nextInt();
		ThreadLocalRandom.current().nextDouble(212D, 345D);
		ThreadLocalRandom.current().nextGaussian();
		ThreadLocalRandom.current().nextLong(Math.abs(ThreadLocalRandom.current().nextLong()));
		ThreadLocalRandom.current().nextLong(Math.abs(ThreadLocalRandom.current().nextLong()));
		ThreadLocalRandom.current().nextDouble(ThreadLocalRandom.current().nextDouble());
		ThreadLocalRandom.current().nextDouble();
		ThreadLocalRandom.current().nextLong(Math.abs(ThreadLocalRandom.current().nextLong()));
		ThreadLocalRandom.current().nextGaussian();
		ThreadLocalRandom.current().nextDouble(ThreadLocalRandom.current().nextDouble());
		ThreadLocalRandom.current().nextBytes(new byte[20]);
		ThreadLocalRandom.current().nextLong(21344812L);
		ThreadLocalRandom.current().nextLong(89281837L, 2183721089L);
		try {
			ThreadLocalRandom.current().setSeed(67L);
		} catch (UnsupportedOperationException e) {
			// expected
		}
		ThreadLocalRandom.current().nextFloat();
		ThreadLocalRandom.current().nextDouble(212D, 345D);
		ThreadLocalRandom.current().nextDouble();
		ThreadLocalRandom.current().nextBoolean();
		ThreadLocalRandom.current().nextInt();
		try {
			ThreadLocalRandom.current().setSeed(67L);
		} catch (UnsupportedOperationException e) {
			// expected
		}
		ThreadLocalRandom.current().nextDouble(ThreadLocalRandom.current().nextDouble());
		ThreadLocalRandom.current().nextGaussian();
		ThreadLocalRandom.current().nextInt(-2982, 2019);
		ThreadLocalRandom.current().nextDouble();
		ThreadLocalRandom.current().nextDouble(47D);
		ThreadLocalRandom.current().nextLong(89281837L, 2183721089L);
		ThreadLocalRandom.current().nextDouble(212D, 345D);
		ThreadLocalRandom.current().nextFloat();
		ThreadLocalRandom.current().nextInt(-2982, 2019);
		ThreadLocalRandom.current().nextInt(-2982, 2019);
		ThreadLocalRandom.current().nextBoolean();
		try {
			ThreadLocalRandom.current().setSeed(67L);
		} catch (UnsupportedOperationException e) {
			// expected
		}
		ThreadLocalRandom.current().nextLong(89281837L, 2183721089L);
		ThreadLocalRandom.current().nextLong();
		ThreadLocalRandom.current().nextBoolean();
		ThreadLocalRandom.current().nextLong(21344812L);
		ThreadLocalRandom.current().nextLong();
		ThreadLocalRandom.current().nextLong(Math.abs(ThreadLocalRandom.current().nextLong()));
		ThreadLocalRandom.current().nextLong();
		ThreadLocalRandom.current().nextDouble(ThreadLocalRandom.current().nextDouble());
		ThreadLocalRandom.current().nextLong();
		ThreadLocalRandom.current().nextInt(47);
		ThreadLocalRandom.current().nextLong();
		ThreadLocalRandom.current().nextInt(-2982, 2019);
		ThreadLocalRandom.current().nextDouble(212D, 345D);
		ThreadLocalRandom.current().nextLong(89281837L, 2183721089L);
		ThreadLocalRandom.current().nextGaussian();
		ThreadLocalRandom.current().nextGaussian();
		ThreadLocalRandom.current().nextFloat();
		ThreadLocalRandom.current().nextLong(21344812L);
		ThreadLocalRandom.current().nextBoolean();
		ThreadLocalRandom.current().nextDouble(47D);
		ThreadLocalRandom.current().nextInt(-2982, 2019);
		ThreadLocalRandom.current().nextBytes(new byte[20]);
		ThreadLocalRandom.current().nextDouble(47D);
		ThreadLocalRandom.current().nextLong(21344812L);
		ThreadLocalRandom.current().nextDouble(212D, 345D);
		ThreadLocalRandom.current().nextDouble();
		ThreadLocalRandom.current().nextDouble(47D);
		ThreadLocalRandom.current().nextDouble(47D);
		ThreadLocalRandom.current().nextLong(Math.abs(ThreadLocalRandom.current().nextLong()));
		try {
			ThreadLocalRandom.current().setSeed(67L);
		} catch (UnsupportedOperationException e) {
			// expected
		}
		ThreadLocalRandom.current().nextInt(-2982, 2019);
		ThreadLocalRandom.current().nextDouble(47D);
		try {
			ThreadLocalRandom.current().setSeed(67L);
		} catch (UnsupportedOperationException e) {
			// expected
		}
		ThreadLocalRandom.current().nextInt(Math.abs(ThreadLocalRandom.current().nextInt()));
		ThreadLocalRandom.current().nextInt(47);
		ThreadLocalRandom.current().nextGaussian();
		ThreadLocalRandom.current().nextDouble(ThreadLocalRandom.current().nextDouble());
		ThreadLocalRandom.current().nextLong(21344812L);
		ThreadLocalRandom.current().nextBoolean();
		ThreadLocalRandom.current().nextDouble(47D);
		ThreadLocalRandom.current().nextLong(Math.abs(ThreadLocalRandom.current().nextLong()));
		ThreadLocalRandom.current().nextBytes(new byte[20]);
		ThreadLocalRandom.current().nextLong(21344812L);
		ThreadLocalRandom.current().nextLong(21344812L);
		ThreadLocalRandom.current().nextFloat();
		ThreadLocalRandom.current().nextDouble(212D, 345D);
		ThreadLocalRandom.current().nextBoolean();
		ThreadLocalRandom.current().nextInt(47);
		ThreadLocalRandom.current().nextInt(47);
		ThreadLocalRandom.current().nextLong(21344812L);
		ThreadLocalRandom.current().nextDouble(47D);
		ThreadLocalRandom.current().nextInt(Math.abs(ThreadLocalRandom.current().nextInt()));
		ThreadLocalRandom.current().nextBoolean();
		ThreadLocalRandom.current().nextLong();
		ThreadLocalRandom.current().nextLong();
		ThreadLocalRandom.current().nextInt(Math.abs(ThreadLocalRandom.current().nextInt()));
		ThreadLocalRandom.current().nextLong(89281837L, 2183721089L);
		ThreadLocalRandom.current().nextInt(47);
		ThreadLocalRandom.current().nextInt(-2982, 2019);
		ThreadLocalRandom.current().nextLong(Math.abs(ThreadLocalRandom.current().nextLong()));
		ThreadLocalRandom.current().nextLong();
		ThreadLocalRandom.current().nextDouble(ThreadLocalRandom.current().nextDouble());
		ThreadLocalRandom.current().nextLong();
		ThreadLocalRandom.current().nextLong();
		ThreadLocalRandom.current().nextDouble();
		ThreadLocalRandom.current().nextInt(-2982, 2019);
		ThreadLocalRandom.current().nextDouble(ThreadLocalRandom.current().nextDouble());
		ThreadLocalRandom.current().nextLong(21344812L);
		ThreadLocalRandom.current().nextLong(Math.abs(ThreadLocalRandom.current().nextLong()));
		ThreadLocalRandom.current().nextGaussian();
		ThreadLocalRandom.current().nextDouble(ThreadLocalRandom.current().nextDouble());
		ThreadLocalRandom.current().nextBytes(new byte[20]);
		ThreadLocalRandom.current().nextInt(47);
		ThreadLocalRandom.current().nextDouble(212D, 345D);
		ThreadLocalRandom.current().nextBoolean();
		ThreadLocalRandom.current().nextDouble();
		ThreadLocalRandom.current().nextFloat();
		ThreadLocalRandom.current().nextDouble(47D);
		ThreadLocalRandom.current().nextGaussian();
		ThreadLocalRandom.current().nextLong(Math.abs(ThreadLocalRandom.current().nextLong()));
		ThreadLocalRandom.current().nextDouble(47D);
		ThreadLocalRandom.current().nextInt(-2982, 2019);
		ThreadLocalRandom.current().nextDouble(47D);
	}
}
