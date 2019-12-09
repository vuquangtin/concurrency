package blockingqueues.delayqueues;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;

public class DelayQueueExample {
	public static void main(String[] args) {
		final BlockingQueue<DelayElement> queue = new DelayQueue<DelayElement>();

		DelayQueueProducer queueProducer = new DelayQueueProducer(queue);
		new Thread(queueProducer).start();

		DelayQueueConsumer queueConsumer1 = new DelayQueueConsumer(queue);
		new Thread(queueConsumer1).start();

		DelayQueueConsumer queueConsumer2 = new DelayQueueConsumer(queue);
		new Thread(queueConsumer2).start();

	}
}