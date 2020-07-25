package executors.customthreadpoolexecutor.feature;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Java7_0346_ThreadPoolExecutorMain {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		new Java7_0346_ThreadPoolExecutorMain().executorServiceFutureTask();
	}
	
	public void executorServiceFutureTask() throws InterruptedException, ExecutionException {
		// Use the executor created by the newCachedThreadPool() method only when you have a reasonable number of threads or when they have a short duration.
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		for (int i = 0; i <= 5; i++) {
			Java7_0346_ThreadPoolExecutorTask task = new Java7_0346_ThreadPoolExecutorTask("Task-" + i);
			System.out.println(task.getName() + " has been added");
			executor.execute(task);
		}
		executor.shutdown();
	}
}
