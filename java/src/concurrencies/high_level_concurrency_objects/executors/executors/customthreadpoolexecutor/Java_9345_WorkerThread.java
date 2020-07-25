package executors.customthreadpoolexecutor;

import static java.lang.System.out;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class Java_9345_WorkerThread implements Runnable {

	private String commandOrTaskName;

	public Java_9345_WorkerThread(String commandOrTaskName) {
		this.commandOrTaskName = commandOrTaskName;
	}

	@Override
	public void run() {
		out.println("Start" + this.commandOrTaskName + " performed by " + Thread.currentThread().getName());
		processCommandOrTaskName();
		out.println("End" + this.commandOrTaskName + " performed by " + Thread.currentThread().getName());
	}

	private void processCommandOrTaskName() {
		try {
			// because of Thread class it is necessary to handle the exception
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return this.commandOrTaskName;
	}
}