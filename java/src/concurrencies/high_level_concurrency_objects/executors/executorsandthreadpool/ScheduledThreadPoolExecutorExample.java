package executorsandthreadpool;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class ScheduledThreadPoolExecutorExample {
	public static void main(String[] args) {
		ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors
				.newScheduledThreadPool(2);

		TaskScheduled task = new TaskScheduled("Repeat Task");
		System.out.println("Created : " + task.getName());

		executor.scheduleWithFixedDelay(task, 2, 2, TimeUnit.SECONDS);
	}
}

class TaskScheduled implements Runnable {
	private String name;

	public TaskScheduled(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@SuppressWarnings("deprecation")
	public void run() {
		System.out.println("Executing : " + name + ", Current Seconds : "
				+ new Date().getSeconds());
	}
}
