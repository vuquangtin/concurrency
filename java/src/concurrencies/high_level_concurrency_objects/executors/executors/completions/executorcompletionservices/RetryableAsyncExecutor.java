package executors.completions.executorcompletionservices;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class RetryableAsyncExecutor {

	private final ExecutorService executorService;
	private final CompletionService<RetryableAsyncCouchDocFetchTask> completionService;

	public RetryableAsyncExecutor() {
		executorService = Executors.newCachedThreadPool();
		completionService = new ExecutorCompletionService<>(executorService);
	}

	public void submit(RetryableAsyncCouchDocFetchTask task) {
		completionService.submit(task);
	}

	public RetryableAsyncCouchDocFetchTask get() throws ExecutionException,
			InterruptedException {
		final Future<RetryableAsyncCouchDocFetchTask> future = completionService
				.take();
		final RetryableAsyncCouchDocFetchTask task = future.get();
		if (task.isRetryableException()) {
			completionService.submit(task);
		}

		return task;
	}

	public void shutdown() {
		executorService.shutdown();
	}
}