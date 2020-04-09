package raceconditions;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class MyCondition {
	public static void main(String[] args) {

		final Commoned common = new Commoned();
		new Thread(new Runnable() {
			public void run() {
				for (int i = 1; i <= 50; i++) {
					common.sub(i);
				}
			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				for (int i = 1; i <= 50; i++) {
					common.sub2(i);
				}
			}
		}).start();

		for (int i = 1; i <= 50; i++) {
			common.main(i);
		}

	}
}

class Commoned {
	private boolean sub = true;
	private boolean sub2 = false;
	Lock lock = new ReentrantLock();
	Condition mainCondition = lock.newCondition();
	Condition subCondition = lock.newCondition();
	Condition sub2Condition = lock.newCondition();

	public void sub(int i) {
		try {
			lock.lock();
			while (!sub) { // 用while而不用if可以避免虚假唤醒
				try {
					subCondition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for (int j = 1; j <= 5; j++) {
				System.out.println("sub  " + j + " loop of " + i);
			}
			sub = false;
			sub2 = true;
			sub2Condition.signal();
		} finally {
			lock.unlock();
		}
	}

	public void main(int i) {
		try {
			lock.lock();
			while (sub2 || sub) {
				try {
					mainCondition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for (int j = 1; j <= 10; j++) {
				System.out.println("main " + j + " loop of  " + i);
			}
			sub = true;
			subCondition.signal();
		} finally {
			lock.unlock();
		}
	}

	public void sub2(int i) {
		try {
			lock.lock();
			while (!sub2) {
				try {
					sub2Condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for (int j = 1; j <= 15; j++) {
				System.out.println("sub2 " + j + " loop of  " + i);
			}
			sub2 = false;
			mainCondition.signal();
		} finally {
			lock.unlock();
		}
	}
}