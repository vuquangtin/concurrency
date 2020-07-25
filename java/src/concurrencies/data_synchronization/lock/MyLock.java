package lock;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class MyLock {

	static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(3, 4, 3, TimeUnit.SECONDS,
			new ArrayBlockingQueue<>(3), new ThreadPoolExecutor.DiscardOldestPolicy());

	public static void init() {
		final OupPrint oupPrint = new OupPrint();
		threadPool.execute(() -> {
			try {
				while (true) {
					Thread.sleep(1000);
					oupPrint.out("hadoop");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		threadPool.execute(() -> {
			try {
				while (true) {
					Thread.sleep(1000);
					oupPrint.out("spark");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

	}

	static class OupPrint {
		Lock lock = new ReentrantLock();

		public void out(String str) {
			lock.lock();
			try {
				for (int i = 0; i < str.length(); i++) {
					System.out.print(str.charAt(i));
				}
				System.out.println();
			} finally {
				// 之前释放锁出现异常将不会释放
				lock.unlock();
			}
		}
	}

	public static void main(String[] args) {
		MyLock.init();
	}
}
