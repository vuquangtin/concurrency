package collections.synchronousqueue;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;

/**
 * Về cơ bản SynchronousQueue cũng giống BlockingQueue ở việc giải quyết bài
 * toán hàng đợi cho mô hình producer/consumer, tuy nhiên có một điểm khác là
 * SynchronousQueue sẽ block hành vi nhét dữ liệu vào khi có hành vi lấy dữ liệu
 * ra tương ứng. Tức là SynchronousQueue chỉ cho phép throughput là 1 item trong
 * khi BlockingQueue cho phép buffer với giới hạn trên là N. <br/>
 * SynchronousQueue hữu hiệu trong các bài toán mà bạn biết producer sẽ cho ra
 * dữ liệu với tốc độ tương đương với consumer mà hệ thống của bạn lại cần tiết
 * kiệm memory ở mức lớn nhất có thể
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class SynchronousQueueExample {

	private final SynchronousQueue<WorkItem> queue = new SynchronousQueue<WorkItem>();
	private final int consumerThreads = 5;
	private final int producerThreads = 5;

	public SynchronousQueueExample() {
		System.out.println("Starting...");

		// Start the consumers.
		for (int i = 0; i < consumerThreads; i++) {
			Consumer c = new Consumer(queue);
			c.setName("Consumer " + i);
			c.start();
			//c.interrupt();
		}

		// Start the producers.
		for (int i = 0; i < producerThreads; i++) {
			Producer p = new Producer(queue);
			p.setName("Producer " + i);
			p.start();

		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new SynchronousQueueExample();
	}

	private static class Consumer extends Thread {

		private final SynchronousQueue<WorkItem> queue;

		public Consumer(SynchronousQueue<WorkItem> queue) {
			this.queue = queue;
		}

		public void run() {
			Random r = new Random();
			while (!Thread.currentThread().isInterrupted()) {
				WorkItem item = null;
				try {
					item = queue.take();
					System.out.println(Thread.currentThread().getName() + " consuming: " + item);
					Thread.sleep(r.nextInt(1000));
				} catch (InterruptedException inte) {
					inte.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	private static class Producer extends Thread {
		private static final String[] DATA = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
				"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };

		private final SynchronousQueue<WorkItem> queue;

		public Producer(SynchronousQueue<WorkItem> queue) {
			this.queue = queue;
		}

		public void run() {
			Random r = new Random();
			while (!Thread.currentThread().isInterrupted()) {
				String data = DATA[r.nextInt(DATA.length)];
				WorkItem item = new WorkItem(data + "(" + Thread.currentThread().getName() + ")");
				try {
					Thread.sleep(r.nextInt(5000));
					System.out.println(Thread.currentThread().getName() + " queuing: " + item);
					queue.put(item);
				} catch (InterruptedException inte) {

					Thread.currentThread().interrupt();
				}
			}
		}
	}

	private static class WorkItem {

		private final String message;

		public WorkItem(String message) {
			this.message = message;
		}

		public String toString() {
			return message;
		}
	}

}
