package concurrency.java.optimize;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ExecutorMonitor extends Thread {

	ThreadPoolExecutor executor = null;
	int initialCorePoolSize;

	public ExecutorMonitor(ThreadPoolExecutor executor) {
		this.executor = executor;
		this.initialCorePoolSize = executor.getCorePoolSize();
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (executor.getQueue().size() > 0) {
				if (executor.getActiveCount() < executor.getMaximumPoolSize())
					executor.setCorePoolSize(executor.getCorePoolSize() + 1);
			}
			if (executor.getQueue().size() == 0) {
				if (executor.getCorePoolSize() > initialCorePoolSize)
					executor.setCorePoolSize(executor.getCorePoolSize() - 1);
			}
		}
	}
}
