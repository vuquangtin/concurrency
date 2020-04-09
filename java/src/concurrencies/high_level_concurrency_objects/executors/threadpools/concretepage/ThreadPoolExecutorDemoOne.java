package threadpools.concretepage;

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
public class ThreadPoolExecutorDemoOne {
	public static void main(final String[] args) throws Exception {
		final ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 3, 100, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());
		executor.execute(new BookReader("Ramayan"));
		executor.execute(new BookReader("Mahabharat"));
		executor.execute(new BookReader("Veda"));
		System.out.println("Old Max Pool Size:" + executor.getMaximumPoolSize());
		executor.setMaximumPoolSize(4);
		System.out.println("New Max Pool Size:" + executor.getMaximumPoolSize());
		executor.shutdown();
	}
}
