package concurrency.java.bounded.blocking;

import java.text.MessageFormat;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import concurrencies.utilities.Log4jUtils;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class BlockingThreadPoolExecutorTest {
	private final BlockingThreadPoolExecutor blockingTpe;
	private final ThreadPoolExecutor defaultTpe;
	static Logger logger = Logger
			.getLogger(BlockingThreadPoolExecutorTest.class.getName());

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		new BlockingThreadPoolExecutorTest().go();
	}

	public BlockingThreadPoolExecutorTest() {
		int corePoolSize = 3, maxPoolSize = 4, queueCapacity = 2;

		// Create ThreadPools
		logger.debug(MessageFormat
				.format("Creating BlockingThreadPoolExecutor and ThreadPoolExecutor with: corePoolSize: {0}, maxPoolSize: {1}, queueCapacity: {2}",
						corePoolSize, maxPoolSize, queueCapacity));
		blockingTpe = new BlockingThreadPoolExecutor(corePoolSize, maxPoolSize,
				Integer.MAX_VALUE, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(queueCapacity),
				new PriorityThreadFactory("workerThread"));
		defaultTpe = new ThreadPoolExecutor(corePoolSize, maxPoolSize,
				Integer.MAX_VALUE, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(queueCapacity),
				new PriorityThreadFactory("workerThread"));
	}

	public void go() {
		// Create some tasks and submit to both ThreadPools
		int rejectCount = 0;

		logger.debug("Submitting tasks to ThreadPoolExecutor");
		for (int i = 0; i < 10; i++) {
			try {
				createAndSubmitTask("thread" + i, defaultTpe);
			} catch (RejectedExecutionException rje) {
				logger.error("task" + i + ": Rejected");
				rejectCount++;
			}
		}
		logger.debug(MessageFormat.format(
				"Done with ThreadPoolExecutor. Total task rejections: {0}",
				rejectCount));

		logger.debug("Submitting tasks to BlockingThreadPoolExecutor");
		rejectCount = 0;
		for (int i = 0; i < 10; i++) {
			try {
				createAndSubmitTask("thread" + i, blockingTpe);
			} catch (RejectedExecutionException rje) {
				logger.error("task" + i + ": Rejected");
				rejectCount++;
			}
		}
		logger.debug(MessageFormat
				.format("Done with BlockingThreadPoolExecutor. Total task rejections: {0}",
						rejectCount));

	}

	private void createAndSubmitTask(final String taskId, ThreadPoolExecutor tpe)
			throws RejectedExecutionException {
		// Create a dummy task to execute for a few seconds
		Runnable r = new Runnable() {
			@Override
			public void run() {
				int i = 0;
				logger.debug(taskId + ": is running");

				while (i++ < 50) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						logger.error("Intrrupted");
						break;
					}
				}

				logger.debug(taskId + ": is done");
			}

		};

		// Submit task to ThreadPoolExecutor
		// Will call the correct execute(...) method using dynamic binding.
		tpe.execute(r);
	}

}
