package blockingqueues.examples;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class DelayQueueExample {
	static class DelayedElement<V> implements Delayed {

		private long initTime;
		private long delay;
		private V data;

		DelayedElement(long delay, TimeUnit timeUnit, V data) {
			this.initTime = System.currentTimeMillis();
			this.delay = TimeUnit.MILLISECONDS.convert(delay, timeUnit);
			this.data = data;
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return unit.convert(((initTime + delay) - System.currentTimeMillis()), TimeUnit.MILLISECONDS);
		}

		@Override
		public int compareTo(Delayed o) {
			if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS))
				return 1;
			else if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS))
				return -1;
			else
				return 0;
		}

		public V getData() {
			return this.data;
		}
	}

	private static BlockingQueue<DelayedElement<Integer>> blockingQueue = new DelayQueue<>();

	public static void main(String[] args) {
		Thread producer = new Thread(DelayQueueExample::runProducer);
		Thread consumer = new Thread(DelayQueueExample::runConsumer);

		producer.start();
		consumer.start();
	}

	private static void runProducer() {
		for (int i = 1; i <= 10; i++) {
			DelayedElement<Integer> element = new DelayedElement(i * 1000, TimeUnit.MILLISECONDS, i);
			System.out.println("Produced: " + i);
			try {
				blockingQueue.put(element);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static void runConsumer() {
		int val;
		int n = 10;
		while (n-- > 0) {
			try {
				val = blockingQueue.take().getData();
				System.out.println("Consumed: " + val);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
