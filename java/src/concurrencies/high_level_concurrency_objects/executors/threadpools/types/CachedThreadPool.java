package threadpools.types;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*- Types
 * 
 * 1. FixedThreadPool
 * 2. CachedThreadPool
 * 3. ScheduleThreadPool
 * 4. SingleThreadedExecutor
 * 
 */
public class CachedThreadPool {

	public static void main(String[] args) {

		/*
		 * Cache Thread Pool will automatically create a new thread if required
		 * and kill any thread which is ideal for more than 60 seconds
		 */
		ExecutorService service = Executors.newCachedThreadPool();

		for (int i = 0; i < 10; i++) {
			service.execute(new Task("Task " + i));
		}

	}

	static class Task implements Runnable {

		String taskName;

		public Task(String taskName) {
			this.taskName = taskName;
		}

		@Override
		public void run() {
			System.out.println(taskName + " started");
		}

	}

}