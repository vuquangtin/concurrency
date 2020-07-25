package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

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
