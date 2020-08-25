package executors.factories;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreadPool {

	public enum poolType {
		newCachedThreadPool, newFixedThreadPool, newScheduledThreadPool, newSingleThreadExecutor;
	}

	final static int Default = 10;

	private ExecutorService exs = null;

	// 手动调用
	public ExecutorService getExecutorService() {
		if (exs == null) {
			exs = Executors.newCachedThreadPool();
		}
		return exs;
	}

	// 默认方法
	public ThreadPool() {
		if (exs == null) {
			exs = Executors.newCachedThreadPool();
		}
	}

	public ThreadPool(int size, poolType type) {
		this(size, type.toString());
	}

	// 默认方法-以传递参数决定启动的线程池类型及大小
	public ThreadPool(int size, String type) {
		if (size == 0) {
			if (type != null && !"".equals(type.trim())) {
				if (poolType.newCachedThreadPool.toString().equals(type)) {
					exs = Executors.newCachedThreadPool();
				} else if (poolType.newFixedThreadPool.equals(type)) {
					exs = Executors.newFixedThreadPool(Default);
				} else if (poolType.newScheduledThreadPool.equals(type)) {
					exs = Executors.newScheduledThreadPool(Default);
				} else if (poolType.newSingleThreadExecutor.equals(type)) {
					exs = Executors.newSingleThreadExecutor();
				} else {
					exs = Executors.newCachedThreadPool();
				}
			} else {
				exs = Executors.newCachedThreadPool();
			}
		} else if (size > 0) {
			if (poolType.newFixedThreadPool.equals(type)) {
				exs = Executors.newFixedThreadPool(size);
			} else if (poolType.newScheduledThreadPool.equals(type)) {
				exs = Executors.newScheduledThreadPool(size);
			} else {
				exs = Executors.newFixedThreadPool(Default);
			}
		}
	}

}