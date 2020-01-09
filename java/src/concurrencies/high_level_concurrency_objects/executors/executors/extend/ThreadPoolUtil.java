package executors.extend;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import akka.protobuf.Parser;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ThreadPoolUtil {
	private final static Map<String, ThreadPoolExecutor> threadPoolExecutorMap = new ConcurrentHashMap<>();

	public static ThreadPoolExecutor createThreadPool(String poolName, int poolSize, int queueSize) {
		ThreadPoolExecutor threadPoolExecutor = new SimpleThreadPoolExecutor(poolSize, poolSize, 0L,
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(queueSize),
				new ThreadPoolExecutor.DiscardPolicy(), poolName);
		new Thread(new ThreadPoolMonitor(threadPoolExecutor, poolName)).start();
		threadPoolExecutorMap.put(poolName, threadPoolExecutor);
		return threadPoolExecutor;
	}

	public static ThreadPoolExecutor createThreadPool(Class<? extends Runnable> taskClass, int poolSize) {
		return createThreadPool(getThreadPoolName(taskClass), poolSize, poolSize);
	}

	public static String getThreadPoolName(Class<? extends Runnable> taskClass) {
		return taskClass.getSimpleName().substring(0, taskClass.getSimpleName().length() - 4) + "ThreadPool";
	}

	public static ThreadPoolExecutor getThreadPool(Class<? extends Runnable> taskClass) {
		return threadPoolExecutorMap.get(getThreadPoolName(taskClass));
	}

	public static ThreadPoolExecutor getThreadPool(String poolName) {
		return threadPoolExecutorMap.get(poolName);
	}

	public static String getParserPoolName(Class<? extends Parser> parser) {
		return parser.getSimpleName() + "ThreadPool";
	}

	public static String getParserQueueName(Class<? extends Parser> parser) {
		return parser.getSimpleName() + "Queue";
	}

	public static Map<String, ThreadPoolExecutor> getThreadPoolExecutorMap() {
		return threadPoolExecutorMap;
	}
}
