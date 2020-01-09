package executors.extended;

import java.util.List;
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

public interface TaskGroupHandler<Task extends TaskGroup, Controller extends ExecutionController<Task>> {

	public Controller execute(Task taskGroup) throws RejectedExecutionException;

	public int getTotalTaskCount();

	public int getTaskGroupCount();

	public void abortAllTaskGroups();

	public List<Controller> getTaskGroups();

	public Status getStatus();

	public void awaitTermination(long timeout) throws InterruptedException;

	public void awaitTermination() throws InterruptedException;

	public void shutdown();

	public void shutdownNow();
}