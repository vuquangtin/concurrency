package common;

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
public class MultiThreadShareData1 {

	public static void main(String[] args) {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 12, 10, TimeUnit.SECONDS,
				new ArrayBlockingQueue<>(2), new ThreadPoolExecutor.DiscardOldestPolicy());

		final Sharedata sharedata = new Sharedata();
		for (int i = 0; i < 5; i++) {

			threadPoolExecutor.execute(() -> sharedata.inc());

			threadPoolExecutor.execute(() -> sharedata.dec());
		}
		threadPoolExecutor.shutdown();
	}

	static class Sharedata {

		private int count = 10;

		public synchronized void inc() {

			count++;
			System.out.println("线程进行了加操作" + count);

		}

		public synchronized void dec() {

			count--;
			System.out.println("线程进行了减操作" + count);

		}
	}
}