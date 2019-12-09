package blockingqueues.delayqueues;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

public class DelayQueueProducer implements Runnable {

	protected BlockingQueue<DelayElement> blockingQueue;
	final Random random = new Random();

	public DelayQueueProducer(BlockingQueue<DelayElement> queue) {
		this.blockingQueue = queue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				int delay = random.nextInt(100);
				DelayElement delayElement = new DelayElement(UUID.randomUUID()
						.toString(), delay);
				System.out.println("Put: "+ delayElement);
				blockingQueue.put(delayElement);
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}