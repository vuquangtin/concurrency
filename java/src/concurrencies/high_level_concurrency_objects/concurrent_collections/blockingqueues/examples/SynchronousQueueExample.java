package blockingqueues.examples;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class SynchronousQueueExample {
	private static BlockingQueue<Integer> blockingQueue = new SynchronousQueue<>();
	// Synchronous Queue has a size of only one Element.

	public static void main(String[] args) {
		Thread producer = new Thread(SynchronousQueueExample::runProducer);
		Thread consumer = new Thread(SynchronousQueueExample::runConsumer);

		producer.start();
		consumer.start();
	}

	private static void runProducer() {
		for (int i = 1; i <= 10; i++) {
			try {
				System.out.println("Produced: " + i);
				blockingQueue.put(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static void runConsumer() {
		int n = 10;
		while (n-- > 0) {
			try {
				Thread.sleep(1000);
				System.out.println("Consumed: " + blockingQueue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}