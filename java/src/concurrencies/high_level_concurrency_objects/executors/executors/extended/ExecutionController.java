package executors.extended;

import java.util.concurrent.RejectedExecutionException;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public interface ExecutionController<Task extends TaskGroup> {

	public void start() throws RejectedExecutionException;

	public void abort();

	public int getRemainingTaskCount();

	public void awaitExecution() throws InterruptedException;

	public void awaitExecution(long timeout) throws InterruptedException;

	public boolean isStarted();

	public boolean isAborted();

	public boolean isFinished();

	public Task getTaskGroup();
}