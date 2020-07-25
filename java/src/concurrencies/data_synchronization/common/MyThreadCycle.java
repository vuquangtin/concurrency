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
public class MyThreadCycle {

	public static void main(String[] args) {
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(3, 4, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<>(3),
				new ThreadPoolExecutor.DiscardOldestPolicy());

		final Common common = new Common();
		threadPool.execute(() -> {
			for (int i = 1; i <= 50; i++) {
				common.sub(i);
			}
		});

		for (int i = 1; i <= 50; i++) {
			common.main(i);
		}
		threadPool.shutdown();
	}
}

class Common {
	private boolean sub = true;

	public synchronized void sub(int i) {
		// 用while而不用if可以避免虚假唤醒
		while (!sub) {
			try {
				this.wait(); // 等待，主main运行完
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int j = 1; j <= 10; j++) {
			System.out.println("sub  " + j + " loop of " + i);
		}
		sub = false;
		this.notify();
	}

	public synchronized void main(int i) {
		while (sub) {
			try {
				this.wait(); // 等待让sub运行完
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int j = 1; j <= 10; j++) {
			System.out.println("main " + j + " loop of  " + i);
		}
		sub = true;
		this.notify();
	}
}