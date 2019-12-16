/**
 * 
 */
package com.handson.concurrency.util.concurrent.atomic;

import static java.lang.System.out;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sveera
 *
 */
public class ConcurrencyExampleAtomicInteger {

	public static void main(String[] args) {

		AtomicInteger atomicInteger = new AtomicInteger();
		out.println(atomicInteger.get());
		out.println(atomicInteger.compareAndSet(1, 2));
		out.println(atomicInteger.get());
		out.println(atomicInteger.updateAndGet((value) -> {
			 out.println(value);
			return 3;
		}));
		out.println(atomicInteger.get());
		out.println(atomicInteger.compareAndSet(0, 2));
		out.println(atomicInteger.get());

	}

}
