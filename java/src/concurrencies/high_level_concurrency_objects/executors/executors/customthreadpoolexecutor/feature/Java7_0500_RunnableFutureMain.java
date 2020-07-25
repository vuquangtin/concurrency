package executors.customthreadpoolexecutor.feature;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Java7_0500_RunnableFutureMain {
	private static final Logger logger = Logger.getLogger("App");

	public static void main(String[] args) {
		// Create ExecutorService using the newCachedThreadPool() method
		// of the Executors class.
		ExecutorService executorService = (ExecutorService) Executors.newCachedThreadPool();
		// Create an array to store five ResultTask objects.
		Java7_0500_ResultTaskFutureTasks[] resultTasks = new Java7_0500_ResultTaskFutureTasks[5];

		// Send the each ResultTask to the executor ResultTask
		// using the submit() method.
		for (int i = 0; i < 5; i++) {
			Java7_0500_ExecutableTaskRunnables executableTask = new Java7_0500_ExecutableTaskRunnables("Task" + i);
			resultTasks[i] = new Java7_0500_ResultTaskFutureTasks(executableTask);
			executorService.submit(resultTasks[i]);
		}

		// Put the main thread to sleep for 5 seconds.
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException ie) {
			logger.log(Level.SEVERE, ie.getLocalizedMessage());
			ie.printStackTrace(System.err);
		}

		// Cancel all the tasks you have sent to the executor.
		for (int i = 0; i < resultTasks.length; i++) {
			resultTasks[i].cancel(true);
		}

		// Write to the console the result of those tasks that haven't been
		// canceled using the get() method of the ResultTask objects.
		for (int i = 0; i < resultTasks.length; i++) {
			try {
				if (resultTasks[i].isCancelled()) {
					logger.info("Task" + i + " was cancelled.");
				} else if (resultTasks[i].isDone()) {
					logger.info(resultTasks[i].get());
				}
			} catch (InterruptedException | ExecutionException e) {
				logger.log(Level.SEVERE, e.getLocalizedMessage());
				e.printStackTrace(System.err);
			}
		}

		// Finish the executor using the shutdown() method.
		executorService.shutdown();
	}
}

//Successful execution output
/*Feb 28, 2016 3:38:40 PM example.java.features.ExecutableTaskRunnables call
INFO: Task2: Waiting 4 seconds for results.
Feb 28, 2016 3:38:40 PM example.java.features.ExecutableTaskRunnables call
INFO: Task3: Waiting 1 seconds for results.
Feb 28, 2016 3:38:40 PM example.java.features.ExecutableTaskRunnables call
INFO: Task0: Waiting 3 seconds for results.
Feb 28, 2016 3:38:40 PM example.java.features.ExecutableTaskRunnables call
INFO: Task1: Waiting 1 seconds for results.
Feb 28, 2016 3:38:40 PM example.java.features.ExecutableTaskRunnables call
INFO: Task4: Waiting 0 seconds for results.
Feb 28, 2016 3:38:40 PM example.java.features.ResultTaskFutureTasks done
INFO: Task4: has finished
Feb 28, 2016 3:38:41 PM example.java.features.ResultTaskFutureTasks done
INFO: Task3: has finished
Feb 28, 2016 3:38:41 PM example.java.features.ResultTaskFutureTasks done
INFO: Task1: has finished
Feb 28, 2016 3:38:43 PM example.java.features.ResultTaskFutureTasks done
INFO: Task0: has finished
Feb 28, 2016 3:38:44 PM example.java.features.ResultTaskFutureTasks done
INFO: Task2: has finished
Feb 28, 2016 3:38:45 PM example.java.features.RunnableFutureMain main
INFO: Hello, world. I'm Task0
Feb 28, 2016 3:38:45 PM example.java.features.RunnableFutureMain main
INFO: Hello, world. I'm Task1
Feb 28, 2016 3:38:45 PM example.java.features.RunnableFutureMain main
INFO: Hello, world. I'm Task2
Feb 28, 2016 3:38:45 PM example.java.features.RunnableFutureMain main
INFO: Hello, world. I'm Task3
Feb 28, 2016 3:38:45 PM example.java.features.RunnableFutureMain main
INFO: Hello, world. I'm Task4
*/

//Unsuccessful execution output
/*Feb 28, 2016 3:32:15 PM example.java.features.ExecutableTask call
INFO: Task2: Waiting 2 seconds for results.
Feb 28, 2016 3:32:15 PM example.java.features.ExecutableTask call
INFO: Task4: Waiting 3 seconds for results.
Feb 28, 2016 3:32:15 PM example.java.features.ExecutableTask call
INFO: Task1: Waiting 6 seconds for results.
Feb 28, 2016 3:32:15 PM example.java.features.ExecutableTask call
INFO: Task3: Waiting 5 seconds for results.
Feb 28, 2016 3:32:15 PM example.java.features.ExecutableTask call
INFO: Task0: Waiting 7 seconds for results.
Feb 28, 2016 3:32:17 PM example.java.features.ResultTask done
INFO: Task2: has finished
Feb 28, 2016 3:32:18 PM example.java.features.ResultTask done
INFO: Task4: has finished
Feb 28, 2016 3:32:20 PM example.java.features.ResultTask done
INFO: Task0: has been canceled
Feb 28, 2016 3:32:20 PM example.java.features.ResultTask done
INFO: Task1: has been canceled
Feb 28, 2016 3:32:20 PM example.java.features.ResultTask done
INFO: Task3: has been canceled
Feb 28, 2016 3:32:20 PM example.java.features.App main
INFO: Task0 was cancelled.
Feb 28, 2016 3:32:20 PM example.java.features.App main
INFO: Task1 was cancelled.
Feb 28, 2016 3:32:20 PM example.java.features.App main
INFO: Hello, world. I'm Task2
Feb 28, 2016 3:32:20 PM example.java.features.App main
INFO: Task3 was cancelled.
Feb 28, 2016 3:32:20 PM example.java.features.App main
INFO: Hello, world. I'm Task4
Feb 28, 2016 3:32:20 PM example.java.features.ExecutableTask call
SEVERE: sleep interrupted
java.lang.InterruptedException: sleep interrupted
	at java.lang.Thread.sleep(Native Method)
	at java.lang.Thread.sleep(Thread.java:340)
	at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
	at example.java.features.ExecutableTask.call(ExecutableTask.java:34)
	at example.java.features.ExecutableTask.call(ExecutableTask.java:1)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:745)
Feb 28, 2016 3:32:20 PM example.java.features.ExecutableTask call
SEVERE: sleep interrupted
java.lang.InterruptedException: sleep interrupted
	at java.lang.Thread.sleep(Native Method)
	at java.lang.Thread.sleep(Thread.java:340)
	at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
	at example.java.features.ExecutableTask.call(ExecutableTask.java:34)
	at example.java.features.ExecutableTask.call(ExecutableTask.java:1)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:745)
Feb 28, 2016 3:32:20 PM example.java.features.ExecutableTask call
SEVERE: sleep interrupted
java.lang.InterruptedException: sleep interrupted
	at java.lang.Thread.sleep(Native Method)
	at java.lang.Thread.sleep(Thread.java:340)
	at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
	at example.java.features.ExecutableTask.call(ExecutableTask.java:34)
	at example.java.features.ExecutableTask.call(ExecutableTask.java:1)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:745)
*/
