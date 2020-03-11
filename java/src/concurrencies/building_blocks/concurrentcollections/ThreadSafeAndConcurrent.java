package concurrentcollections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 * @see https://riptutorial.com/java/example/6870/concurrent-collections
 *
 */
public class ThreadSafeAndConcurrent {
	// For example, the Java SE 5 java.util.concurrent.CopyOnWriteArrayList is a
	// thread safe and concurrent List implementation, its javadoc states :
	//
	// The "snapshot" style iterator method uses a reference to the state of the
	// array at the point that the iterator was created. This array never
	// changes during the lifetime of the iterator, so interference is
	// impossible and the iterator is guaranteed not to throw
	// ConcurrentModificationException.
	//
	// Therefore, the following code is safe :
	// public static final List<Integer> LIST = new CopyOnWriteArrayList<>();

	// Thread safe but non concurrent examples

	// In the above code, changing the LIST declaration to

	public static final List<Integer> LIST = Collections
			.synchronizedList(new ArrayList<>());

	// Could (and statistically will on most modern, multi CPU/core
	// architectures) lead to exceptions.

	// Synchronized collections from the Collections utility methods are thread
	// safe for addition/removal of elements, but not iteration (unless the
	// underlying collection being passed to it already is).
	public static void main(String[] args) throws InterruptedException {
		Thread modifier = new Thread(new ModifierRunnable());
		Thread iterator = new Thread(new IteratorRunnable());
		modifier.start();
		iterator.start();
		modifier.join();
		iterator.join();
	}

	public static final class ModifierRunnable implements Runnable {
		@Override
		public void run() {
			try {
				for (int i = 0; i < 50000; i++) {
					LIST.add(i);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static final class IteratorRunnable implements Runnable {
		@Override
		public void run() {
			try {
				for (int i = 0; i < 10000; i++) {
					long total = 0;
					for (Integer inList : LIST) {
						total += inList;
					}
					System.out.println(total);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}