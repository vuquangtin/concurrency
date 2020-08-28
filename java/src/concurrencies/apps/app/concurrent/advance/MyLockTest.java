package app.concurrent.advance;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class MyLockTest {
	public static int COUNT = 0;

	public static void main(String[] args) {
		ExecutorService ex = Executors.newFixedThreadPool(10);
		MyLock lock = new MyLock();
		for (int i = 0; i < 1000; i++) {
			//System.out.println(i);
			ex.execute(() -> {
				try {
					lock.lock();
					COUNT++;
					lock.unlock();
				} catch (Exception e) {
					e.printStackTrace();
				}

			});
		}
		ex.shutdown();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(COUNT);

	}

	static class MyLock {
		private boolean isLocked = false;

		public synchronized void lock() throws InterruptedException {
			while (isLocked) {
				wait();
			}
			isLocked = true;
		}

		public synchronized void unlock() {
			isLocked = false;
			notify();
		}
	}
}
