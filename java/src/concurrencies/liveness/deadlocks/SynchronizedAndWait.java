package deadlocks;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class SynchronizedAndWait {
	private static final Queue<Integer> queue = new ConcurrentLinkedQueue<Integer>();

	public synchronized Integer getNextInt() {
		Integer retVal = null;
		while (retVal == null) {
			synchronized (queue) {
				try {
					queue.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				retVal = queue.poll();
			}
		}
		return retVal;
	}

	public synchronized void putInt(Integer value) {
		synchronized (queue) {
			queue.add(value);
			queue.notify();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		final SynchronizedAndWait queue = new SynchronizedAndWait();
		Thread thread1 = new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 10; i++) {
					queue.putInt(i);
				}
			}
		});
		Thread thread2 = new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 10; i++) {
					Integer nextInt = queue.getNextInt();
					System.out.println("Next int: " + nextInt);
				}
			}
		});
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();
	}
	public Integer getCNextInt() {
		Integer retVal = null;
		synchronized (queue) {
			try {
				while (queue.isEmpty()) {
					queue.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		synchronized (queue) {
			retVal = queue.poll();
			if (retVal == null) {
				System.err.println("retVal is null");
				throw new IllegalStateException();
			}
		}
		return retVal;
	}
	public Integer getBNextInt() {
		Integer retVal = null;
		synchronized (queue) {
			try {
				while (queue.isEmpty()) {
					queue.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			retVal = queue.poll();
		}
		return retVal;
	}
	String str = "abc";

	String substr = str.substring(1);
}