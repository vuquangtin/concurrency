package concurrency.java.blocking;

import java.util.concurrent.atomic.AtomicReference;

import javax.annotation.concurrent.Immutable;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class CasNumberRange {
	@Immutable
	private static class IntPair {
		final int lower; // Invariant: lower <= upper
		final int upper;

		IntPair(int lower, int upper) {
			this.lower = lower;
			this.upper = upper;
		}
	}

	private final AtomicReference<IntPair> values = new AtomicReference<IntPair>(new IntPair(0, 0));
	public int getLower() { return values.get().lower; }
	public int getUpper() { return values.get().upper; }

	public void setLower(int i) {
		while (true) {
			IntPair oldV = values.get();
			if (i > oldV.upper) {
				throw new IllegalArgumentException("Can’t set lower to " + i + " > upper");
			}
			IntPair newV = new IntPair(i, oldV.upper);
			if (values.compareAndSet(oldV, newV)) {
				return;
			}
		}
	} // similarly for setUpper

	public void setUpper(int i) {
		while (true) {
			IntPair oldV = values.get();
			if (i < oldV.lower) {
				throw new IllegalArgumentException("Can't set upper to " + i + " < lower");
			}
			IntPair newV = new IntPair(oldV.lower, i);
			if (values.compareAndSet(oldV, newV)) {
				return;
			}
		}
	}

}