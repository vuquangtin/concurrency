package concurrency.java.optimize.test2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
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
public class LimitedQueueExchangeTest {

	public static void main(String[] args) throws InterruptedException {

		/**
		 *
		 * 1.可以设置totalWorks[<,=,>]workQueueCapacity这3种情况 2.可以设置workQueue =
		 * ArrayBlockingQueue与workQueue = LimitedQueue这2种情况
		 *
		 * 综上看看这6种情况
		 *
		 * 结论：totalWorks[>]workQueueCapacity时只能选择LimitedQueue
		 *
		 */

		int totalWorks = 20; // 任务总数
		int workQueueCapacity = 5; // 任务队列容量

		List<String> keys = keys(totalWorks);

		final CountDownLatch latch = new CountDownLatch(keys.size());

		// BlockingQueue<Runnable> workQueue = new
		// ArrayBlockingQueue<>(workQueueCapacity); // 当队列满的时候会拒绝,会抛出异常
		BlockingQueue<Runnable> workQueue = new LimitedQueue<>(workQueueCapacity); // 重写offer方法

		int nThreads = Runtime.getRuntime().availableProcessors() + 1; // 本电脑为4核
		ThreadPoolExecutor service = new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, workQueue);

		for (String pid : keys) {
			service.execute(new RefreshWork(pid, latch));
			// TimeUnit.SECONDS.sleep(1);
			System.out.println("--------------workQueue.size = " + service.getQueue().size());
		}

		latch.await(); // 拦住等待执行结束
		service.shutdown();
		System.out.println("finished...");
	}

	
	private static List<String> keys(int totalWorks) {
		List<String> keys = new ArrayList<>();

		for (int i = 0; i < totalWorks; i++) {
			keys.add(String.valueOf(i));
		}

		return keys;
	}

	private static void refreshProperty(String pid) {
		System.out.println(Thread.currentThread().getName() + ", pid = " + pid);
	}

	/**
	 *
	 * 刷新的工作
	 *
	 */
	static class RefreshWork implements Runnable {

		private final String pid;
		private final CountDownLatch latch;

		public RefreshWork(String pid, CountDownLatch latch) {
			this.pid = pid;
			this.latch = latch;
		}

		@Override
		public void run() {
			try {
				refreshProperty(pid);
				TimeUnit.SECONDS.sleep(1); // 模拟执行需要的时间
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				latch.countDown(); // 无论何种情况,最后都减少一个计数
			}

		}
	}

	/**
	 *
	 * 将offer方法变成阻塞式的
	 *
	 */
	static class LimitedQueue<E> extends LinkedBlockingQueue<E> {
		public LimitedQueue(int maxSize) {
			super(maxSize);
		}

		@Override
		public boolean offer(E e) {
			// http://www.aichengxu.com/other/6569886.htm
			// http://stackoverflow.com/questions/4521983/java-executorservice-that-blocks-on-submission-after-a-certain-queue-size
			// turn offer() and add() into a blocking calls (unless interrupted)
			try {
				put(e);
				return true;
			} catch (InterruptedException ie) {
				Thread.currentThread().interrupt();
			}
			return false;
		}
	}
}