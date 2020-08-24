package synchronizers.impl;

import java.util.LinkedList;
import java.util.List;

/**
 * https://github.com/Oddys/synchronizers/tree/master/src/synchronizers
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class BlockingQueue<E> {
	private List<E> queue = new LinkedList<>();
	private int queueLimit;

	public BlockingQueue(int queueLimit) {
		this.queueLimit = queueLimit;
	}

	public synchronized void put(E task) throws InterruptedException {
		while (queue.size() == queueLimit) {
			wait();
		}
		if (queue.size() == 0) {
			notifyAll();
		}
		queue.add(task);
	}

	public synchronized E take() throws InterruptedException {
		while (queue.size() == 0) {
			wait();
		}
		if (queue.size() == queueLimit) {
			notifyAll();
		}
		return queue.remove(0);
	}

	public synchronized boolean isEmpty() {
		return queue.size() == 0;
	}
}