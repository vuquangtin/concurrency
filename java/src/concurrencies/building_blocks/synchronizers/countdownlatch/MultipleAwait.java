package synchronizers.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class MultipleAwait {

	public static void main(String[] args) {

		CountDownLatch latch = new CountDownLatch(3);
		Thread one = new Thread(new Runner(latch), "one");
		Thread two = new Thread(new Runner(latch), "two");
		Thread three = new Thread(new Runner(latch), "three");
		for (int i = 0; i < 10; i++) {
			new Thread(new AwaitRunner(latch), "one").start();
		}
		// Starting all the threads
		one.start();
		two.start();
		three.start();
		try {
			latch.await();
			latch.await();
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("DONE");

	}

	static class Runner implements Runnable {

		CountDownLatch latch;

		public Runner(CountDownLatch latch) {
			this.latch = latch;
		}

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName()
					+ " is Waiting.");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			latch.countDown();
			System.out.println(Thread.currentThread().getName()
					+ " is Completed.");
		}
	}

	static class AwaitRunner implements Runnable {

		CountDownLatch latch;

		public AwaitRunner(CountDownLatch latch) {
			this.latch = latch;
		}

		@Override
		public void run() {
			try {
				latch.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()
					+ " await is Completed.");
		}
	}
}
