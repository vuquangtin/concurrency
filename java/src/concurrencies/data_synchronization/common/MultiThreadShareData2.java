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
public class MultiThreadShareData2 {

	private int count = 10;

	public synchronized void inc() {
		count++;
		System.out.println("线程进行了加操作" + count);
	}

	public synchronized void dec() {
		count--;
		System.out.println("线程进行了减操作" + count);
	}

	public static void main(String[] args) {
		ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 12, 20, TimeUnit.SECONDS,
				new ArrayBlockingQueue<>(10), new ThreadPoolExecutor.DiscardOldestPolicy());

		MultiThreadShareData2 multiThreadShareData2 = new MultiThreadShareData2();

		for (int i = 0; i < 5; i++) {
			poolExecutor.execute(multiThreadShareData2.new Threadinc());
			poolExecutor.execute(multiThreadShareData2.new Threaddec());
		}

		System.out.println("thread execute over!!");
		poolExecutor.shutdown();

	}

	class Threadinc implements Runnable {
		@Override
		public void run() {
			inc();
		}
	}

	class Threaddec implements Runnable {
		@Override
		public void run() {
			dec();
		}
	}
}