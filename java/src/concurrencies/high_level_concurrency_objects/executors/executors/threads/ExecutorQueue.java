package executors.threads;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.RejectedExecutionException;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ExecutorQueue extends LinkedTransferQueue<Runnable> {

	private static final long serialVersionUID = -265236426751004839L;

	private StandardThreadExecutor threadPoolExecutor;

	public ExecutorQueue() {
		super();
	}

	public void setStandardThreadExecutor(StandardThreadExecutor threadPoolExecutor) {
		this.threadPoolExecutor = threadPoolExecutor;
	}

	// 注：代码来源于 tomcat
	public boolean force(Runnable o) {
		if (threadPoolExecutor.isShutdown()) {
			throw new RejectedExecutionException("Executor not running, can't force a command into the queue");
		}
		// forces the item onto the queue, to be used if the scheduling is
		// rejected
		return super.offer(o);
	}

	// 注：tomcat的代码进行一些小变更
	public boolean offer(Runnable o) {
		int poolSize = threadPoolExecutor.getPoolSize();

		// we are maxed out on threads, simply queue the object
		if (poolSize == threadPoolExecutor.getMaximumPoolSize()) {
			return super.offer(o);
		}
		// we have idle threads, just add it to the queue
		// note that we don't use getActiveCount(), see BZ 49730
		if (poolSize >= threadPoolExecutor.getSubmittedTasksCount()) {
			return super.offer(o);
		}
		// if we have less threads than maximum force creation of a new
		// thread
		if (poolSize < threadPoolExecutor.getMaximumPoolSize()) {
			return false;
		}
		// if we reached here, we need to add it to the queue
		return super.offer(o);
	}

}
