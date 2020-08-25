package concurrence;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class AtomicIntegerFieldUpdaterDemo2 {

	public static void main(String[] args) throws InterruptedException {

		Counter counter = new Counter();
		for (int i = 0; i < 10; i++)
			new Thread(() -> {
				for (int j = 0; j < 10000; j++)
					counter.atomicAdd(1);
			}).start();
		Thread.sleep(3000);
		System.out.println("count = " + counter.getCount());

	}

	static class Counter {

		AtomicInteger count = new AtomicInteger(0);

		public int getCount() {
			return count.get();
		}

		public void atomicAdd(int delta) {
			count.getAndAdd(delta);
		}
	}

}