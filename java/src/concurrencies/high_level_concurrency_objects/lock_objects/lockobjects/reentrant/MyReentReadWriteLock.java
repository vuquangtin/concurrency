package lockobjects.reentrant;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class MyReentReadWriteLock {

	private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private static ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
	private static ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
	private static Map<String, String> maps = new HashMap<>();
	private static CountDownLatch latch = new CountDownLatch(102);
	private static CyclicBarrier cyclicBarrier = new CyclicBarrier(102);

	public static void main(String[] args) throws InterruptedException {
		long beginTime = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			new Thread(new ReadThread()).start();
		}
		for (int i = 0; i < 100; i++) {
			new Thread(new WriteThread()).start();
		}
		latch.await();
		long endTime = System.currentTimeMillis();
		System.out.println("Consumer Time is " + (endTime - beginTime) + "ms");
	}

	static class WriteThread implements Runnable {
		@Override
		public void run() {
			try {
				cyclicBarrier.await();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				writeLock.lock();
				maps.put("1", "2");
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				writeLock.unlock();
			}
			latch.countDown();
		}
	}

	static class ReadThread implements Runnable {
		@Override
		public void run() {
			try {
				cyclicBarrier.await();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				readLock.lock();
				maps.get("1");
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				readLock.unlock();
			}
			latch.countDown();
		}
	}
}