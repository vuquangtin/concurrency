package concurrentcollections;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Fail-Fast Iterators<br/>
 * When working with collections, you also need to understand this concurrency
 * policy with regard to their iterators: Fail-fast iterators. <br/>
 * Consider the following code snippet that iterates a list of Strings:<br/>
 * 
 * <code> List<String> listNames = Arrays.asList("Tom", "Joe", "Bill", "Dave",
 *       "John");
 * 
 *       Iterator<String> iterator = listNames.iterator();
 * 
 *       while (iterator.hasNext()) { String nextName = iterator.next();
 *       System.out.println(nextName); }
 *       </code> This test program illustrates how a collection's iterator fails
 * fast and throw ConcurrentModificationException
 * 
 * As you can see, the thread1 is iterating the list, while the thread2 adds
 * more elements to the collection. This causes the
 * ConcurrentModificationException to be thrown.
 * 
 * Note that the fail-fast behavior of collection’s iterators intends to help
 * find and diagnose bugs easily. We should not rely on it to handle
 * ConcurrentModificationException in our programs, because the fail-fast
 * behavior is not guaranteed. That means if this exception is thrown, the
 * program should stop immediately, instead of continuing the execution.
 * 
 * Now you understand how ConcurrentModificationExceptionworks and it’s better
 * to avoid it.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class IteratorFailFastTest {

	private List<Integer> list = new ArrayList<>();

	public IteratorFailFastTest() {
		for (int i = 0; i < 10_000; i++) {
			list.add(i);
		}
	}

	public void runUpdateThread() {
		Thread thread1 = new Thread(new Runnable() {

			public void run() {
				for (int i = 10_000; i < 20_000; i++) {
					list.add(i);
				}
			}
		});

		thread1.start();
	}

	public void runIteratorThread() {
		Thread thread2 = new Thread(new Runnable() {

			public void run() {
				ListIterator<Integer> iterator = list.listIterator();
				while (iterator.hasNext()) {
					Integer number = iterator.next();
					System.out.println(number);
				}
			}
		});

		thread2.start();
	}

	public static void main(String[] args) {
		IteratorFailFastTest tester = new IteratorFailFastTest();

		tester.runIteratorThread();
		tester.runUpdateThread();
	}
}