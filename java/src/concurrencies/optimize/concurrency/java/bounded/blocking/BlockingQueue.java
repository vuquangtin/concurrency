package concurrency.java.bounded.blocking;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class BlockingQueue<T> {
	
	private List<T> queue = new ArrayList<>();
	private final int SIZE;
	
	public BlockingQueue(int size) {
		this.SIZE = size;
	}
	
	public synchronized void enqueue(T elt) throws InterruptedException {
		while (queue.size() == SIZE) {
			wait();
		}
		if (queue.size() == 0) {
			notifyAll(); 
		}
		queue.add(elt);
	}
	
	public synchronized T dequeue() throws InterruptedException {
		while (queue.size() == 0) {
			wait();
		}
		if (queue.size() == SIZE) {
			notifyAll();
		}
		return queue.remove(0);
	}

}