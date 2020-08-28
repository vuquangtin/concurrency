package lockobjects.reentrant;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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
public class MyReadWriteLock2 {

	public static void main(String[] args) {
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10, 14, 10, TimeUnit.SECONDS,
				new ArrayBlockingQueue<>(10), new ThreadPoolExecutor.DiscardOldestPolicy());
		ThreadPoolExecutor threadPool2 = new ThreadPoolExecutor(10, 14, 10, TimeUnit.SECONDS,
				new ArrayBlockingQueue<>(10), new ThreadPoolExecutor.DiscardOldestPolicy());

		final DataSave ds = new DataSave();
		for (int i = 0; i <= 10; i++) {
			threadPool.execute(() -> ds.put(new Random().nextLong()));

			threadPool2.execute(() -> ds.get());
		}
	}

	static class DataSave {
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		private long age;

		public void put(long age) {
			lock.writeLock().lock(); // 上写锁
			try {
				Thread.sleep((long) Math.random() * 500);
				this.age = age;
				System.out.println(Thread.currentThread().getName() + "写入数据值为：" + age);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.writeLock().unlock();
			}
		}

		public void get() {
			lock.readLock().lock();
			try {
				Thread.sleep((long) Math.random() * 1000);
				System.out.println(Thread.currentThread().getName() + "读取数据值为：" + age);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.readLock().unlock();
			}
		}
	}
}