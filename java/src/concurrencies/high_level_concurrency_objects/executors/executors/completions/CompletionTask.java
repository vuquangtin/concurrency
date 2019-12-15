package executors.completions;

import java.util.concurrent.Callable;

public class CompletionTask implements Callable<Integer> {
	private final String taskName;
	private final int sleepTime;

	public CompletionTask(String taskName, int sleepTime) {
		this.taskName = taskName;
		this.sleepTime = sleepTime;
	}

	@Override
	public Integer call() throws Exception {
		System.out.println("starting with task " + taskName);
		Thread.sleep(sleepTime);
		System.out.println("Completed with task " + taskName);
		return ((int) Math.random() * 5000 + 1);
	}
}