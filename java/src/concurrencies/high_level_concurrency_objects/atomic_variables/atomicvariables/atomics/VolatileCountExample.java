package atomicvariables.atomics;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

/**
 * volatile 并不能完全保证线程安全 , 在读取时同时拿到 最新值, 但是可能同时进行写操作
 *
 * volatile 适用于 1. 对变量的写操作不依赖于当前值 2. 该变量没有包含在具有其它变量的式子中
 */

@Slf4j
public class VolatileCountExample {
	static Logger logger = Logger.getLogger(VolatileCountExample.class
			.getName());
	private static int threadTotal = 200;
	private static int clientTotal = 5000;

	private static volatile long count = 0;

	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		final Semaphore semaphore = new Semaphore(threadTotal);
		final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

		for (int index = 0; index < clientTotal; index++) {
			exec.execute(() -> {
				try {
					semaphore.acquire();
					add();
					semaphore.release();
				} catch (Exception e) {
					e.printStackTrace();
				}
				countDownLatch.countDown();
			});
		}
		countDownLatch.await();
		exec.shutdown();
		logger.info("count : " + count);
	}

	private static void add() {
		count++;
	}
}
