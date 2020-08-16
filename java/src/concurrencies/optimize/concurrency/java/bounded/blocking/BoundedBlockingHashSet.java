package concurrency.java.bounded.blocking;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class BoundedBlockingHashSet<T> {
	private Set<T> set;
	private Semaphore sem;

	public BoundedBlockingHashSet(int bound) {
		set = new HashSet<>();
		sem = new Semaphore(bound);
	}

	public boolean add(T o) throws InterruptedException {
		sem.acquire();
		boolean isAdded = false;
		try {
			isAdded = set.add(o);
		} finally {
			if (!isAdded) {
				sem.release();
			}
		}
		return isAdded;
	}

	public boolean remove(T o) throws InterruptedException {
		boolean isRemoved = set.remove(o);
		if (isRemoved) {
			sem.release();
		}
		return isRemoved;
	}

	public T get() throws InterruptedException {
		for (Iterator<T> it = set.iterator(); it.hasNext();) {
			T f = it.next();
			if (remove(f) == true)
				return f;
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		BoundedBlockingHashSet<String> set = new BoundedBlockingHashSet<>(5);
		set.add("Hi");
		set.add("Hola");
		set.add("Hello");
		set.add("Hey");
		System.out.println(set.get());
		set.add("Bonjour");
		set.add("Ciao");
	}
}