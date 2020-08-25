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
public class AtomicIntegerDemo5 {

	static AtomicInteger count = new AtomicInteger(0);

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++)
			new Thread(() -> {
				for (int j = 0; j < 10000; j++)
					count.getAndAdd(1);
			}).start();
		Thread.sleep(3000);
		System.out.println("count = " + count.get());
	}
}