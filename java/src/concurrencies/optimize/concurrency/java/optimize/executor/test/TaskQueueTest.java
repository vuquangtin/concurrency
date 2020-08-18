package concurrency.java.optimize.executor.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import concurrency.java.optimize.executor.KeyRunnable;
import concurrency.java.optimize.executor.TaskQueue;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class TaskQueueTest {

	@Test(timeout = 5000)
	public void enqueueDequeue() throws InterruptedException {
		TaskQueue taskQueue = new TaskQueue();
		Thread enqueueThread = new Thread(() -> {
			for (int i = 0; i < 100; ++i) {
				taskQueue.enqueue(new KeyRunnable<>(i, TestUtil.doSomething));
			}
		});
		enqueueThread.start();
		for (int i = 0; i < 100;) {
			Runnable task = taskQueue.dequeue();
			if (task == null) {
				Thread.sleep(100);
			} else {
				assertEquals(task, new KeyRunnable<>(i, TestUtil.doSomething));
				++i;
			}
		}
	}

	@Test(timeout = 5000)
	public void empty() {
		TaskQueue taskQueue = new TaskQueue();

		assertTrue(taskQueue.isEmpty());
		taskQueue.enqueue(new KeyRunnable<>(0, TestUtil.doSomething));

		assertFalse(taskQueue.isEmpty());
	}

	@Test(timeout = 5000)
	public void reject() throws InterruptedException {
		TaskQueue taskQueue = new TaskQueue();

		Thread rejectTrigger = new Thread(() -> {
			try {
				Thread.sleep(200);
			} catch (InterruptedException ignored) {
			}
			taskQueue.rejectNew();
		});
		rejectTrigger.start();

		while (true) {
			if (!taskQueue.enqueue(new KeyRunnable<>(Math.random(), TestUtil.doSomething))) {
				assertTrue(true);
				return;
			}
			Thread.sleep(100);
		}
	}
}