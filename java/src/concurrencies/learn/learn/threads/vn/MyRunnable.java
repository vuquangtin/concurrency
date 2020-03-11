package learn.threads.vn;

import java.util.Arrays;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class MyRunnable implements Runnable {

	@Override
	public void run() {
		try {
			String threadName = Thread.currentThread().getName();
			System.out.println("Running in " + threadName);
			Thread.sleep(5000);
			System.out.println("Finish in " + threadName);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void runThread() {
		MyRunnable runnable1 = new MyRunnable();
		MyRunnable runnable2 = new MyRunnable();
		Thread thread1 = new Thread(runnable1);
		Thread thread2 = new Thread(runnable2);
		Arrays.asList(thread1, thread2).parallelStream().forEach(Thread::start);
	}
}
