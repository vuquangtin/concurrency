package concurrence;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class AtomicIntegerArrayDemo2 {

	static AtomicIntegerArray counts = new AtomicIntegerArray(2);

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++)
			new Thread(() -> {
				for (int j = 0; j < 10000; j++) {
					counts.incrementAndGet(0);
					counts.incrementAndGet(1);
				}
			}).start();
		Thread.sleep(3000);
		System.out.println("counts[0] = " + counts.get(0));
		System.out.println("counts[1] = " + counts.get(1));
	}
}