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
import java.util.concurrent.Callable;

public class RetryableAsyncCouchDocFetchTask implements
		Callable<RetryableAsyncCouchDocFetchTask> {
	private final String docId;
	private final String name;
	private/* */String docResult;
	private/* */boolean isRetryableException;
	private/* */int retryCount = 0;
	private static final int MAX_TASK_RETRY = 2;

	public RetryableAsyncCouchDocFetchTask(String name, String docId) {
		this.name = name;
		this.docId = docId;
	}

	@Override
	public RetryableAsyncCouchDocFetchTask call() throws Exception {
		try {
			retryCount++;
			isRetryableException = false;
			// -------- Begin of functionnal code
			if (Math.random() > 0.5) {
				System.err.printf("%s: IO Error Occurred (%d)\n",
						this.getName(), this.getRetryCount());
				throw new IOException(); // timeout condition
			}

			docResult = "Doc Retrieved!";
			// -------- End of functionnal code
		} catch (final IOException e) {
			if (retryCount > MAX_TASK_RETRY) {
				System.out.println(name + " : stopping retryCount :"
						+ retryCount);
				throw new IOException(name
						+ " : stopping due to IOException retryCount :"
						+ retryCount);
			}

			isRetryableException = true;
		}

		return this;
	}

	public String getName() {
		return name;
	}

	public String getDocResult() {
		return docResult;
	}

	public boolean isRetryableException() {
		return isRetryableException;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public String getDocId() {
		return docId;
	}
}