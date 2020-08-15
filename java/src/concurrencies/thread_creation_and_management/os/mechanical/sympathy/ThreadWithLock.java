package os.mechanical.sympathy;

import java.util.concurrent.locks.ReentrantLock;

import com.google.common.base.Preconditions;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreadWithLock {
	public static void singleThreadWithLock(int MAX) {
		ReentrantLock lock = new ReentrantLock();

		int counter = 0;
		for (int i = 0; i < MAX; i++) {
			lock.lock();
			try {
				counter = counter + 1;
			} finally {
				lock.unlock();
			}
		}
	}

	static int sharedCounter = 0;

	public void twoThreadWithLock(int MAX) throws Exception {
		ReentrantLock lock = new ReentrantLock();

		// First thread
		final Thread t1 = new Thread(() -> {
			for (int i = 0; i < MAX / 2; i++) {
				lock.lock();
				try {
					sharedCounter = sharedCounter + 1;
				} finally {
					lock.unlock();
				}
			}
		});

		// Second thread
		final Thread t2 = new Thread(() -> {
			for (int i = 0; i < MAX / 2; i++) {
				lock.lock();
				try {
					sharedCounter = sharedCounter + 1;
				} finally {
					lock.unlock();
				}
			}
		});

		// Start threads
		t1.start();
		t2.start();

		// Wait threads
		t1.join();
		t2.join();
	}

	/** Main lock guarding all access */
	static ReentrantLock lock;

//	public static <E> void put(E e,int count) throws InterruptedException {
//		Preconditions.checkNotNull(e);
//		final ReentrantLock lock = ThreadWithLock.lock;
//		lock.lockInterruptibly(); // Lock accesses
//		try {
//			while (count == items.length)
//				notFull.await();
//			enqueue(e); // See method below
//		} finally {
//			lock.unlock(); // Unlock
//		}
//	}
//	static Object[] items;
//	private static <E> void enqueue(E x,int putIndex,int count) {
//		final Object[] items = ThreadWithLock.items;
//		items[putIndex] = x;
//		if (++putIndex == items.length)
//			putIndex = 0;
//		count++;
//		notEmpty.signal(); // Send signal to indicate a change
//	}
}
