package concurrence;

import java.util.concurrent.CountDownLatch;
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
public class AtomicTest {
	public static AtomicInteger atc = new AtomicInteger();
	public static CountDownLatch cd = new CountDownLatch(1);

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.err.println(atc.incrementAndGet());
		}
		atc.set(0);
		System.err.println(atc.get());

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("thread");
				cd.countDown();
				// atc.decrementAndGet();
			}
		});

		thread.start();

		try {
			cd.await();
		} catch (InterruptedException e) {
		}
		System.out.println("hhhhhh");
		// atc.compareAndSet(0, atc.get());

		Thread thread1 = new Thread(new Runnable() {

			@Override
			public void run() {
				int i = atc.get();
				System.err.println("thread1: " + i);
				System.err.println("thread1: " + atc.incrementAndGet());
			}
		});

		Thread thread2 = new Thread(new Runnable() {

			@Override
			public void run() {
				int i = atc.get();
				System.err.println("thread2: " + i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.err.println("thread2: " + atc.incrementAndGet());
			}
		});
		thread2.start();
		thread1.start();

	}
}
