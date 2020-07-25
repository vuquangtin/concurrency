package threadpools.concretepage;

import java.util.concurrent.LinkedBlockingQueue;
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
public class ExecutorsPoolEquivalentExamples {
	public static void main(String[] args) {
		// equivalent to Executors.newCachedThreadPool()
		executePool(new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>()),
				"equivalent to Executors#newCachedThreadPool()");

		// equivalent to Executors.newFixedThreadPool(int nThreads)
		executePool(new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>()),
				"equivalent to Executors#newFixedThreadPool(int nThreads)");

		// equivalent to Executors.newSingleThreadExecutor()
		executePool(new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>()),
				"equivalent to Executors#newSingleThreadExecutor()");
	}

	private static void executePool(ThreadPoolExecutor e, String msg) {
		System.out.println("---- " + msg + "-------------");

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