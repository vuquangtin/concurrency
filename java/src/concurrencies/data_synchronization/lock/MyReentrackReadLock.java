package lock;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class MyReentrackReadLock implements Runnable {

	/**
	 * 在读写锁中读线程是允许多个的，所以用map
	 */
	Map<Thread, Integer> mapReadLock;
	private int write = 0;
	private int writeRequest = 0;

	public MyReentrackReadLock() {
		mapReadLock = new HashMap<>();
	}

	public Boolean isRead(Thread readThread) { // 判断是否有读线程，这是读锁重入的条件之一
		return mapReadLock.get(readThread) != null;
	}

	public Boolean isCanGetReadAccess(Thread readThread) { // 判断读锁的重入的条件是否都满足
		if (write > 0) {
			return false;
		}
		if (writeRequest > 0) {
			return false;
		}
		if (isRead(readThread)) {
			return true;
		}
		// 第一次时以上条件都不满足
		return true;
	}

	public int getReadThreadCount(Thread readThread) { // 获取读线程的总数
		Integer count = mapReadLock.get(readThread);
		return count == null ? 0 : count.intValue();
	}

	public void readLock() throws InterruptedException {
		Thread readThread = Thread.currentThread();
		System.out.println(isCanGetReadAccess(readThread));
		if (!isCanGetReadAccess(readThread)) {
			wait();
		}
		mapReadLock.put(readThread, getReadThreadCount(readThread) + 1);
	}

	public void unReadLock() {
		Thread thread = Thread.currentThread();
		if (getReadThreadCount(thread) == 1) {
			mapReadLock.remove(thread);
		} else {
			mapReadLock.put(thread, getReadThreadCount(thread) - 1);
		}
		notifyAll();
	}

	@Override
	public void run() { // 等于readLock
		Thread readThread = Thread.currentThread();
		if (!isCanGetReadAccess(readThread)) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		mapReadLock.put(readThread, getReadThreadCount(readThread) + 1);
	}

	public static void main(String[] args) {
		Runnable readThread = new MyReentrackReadLock();
		Thread thread = new Thread(readThread);
		thread.start();
	}
}