package executorsandthreadpool;

import java.util.Set;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class CustomThreadPoolExample {
	public static void main(String[] args) {
		CustomThreadPool customThreadPool = new CustomThreadPool(2);

		for (int i = 1; i <= 5; i++) {
			Task task = new Task("Task " + i);
			System.out.println("Created : " + task.getName());

			customThreadPool.execute(task);
		}
		Set<Thread> threads = Thread.getAllStackTraces().keySet();

		for (Thread t : threads) {
			String name = t.getName();
			Thread.State state = t.getState();
			int priority = t.getPriority();
			String type = t.isDaemon() ? "Daemon" : "Normal";
			System.out.printf("%-20s \t %s \t %d \t %s\n", name, state,
					priority, type);
		}
	}
}