package common;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class MyCopyOnWriteArrayList {

	public static void main(String[] args) {

		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(3, 4, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<>(3),
				new ThreadPoolExecutor.DiscardOldestPolicy());

		// Collection num = new ArrayList<String>();
		final Collection num = new CopyOnWriteArrayList();
		num.add("One");
		num.add("Two");
		num.add("Three");

		threadPool.execute(() -> {
			Iterator<String> iterator = num.iterator();
			while (iterator.hasNext()) {
				String element = iterator.next();
				if ("Two".equals(element)) {
					num.remove(element);
				} else {
					System.out.println(element);
				}
			}
		});

		threadPool.execute(() -> {
			Iterator<String> iterator = num.iterator();
			while (iterator.hasNext()) {
				String element = iterator.next();
				System.out.println(element + "ppp");
			}
		});
		threadPool.shutdown();
	}
}