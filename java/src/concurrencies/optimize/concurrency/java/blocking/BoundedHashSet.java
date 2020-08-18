package concurrency.java.blocking;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class BoundedHashSet<T> {
	private final Set<T> set;
	private final Semaphore semaphore;

	public BoundedHashSet(int bound) {
		this.set = Collections.synchronizedSet(new HashSet<T>());
		this.semaphore = new Semaphore(bound);
	}

	public boolean add(T t) throws InterruptedException{
		semaphore.acquire();
		boolean wasAdded = false;
		try {
			wasAdded = set.add(t);
			return wasAdded;
		} finally {
			if (!wasAdded) {
				semaphore.release();
			}
		}
	}

	public boolean remove(Object o) {
		boolean wasRemoved = set.remove(o);
		if (wasRemoved) {
			semaphore.release();
		}
		return wasRemoved;
	}

}