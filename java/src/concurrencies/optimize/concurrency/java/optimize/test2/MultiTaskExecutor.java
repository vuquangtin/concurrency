package concurrency.java.optimize.test2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class MultiTaskExecutor {
	public static void main(String[] args) {
		BlockingQueue<Runnable> worksQueue = new ArrayBlockingQueue<Runnable>(10);
		RejectedExecutionHandler rejectionHandler = new RejectedExecutionHandelerImpl();
		ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3, 10, TimeUnit.SECONDS, worksQueue, rejectionHandler);
		executor.prestartAllCoreThreads();

		List<Runnable> taskGroup = new ArrayList<Runnable>();
		taskGroup.add(new TaskOne());
		taskGroup.add(new TaskTwo());
		taskGroup.add(new TaskThree());

		worksQueue.add(new MultiRunnable(taskGroup));
	}
}

class MultiRunnable implements Runnable {
	final List<Runnable> runnables;

	MultiRunnable(List<Runnable> runnables) {
		this.runnables = runnables;
	}

	@Override
	public void run() {
		for (Runnable runnable : runnables) {
			new Thread(runnable).start();
		}
	}
}

class TaskOne implements Runnable {
	@Override
	public void run() {
		System.out.println("Executing Task One");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class TaskTwo implements Runnable {
	@Override
	public void run() {
		System.out.println("Executing Task Two");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class TaskThree implements Runnable {
	@Override
	public void run() {
		System.out.println("Executing Task Three");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class RejectedExecutionHandelerImpl implements RejectedExecutionHandler {
	@Override
	public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
		System.out.println(runnable.toString() + " : I've been rejected ! ");
	}
}