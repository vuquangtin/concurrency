package executors.customthreadpoolexecutor;

import static java.lang.System.out;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class Java_9345_CustomThreadPoolExecutor implements Runnable {
	private ThreadPoolExecutor executor;
	private int seconds;
	private boolean isActive = true;

	public Java_9345_CustomThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor, int delayInSeconds) {
		this.executor = threadPoolExecutor;
		this.seconds = delayInSeconds;
	}

	public void shutdown() {
		this.isActive = false;
	}

	@Override
	public void run() {
		while (isActive) {
			out.println(String.format(
					"[monitor] [%d/%d] Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated: %s",
					this.executor.getPoolSize(), this.executor.getCorePoolSize(), this.executor.getActiveCount(),
					this.executor.getCompletedTaskCount(), this.executor.getTaskCount(), this.executor.isShutdown(),
					this.executor.isTerminated()));
			try {
				Thread.sleep(seconds * 10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}