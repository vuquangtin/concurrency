package threadpools.concretepage;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
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
public class ThreadPoolExecutorExample {
	public static void main(String[] args) {
		createAndRunPoolForQueue(new ArrayBlockingQueue<>(3), "Bounded");
		createAndRunPoolForQueue(new LinkedBlockingDeque<>(), "Unbounded");
		createAndRunPoolForQueue(new SynchronousQueue<>(), "Direct hand-off");
	}

	private static void createAndRunPoolForQueue(BlockingQueue<Runnable> queue, String msg) {
		System.out.println("---- " + msg + " queue instance = " + queue.getClass() + " " + "-------------");

		ThreadPoolExecutor e = new ThreadPoolExecutor(2, 5, Long.MAX_VALUE, TimeUnit.NANOSECONDS, queue);
		for (int i = 0; i < 10; i++) {
			try {
				e.execute(new Task());
			} catch (RejectedExecutionException ex) {
				System.out.println("Task rejected = " + (i + 1));
			}
			printStatus(i + 1, e);
		}

		e.shutdownNow();

		System.out.println("--------------------\n");
	}

	private static void printStatus(int taskSubmitted, ThreadPoolExecutor e) {

		StringBuilder s = new StringBuilder();
		s.append("poolSize = ").append(e.getPoolSize()).append(", corePoolSize = ").append(e.getCorePoolSize())
				.append(", queueSize = ").append(e.getQueue().size()).append(", queueRemainingCapacity = ")
				.append(e.getQueue().remainingCapacity()).append(", maximumPoolSize = ").append(e.getMaximumPoolSize())
				.append(", totalTasksSubmitted = ").append(taskSubmitted);

		System.out.println(s.toString());

	}

	private static class Task implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(1000000);
				} catch (InterruptedException e) {
					break;
				}

			}
		}
	}
}