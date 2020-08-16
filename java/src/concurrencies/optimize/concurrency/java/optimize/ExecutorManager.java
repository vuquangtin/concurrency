package concurrency.java.optimize;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ExecutorManager {

	private Logger log = Logger.getLogger(ExecutorManager.class);

	private static ExecutorManager executorManager = new ExecutorManager();
	private static boolean flag = false;

	private ExecutorManager() {
		synchronized (ExecutorManager.class) {
			if (false == flag) {
				flag = !flag;
			} else {
				throw new RuntimeException("单例模式正在被攻击");
			}

		}
	}

	public static ExecutorManager getInstance() {
		return executorManager;
	}

	/**
	 * 初始化线程池
	 */
	public ExecutorService initTheadPool(String theadName) {
		ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(theadName + "-pool-%d").build();
		int nThreads = Runtime.getRuntime().availableProcessors();
		return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1024),
				threadFactory, new ThreadPoolExecutor.AbortPolicy());
	}

	/**
	 * 等待线程全部执行完成
	 *
	 * @param executorService
	 */
	public void closeExecutorService(ExecutorService executorService) {
		try {
			executorService.shutdown();// 启动一次顺序关闭，执行以前提交的任务，但不接受新任务
			while (true) {
				if (executorService.isTerminated()) {// 当所有子线程结束则跳出循环
					break;
				}
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
