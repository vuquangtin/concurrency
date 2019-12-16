package executors.completions.executorcompletionservices;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Main {

	public static void main(String[] args) {
		final int COUNT = 8;
		final RetryableAsyncExecutor re = new RetryableAsyncExecutor();
		final ArrayList<String> completedList = new ArrayList();
		final ArrayList<String> failedList = new ArrayList();

		for (int i = 0; i < COUNT; ++i) {
			re.submit(new RetryableAsyncCouchDocFetchTask("Async#" + (i + 1),
					"docId_" + (1 + 1)));
		}

		int count = 0;
		while (count < COUNT) {
			final RetryableAsyncCouchDocFetchTask task;
			try {
				task = re.get();
				if (task.isRetryableException()) {
					System.err.printf("%s: retrying (%d)\n", task.getName(),
							task.getRetryCount());
				} else {
					System.err.printf("%s: done with '%s'.\n", task.getName(),
							task.getDocResult());
					completedList.add(task.getName());
					count++;
				}

			} catch (ExecutionException e) {
				if (e.getCause() instanceof IOException) {
					System.out.println("Tracked!!" + e.getCause());
					failedList.add(e.getMessage());
					count++;
				}
			} catch (InterruptedException e) {
				System.out.println("ooo!!" + e.getCause());
				count++;
			}
		}

		re.shutdown();

		System.out.println("Success List Size : " + completedList.size());
		System.out.println("Failed List Size : " + failedList.size());

		System.exit(0);
	}
}
