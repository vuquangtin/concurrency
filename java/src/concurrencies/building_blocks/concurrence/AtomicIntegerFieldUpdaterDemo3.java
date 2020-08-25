package concurrence;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class AtomicIntegerFieldUpdaterDemo3 {

	public static void main(String[] args) throws InterruptedException {

		AtomicIntegerFieldUpdater<Counter> countFieldUpdater = AtomicIntegerFieldUpdater
				.newUpdater(Counter.class, "count");

		Counter test = new Counter();
		for (int i = 0; i < 10; i++)
			new Thread(() -> {
				for (int j = 0; j < 10000; j++)
					countFieldUpdater.incrementAndGet(test);
			}).start();
		Thread.sleep(3000);
		System.out.println("count = " + test.getCount());

	}

	public static class Counter {
		// static error
		/*-
		 * Exception in thread "main" java.lang.IllegalArgumentException
		at sun.misc.Unsafe.objectFieldOffset(Native Method)
		at java.util.concurrent.atomic.AtomicIntegerFieldUpdater$AtomicIntegerFieldUpdaterImpl.<init>(AtomicIntegerFieldUpdater.java:426)
		at java.util.concurrent.atomic.AtomicIntegerFieldUpdater.newUpdater(AtomicIntegerFieldUpdater.java:88)
		at basic.java8.concurrence.AtomicIntegerFieldUpdaterDemo3.main(AtomicIntegerFieldUpdaterDemo3.java:19)

		 */
		volatile int count = 0;

		public int getCount() {
			return count;
		}
	}

}