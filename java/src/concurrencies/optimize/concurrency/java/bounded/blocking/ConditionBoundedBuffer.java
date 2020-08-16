package concurrency.java.bounded.blocking;

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
public class ConditionBoundedBuffer<T> {

	protected final Lock lock = new ReentrantLock();
	private final Condition notFull = lock.newCondition();
	private final Condition notEmpty = lock.newCondition();

	private final T[] items = (T[]) new Object[10];
	private int head, tail, count;

	public void put(T elem) throws InterruptedException {
		lock.lock();
		try {
			while (count == items.length) {
				notFull.await();
			}
			items[tail] = elem;
			if (++tail == items.length) {
				tail = 0;
			}
			++count;
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	public T take() throws InterruptedException {
		lock.lock();
		try {
			while (count == 0) {
				notEmpty.await();
			}
			T elem = items[head];
			items[head] = null;
			if (++head == items.length) {
				head = 0;
			}
			--count;
			notFull.signal();
			return elem;
		} finally {
			lock.unlock();
		}
	}
}