package atomicvariables.atomics;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

/**
 * @author panghu
 * @Title: AtomicExample6
 * @ProjectName cuncurrency_demo
 * @Description: TODO
 * @date 19-2-16 上午10:45
 */
@Slf4j
public class AtomicExample6 {
	static Logger logger = Logger.getLogger(AtomicExample6.class
			.getName());
	private static AtomicBoolean isHappened = new AtomicBoolean();

	/** 请求总数 */
	private static int clientTotal = 5000;

	/** 同时并发的执行数目 */
	public static int threadTotal = 200;

	public static void main(String[] args) throws Exception {
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		final Semaphore semaphore = new Semaphore(threadTotal);
		final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
		for (int i = 0; i < clientTotal; i++) {
			executorService.execute(() -> {
				try {
					semaphore.acquire();
					test();
					semaphore.release();
				} catch (Exception e) {
					logger.info("exception", e);
				}
				countDownLatch.countDown();
			});
		}
		countDownLatch.await();
		executorService.shutdown();
		logger.info("idHappened:{}" + isHappened.get());
	}

	/**
	 * 这样可以保证代码只执行一次，原子性
	 */
	private static void test() {
		if (isHappened.compareAndSet(false, true)) {
			logger.info("execute");
		}
	}

}
