package deadlocks;

import java.util.Random;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class Deadlock implements Runnable {
	private static final Object resource1 = new Object();
	private static final Object resource2 = new Object();
	private final Random random = new Random(System.currentTimeMillis());

	public static void main(String[] args) {
		Thread myThread1 = new Thread(new Deadlock(), "thread-1");
		Thread myThread2 = new Thread(new Deadlock(), "thread-2");
		myThread1.start();
		myThread2.start();
	}

	public void run() {
		for (int i = 0; i < 10000; i++) {
			boolean b = random.nextBoolean();
			if (b) {
				System.out.println("[" + Thread.currentThread().getName()
						+ "] Trying to lock resource 1.");
				synchronized (resource1) {
					System.out.println("[" + Thread.currentThread().getName()
							+ "] Locked resource 1.");
					System.out.println("[" + Thread.currentThread().getName()
							+ "] Trying to lock resource 2.");
					synchronized (resource2) {
						System.out.println("["
								+ Thread.currentThread().getName()
								+ "] Locked resource 2.");
					}
				}
			} else {
				System.out.println("[" + Thread.currentThread().getName()
						+ "] Trying to lock resource 2.");
				synchronized (resource2) {
					System.out.println("[" + Thread.currentThread().getName()
							+ "] Locked resource 2.");
					System.out.println("[" + Thread.currentThread().getName()
							+ "] Trying to lock resource 1.");
					synchronized (resource1) {
						System.out.println("["
								+ Thread.currentThread().getName()
								+ "] Locked resource 1.");
					}
				}
			}
		}
	}
}
