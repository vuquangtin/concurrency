package blockingqueues.examples;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class PriorityBlockingQueueExample {

	static class PriorityElement<V> implements Comparable<PriorityElement> {

		private int priorityLevel;
		private V data;

		PriorityElement(int priorityLevel, V data) {
			this.priorityLevel = priorityLevel;
			this.data = data;
		}

		public int getPriorityLevel() {
			return this.priorityLevel;
		}

		@Override
		public int compareTo(PriorityElement o) {
			return o.getPriorityLevel() - this.getPriorityLevel();
		}

		public V getData() {
			return data;
		}
	}

	private static BlockingQueue<PriorityElement<String>> blockingQueue = new PriorityBlockingQueue<>();

	public static void main(String[] args) throws InterruptedException {
		PriorityElement high = new PriorityElement(10, "HIGH");
		PriorityElement mid = new PriorityElement(5, "MID");
		PriorityElement low = new PriorityElement(1, "LOW");

		blockingQueue.put(high);
		blockingQueue.put(low);
		blockingQueue.put(mid);

		System.out.println(blockingQueue.take().getData());
		System.out.println(blockingQueue.take().getData());
		System.out.println(blockingQueue.take().getData());
	}
}