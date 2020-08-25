package executors.factories;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import executors.factories.models.DaemonThreadFactory;
import executors.factories.models.MaxPriorityThreadFactory;
import executors.factories.models.MinPriorityThreadFactory;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ExecutorServiceFactory {
	public enum ThreadFactoryType {
		newCachedThreadPool, defaultThreadFactory, daemonThreadFactory, maxPriorityThreadFactory, minPriorityThreadFactory;
	}

	public static ExecutorService getExecutorService(
			ThreadFactoryType threadFactoryType) {
		ExecutorService executorService = null;
		switch (threadFactoryType) {
		case newCachedThreadPool:
			executorService = Executors.newCachedThreadPool();
			break;
		case defaultThreadFactory:
			executorService = Executors.newCachedThreadPool(Executors
					.defaultThreadFactory());
		case daemonThreadFactory:
			executorService = Executors
					.newCachedThreadPool(new DaemonThreadFactory());
			break;
		case maxPriorityThreadFactory:
			executorService = Executors
					.newCachedThreadPool(new MaxPriorityThreadFactory());
			break;
		case minPriorityThreadFactory:
			executorService = Executors
					.newCachedThreadPool(new MinPriorityThreadFactory());
			break;
		default:
			executorService = Executors.newCachedThreadPool();
			break;

		}

		return executorService;
	}
}
