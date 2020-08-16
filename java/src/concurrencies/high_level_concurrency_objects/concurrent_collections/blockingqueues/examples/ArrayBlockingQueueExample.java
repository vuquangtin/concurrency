package blockingqueues.examples;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ArrayBlockingQueueExample {
	private static BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(1);
	private static boolean isProducerRunning = true;

	public static void main(String[] args) {
		Thread producer = new Thread(ArrayBlockingQueueExample::runProducer);
		Thread consumer = new Thread(ArrayBlockingQueueExample::runConsumer);

		producer.start();
		consumer.start();
	}

	private static void runProducer() {
		Thread.currentThread().setName("Producer");
		for (int i = 1; i <= 100; i += 2) {
			try {
				blockingQueue.put(i);
				System.out.println("Produced: " + i);
				blockingQueue.put(i + 1);
				System.out.println("Produced: " + (i + 1));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		isProducerRunning = false;
	}

	private static void runConsumer() {
		Thread.currentThread().setName("Consumer");
		int val;
		while (isProducerRunning) {
			try {
				Thread.sleep(1000);
				val = blockingQueue.take();
				System.out.println("Consumed: " + val);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
