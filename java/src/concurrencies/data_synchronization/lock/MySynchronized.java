package lock;

import java.util.concurrent.ArrayBlockingQueue;
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
public class MySynchronized {

	static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 2, 2, TimeUnit.SECONDS,
			new ArrayBlockingQueue<>(3), new ThreadPoolExecutor.DiscardOldestPolicy());

	public static void init() {

		threadPool.execute(() -> {
			try {
				while (true) {
					Thread.sleep(1000);
					OutPrint.out("hadoop");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		threadPool.execute(() -> {
			try {
				while (true) {
					Thread.sleep(1000);
					OutPrint.out("spark");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

	}

	static class OutPrint {
		public static void out(String str) {
			synchronized (OutPrint.class) {
				for (int i = 0; i < str.length(); i++) {
					System.out.print(str.charAt(i));
				}
				System.out.println();
			}
		}
	}

	public static void main(String[] args) {
		MySynchronized.init();
	}
}