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

public class ThreadPoolExecutorDemoTwo {
	public static void main(final String[] args) throws Exception {
		final ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 100, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(), new MyThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
		executor.execute(new BookReader("Mantra"));
		executor.execute(new BookReader("Karbla"));
		executor.execute(new BookReader("Sipahi"));
		System.out.println("Completed Task:" + executor.getCompletedTaskCount());
		System.out.println("Old Core Pool Size:" + executor.getCorePoolSize());
		executor.setCorePoolSize(3);
		System.out.println("New Core Pool Size:" + executor.getCorePoolSize());
		executor.shutdown();
	}
}