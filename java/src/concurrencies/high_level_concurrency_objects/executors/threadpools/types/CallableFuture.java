package threadpools.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableFuture {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService service = Executors.newFixedThreadPool(100);

		/* Submit tasks to thread pool */
		@SuppressWarnings("rawtypes")
		List<Future<String>> allFutures = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			Future<String> future = service.submit(new Task("Task " + i));
			allFutures.add(future);
		}

		for (Future<String> future : allFutures) {

			/*- If you want the operation to be completed in a specified time
			 * 
			 *  future.get(1, TimeUnit.SECONDS);
			 * 
			 */

			System.out.println("==> " + future.get());
		}
		List<Callable<String>> allCallable = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			allCallable.add(new Task("Task " + i));
		}
		allFutures = service.invokeAll(allCallable);
		for (Future<String> future : allFutures) {
			System.out.println("==> " + future.get());
		}
		service.shutdown();
	}

	static class Task implements Callable<String> {

		String taskName;

		public Task(String taskName) {
			this.taskName = taskName;
		}

		@Override
		public String call() throws Exception {
			Thread.sleep(new Random().nextInt(1000));
			return taskName + " started";
		}
	}

}