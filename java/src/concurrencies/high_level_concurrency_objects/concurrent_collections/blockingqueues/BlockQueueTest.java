package blockingqueues;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jackyuj
 */
public class BlockQueueTest {

	public BlockQueueTest() {
		super();
	}

	public static void main(String[] args) throws InterruptedException {

		// 声明一个容量为10的缓存队列
		BlockingQueue<Object> queue = new LinkedBlockingQueue<Object>(10);

		Producer producer1 = new Producer(queue);
		Producer producer2 = new Producer(queue);
		Producer producer3 = new Producer(queue);
		Consumer consumer = new Consumer(queue);

		// 借助Executors
		ExecutorService service = Executors.newCachedThreadPool();
		// 启动线程
		service.execute(producer1);
		service.execute(producer2);
		service.execute(producer3);
		service.execute(consumer);

		// 执行10s
		Thread.sleep(10 * 1000);
		producer1.stop();
		producer2.stop();
		producer3.stop();

		Thread.sleep(2000);
		// 退出Executor
		service.shutdown();
	}
}

/**
 * 消费者线程
 *
 * @author jackyuj
 */
class Consumer implements Runnable {

	private BlockingQueue<Object> queue;
	private static final int DEFAULT_RANGE_FOR_SLEEP = 1000;

	public Consumer(BlockingQueue<Object> queue) {
		this.queue = queue;
	}

	public void run() {
		System.out.println("启动消费者线程！");
		Random r = new Random();
		boolean isRunning = true;
		try {
			while (isRunning) {
				System.out.println("正从队列获取数据...");
				String data = (String) queue.poll(2, TimeUnit.SECONDS);
				if (null != data) {
					System.out.println("拿到数据：" + data);
					System.out.println("正在消费数据：" + data);
					Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));
				} else {
					// 超过2s还没数据，认为所有生产线程都已经退出，自动退出消费线程。
					isRunning = false;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		} finally {
			System.out.println("退出消费者线程！");
		}
	}

}

/**
 * 生产者线程
 *
 * @author jackyuj
 */
class Producer implements Runnable {
	private volatile boolean isRunning = true;
	private BlockingQueue<Object> queue;
	private static AtomicInteger count = new AtomicInteger();
	private static final int DEFAULT_RANGE_FOR_SLEEP = 1000;

	public Producer(BlockingQueue<Object> queue) {
		this.queue = queue;
	}

	public void run() {
		String data = null;
		Random r = new Random();
		System.out.println("启动生产者线程！");
		try {
			while (isRunning) {
				System.out.println("正在生产数据...");
				Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));

				data = "data:" + count.incrementAndGet();
				System.out.println("将数据：" + data + "放入队列...");
				if (!queue.offer(data, 2, TimeUnit.SECONDS)) {
					System.out.println("放入数据失败：" + data);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		} finally {
			System.out.println("退出生产者线程！");
		}
	}

	public void stop() {
		isRunning = false;
	}

}