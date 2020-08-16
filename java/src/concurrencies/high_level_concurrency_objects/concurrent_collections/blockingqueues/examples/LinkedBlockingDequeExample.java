package blockingqueues.examples;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class LinkedBlockingDequeExample {
	private static BlockingDeque<String> integerBlockingDeque = new LinkedBlockingDeque<>();

	public static void main(String[] args) {
		Thread startThread = new Thread(LinkedBlockingDequeExample::runStartEndThread);
		Thread endThread = new Thread(LinkedBlockingDequeExample::runLastEndThread);
		startThread.start();
		endThread.start();
	}

	private static void runStartEndThread() {
		Thread.currentThread().setName("START-THREAD");
		try {
			for (int i = 1; i <= 10; i++) {
				integerBlockingDeque.putFirst(Thread.currentThread().getName() + ":" + i);
				System.out.println("Thread: " + Thread.currentThread().getName() + " Produced: " + i + " Consumed: "
						+ integerBlockingDeque.takeLast());
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void runLastEndThread() {
		Thread.currentThread().setName("END-THREAD");
		try {
			for (int i = 1; i <= 10; i++) {
				integerBlockingDeque.putLast(Thread.currentThread().getName() + ":" + i);
				System.out.println("Thread: " + Thread.currentThread().getName() + " Produced: " + i + " Consumed: "
						+ integerBlockingDeque.takeFirst());
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}