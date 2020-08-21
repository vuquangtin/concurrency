package concurrency.java.optimize.executor;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ExecutorMonitor {

	public static void main(String[] args) throws Exception {

		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
				.newCachedThreadPool();

		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			Task task = new Task(random.nextInt(10000));
			executor.submit(task);
		}

		for (int i = 0; i < 5; i++) {
			showLog(executor);
			TimeUnit.SECONDS.sleep(1);
		}

		executor.shutdown();

		for (int i = 0; i < 5; i++) {
			showLog(executor);
			TimeUnit.SECONDS.sleep(1);
		}

		executor.awaitTermination(1, TimeUnit.DAYS);

		System.out.printf("Main: End of the program.\n");

	}

	private static void showLog(ThreadPoolExecutor executor) {
		System.out.printf("*********************");
		System.out.printf("Main: Executor Log");
		System.out.printf("Main: Executor: Core Pool Size: %d\n",
				executor.getCorePoolSize());
		System.out.printf("Main: Executor: Pool Size: %d\n",
				executor.getPoolSize());
		System.out.printf("Main: Executor: Active Count: %d\n",
				executor.getActiveCount());
		System.out.printf("Main: Executor: Task Count: %d\n",
				executor.getTaskCount());
		System.out.printf("Main: Executor: Completed Task Count: %d\n",
				executor.getCompletedTaskCount());
		System.out.printf("Main: Executor: Shutdown: %s\n",
				executor.isShutdown());
		System.out.printf("Main: Executor: Terminating: %s\n",
				executor.isTerminating());
		System.out.printf("Main: Executor: Terminated: %s\n",
				executor.isTerminated());
		System.out.printf("*********************\n");
	}

	/**
	 * Task implemented to log data about an Executor
	 */
	public static class Task implements Runnable {

		private long milliseconds;

		public Task(long milliseconds) {
			this.milliseconds = milliseconds;
		}

		@Override
		public void run() {

			System.out.printf("%s: Begin\n", Thread.currentThread().getName());
			try {
				TimeUnit.MILLISECONDS.sleep(milliseconds);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.printf("%s: End\n", Thread.currentThread().getName());

		}

	}

}