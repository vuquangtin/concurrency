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
public class AtomicIntegerArrayDemo3 {

	static AtomicInteger[] counts = new AtomicInteger[2];

	public static void main(String[] args) throws InterruptedException {
		counts[0] = new AtomicInteger();
		counts[1] = new AtomicInteger();
		for (int i = 0; i < 10; i++)
			new Thread(() -> {
				for (int j = 0; j < 10000; j++) {
					counts[0].getAndAdd(1);
					counts[1].getAndAdd(1);
				}
			}).start();
		Thread.sleep(3000);
		System.out.println("counts[0] = " + counts[0].get());
		System.out.println("counts[1] = " + counts[1].get());
	}
}