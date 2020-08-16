package concurrency.java.optimize.test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import concurrency.java.optimize.MyRunnable;
import concurrency.java.optimize.tasks.PriorityThreadFactory;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class BlockingThreadPoolExecutorTest {

	private final BlockingThreadPoolExecutor blockingExecutor;
	private final ThreadPoolExecutor executor;

	public BlockingThreadPoolExecutorTest() {
		int corePoolSize = 3;
		int maxPoolSize = 6;
		int queueCapacity = 2;

		System.out.println("Creating BlockingThreadPoolExecutor and ThreadPoolExecutor");
		blockingExecutor = new BlockingThreadPoolExecutor(corePoolSize, maxPoolSize, Integer.MAX_VALUE,
				TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(queueCapacity),
				new PriorityThreadFactory("workerThread"));
		executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, Integer.MAX_VALUE, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(queueCapacity), new PriorityThreadFactory("workerThread"));
	}

	public static void main(String[] args) {
		new BlockingThreadPoolExecutorTest().start();
	}

	public void start() {
		int rejectTaskCount = 0;

		System.out.println("ThreadPoolExecutor start:");
		for (int i = 0; i < 10; i++) {
			try {
				createAndInvokeTask("thread" + i, executor);
			} catch (RejectedExecutionException ex) {
				System.out.println("task" + i + ": rejected");
				rejectTaskCount++;
			}
		}
		System.out.println("ThreadPoolExecutor done, total task rejections: " + rejectTaskCount);

		System.out.println("BlockingThreadPoolExecutor start:");
		rejectTaskCount = 0;
		for (int i = 0; i < 10; i++) {
			try {
				createAndInvokeTask("thread" + i, blockingExecutor);
			} catch (RejectedExecutionException ex) {
				System.out.println("task" + i + ": rejected");
				rejectTaskCount++;
			}
		}
		System.out.println("BlockingThreadPoolExecutor done, total task rejections: " + rejectTaskCount);
	}

	private void createAndInvokeTask(final String name, ThreadPoolExecutor executor) {
		executor.execute(new MyRunnable(name));
	}
}