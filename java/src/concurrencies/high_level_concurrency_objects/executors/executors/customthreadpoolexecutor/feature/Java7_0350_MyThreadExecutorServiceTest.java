package executors.customthreadpoolexecutor.feature;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Java7_0350_MyThreadExecutorServiceTest {
	private static Future taskTwo = null;
	private static Future taskThree = null;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(2);

		// execute the Runnable
		Runnable taskOne = new Java7_0350_MyThread("TaskOne", 2, 100);
		executor.execute(taskOne);
		for (int i = 0; i < 2; i++) {
			// if this task is not created or is canceled or is completed
			if ((taskTwo == null) || (taskTwo.isDone()) || (taskTwo.isCancelled())) {
				// submit a task and return a Future
				taskTwo = executor.submit(new Java7_0350_MyThread("TaskTwo", 4, 200));
			}

			if ((taskThree == null) || (taskThree.isDone()) || (taskThree.isCancelled())) {
				taskThree = executor.submit(new Java7_0350_MyThread("TaskThree", 5, 100));
			}
			// if null the task has finished
			if (taskTwo.get() == null) {
				System.out.println(i + 1 + ") TaskTwo terminated successfully");
			} else {
				// if it doesn't finished, cancel it
				taskTwo.cancel(true);
			}
			if (taskThree.get() == null) {
				System.out.println(i + 1 + ") TaskThree terminated successfully");
			} else {
				taskThree.cancel(true);
			}
		}
		executor.shutdown();
		System.out.println("-----------------------");
		// wait until all tasks are finished
		executor.awaitTermination(1, TimeUnit.SECONDS);
		System.out.println("All tasks are finished!");
	}
}

/*
 * Output TaskOne thread has sum = 3 and is going to sleep for 100 TaskTwo
 * thread has sum = 10 and is going to sleep for 200 TaskThree thread has sum =
 * 15 and is going to sleep for 100 1) TaskTwo terminated successfully 1)
 * TaskThree terminated successfully TaskTwo thread has sum = 10 and is going to
 * sleep for 200 TaskThree thread has sum = 15 and is going to sleep for 100 2)
 * TaskTwo terminated successfully 2) TaskThree terminated successfully
 * ----------------------- All tasks are finished!
 */
