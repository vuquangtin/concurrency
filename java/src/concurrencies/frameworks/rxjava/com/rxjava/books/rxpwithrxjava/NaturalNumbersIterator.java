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
import java.math.BigInteger;
import java.util.Iterator;

public class NaturalNumbersIterator implements Iterator<BigInteger> {

	private BigInteger current = BigInteger.ZERO;

	public boolean hasNext() {
		return true;
	}

	@Override
	public BigInteger next() {
		current = current.add(BigInteger.ONE);
		return current;
	}
}
