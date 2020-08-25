package executors.factories.test;

import java.util.concurrent.ExecutorService;

import executors.factories.ExecutorServiceFactory;
import executors.factories.ExecutorServiceFactory.ThreadFactoryType;
import executorsandthreadpool.Task;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ExecFromFactory {
	public static void main(String[] args) throws Exception {
		ExecutorService executorService = ExecutorServiceFactory
				.getExecutorService(ThreadFactoryType.newCachedThreadPool);
		ExecutorService defaultExec = ExecutorServiceFactory
				.getExecutorService(ThreadFactoryType.defaultThreadFactory);
		ExecutorService daemonExec = ExecutorServiceFactory
				.getExecutorService(ThreadFactoryType.daemonThreadFactory);
		ExecutorService maxPriorityExec = ExecutorServiceFactory
				.getExecutorService(ThreadFactoryType.maxPriorityThreadFactory);
		ExecutorService minPriorityExec = ExecutorServiceFactory
				.getExecutorService(ThreadFactoryType.minPriorityThreadFactory);
		Thread t = new Thread(new Task("Task"));
		for (int i = 0; i < 10; i++) {
			if (i == 1) {
				defaultExec.execute(t);
			} else if (i == 3) {
				daemonExec.execute(t);
			} else if (i == 5) {
				maxPriorityExec.execute(t);
			} else if (i == 7) {
				minPriorityExec.execute(t);
			} else {
				executorService.execute(t);
			}
		}
	}
}
