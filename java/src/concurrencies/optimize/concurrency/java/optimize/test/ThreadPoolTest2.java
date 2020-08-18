package concurrency.java.optimize.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreadPoolTest2 {
	static Logger logger = Logger.getLogger(ThreadPoolTest2.class.getName());
	private ThreadPoolExecutor executor = null;

	/**
	 * SynchronousQueue
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void pool1() throws InterruptedException {
		executor = new ThreadPoolExecutor(6, 10, 5, TimeUnit.SECONDS,
				new SynchronousQueue<>());
		runTask();
	}

	/**
	 * LinkedBlockingDeque
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void pool2() throws InterruptedException {
		executor = new ThreadPoolExecutor(3, 6, 5, TimeUnit.SECONDS,
				new LinkedBlockingDeque<>());
		runTask();
	}

	/**
	 * SynchronousQueue
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void pool3() throws InterruptedException {
		executor = new ThreadPoolExecutor(3, 6, 5, TimeUnit.SECONDS,
				new SynchronousQueue<>());
		runTask();
	}

	/**
	 * LinkedBlockingDeque
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void pool4() throws InterruptedException {
		executor = new ThreadPoolExecutor(3, 4, 5, TimeUnit.SECONDS,
				new LinkedBlockingDeque<>());
		runTask();
	}

	/**
	 * LinkedBlockingDeque with limit 2
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void pool5() throws InterruptedException {
		executor = new ThreadPoolExecutor(3, 4, 5, TimeUnit.SECONDS,
				new LinkedBlockingDeque<>(2));
		runTask();
	}

	/**
	 * LinkedBlockingDeque with limit 1
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void pool6() throws InterruptedException {
		executor = new ThreadPoolExecutor(3, 4, 5, TimeUnit.SECONDS,
				new LinkedBlockingDeque<>(1));
		runTask();
	}

	/**
	 * SynchronousQueue
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void pool7() throws InterruptedException {
		executor = new ThreadPoolExecutor(3, 4, 5, TimeUnit.SECONDS,
				new SynchronousQueue<>());
		runTask();
	}

	/**
	 * SynchronousQueue
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void arrayBlockingQueue() throws InterruptedException {
		executor = new ThreadPoolExecutor(2, 4, 5, TimeUnit.SECONDS,
				new ArrayBlockingQueue<>(2));
		runTask();
	}

	void runTask() throws InterruptedException {
		executor.execute(new TestRunable());
		executor.execute(new TestRunable());
		executor.execute(new TestRunable());

		logger.info("********启动3个线程********");
		logger.info(String.format("getCorePoolSize : {}",
				executor.getCorePoolSize()));
		logger.info(String.format("getPoolSize : {}", executor.getPoolSize()));
		logger.info(String.format("getActiveCount : {}",
				executor.getActiveCount()));
		logger.info(String.format("getQueue : {}", executor.getQueue().size()));

		executor.execute(new TestRunable());
		executor.execute(new TestRunable());
		executor.execute(new TestRunable());

		logger.info("********再启动3个线程********");
		logger.info(String.format("getCorePoolSize : {}",
				executor.getCorePoolSize()));
		logger.info(String.format("getPoolSize : {}", executor.getPoolSize()));
		logger.info(String.format("getActiveCount : {}",
				executor.getActiveCount()));
		logger.info(String.format("getQueue : {}", executor.getQueue().size()));

		Thread.sleep(8000);
		logger.info("--------8秒后--------");
		logger.info(String.format("getCorePoolSize : {}",
				executor.getCorePoolSize()));
		logger.info(String.format("getPoolSize : {}", executor.getPoolSize()));
		logger.info(String.format("getActiveCount : {}",
				executor.getActiveCount()));
		logger.info(String.format("getQueue : {}", executor.getQueue().size()));
	}

	static class TestRunable implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(2000);
				logger.info(String.format("thread name {} , id {} runs", Thread
						.currentThread().getName(), Thread.currentThread()
						.getId()));
			} catch (InterruptedException e) {
				logger.info("error : {}", e);
			}
		}
	}

}
