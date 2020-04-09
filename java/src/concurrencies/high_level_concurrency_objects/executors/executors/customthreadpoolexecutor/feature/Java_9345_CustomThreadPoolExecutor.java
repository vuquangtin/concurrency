package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.util.concurrent.ThreadPoolExecutor;

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
