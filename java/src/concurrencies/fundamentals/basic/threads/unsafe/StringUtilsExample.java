package basic.threads.unsafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

/**
 * StringBuilder 是线程不安全的 StringBuffer 是线程安全的, StringBuffer 中有 synchronized
 * 来保证线程安全
 */

@Slf4j
public class StringUtilsExample {
	static Logger logger = Logger.getLogger(HashTableExample.class.getName());
	private static int threadTotal = 200;
	private static int clientTotal = 5000;

	// @NotThreadSafe
	private static StringBuilder stringBuilder = new StringBuilder();

	// @ThreadSafe
	private static StringBuffer stringBuffer = new StringBuffer();

	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		final Semaphore semaphore = new Semaphore(threadTotal);
		final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

		for (int index = 0; index < clientTotal; index++) {
			exec.execute(() -> {
				try {
					semaphore.acquire();
					update();
					semaphore.release();
				} catch (Exception e) {
					e.printStackTrace();
				}
				countDownLatch.countDown();
			});
		}
		countDownLatch.await();
		exec.shutdown();
		logger.info("string builder's size : " + stringBuilder.length());
		logger.info("string buffer's size : " + stringBuffer.length());
	}

	private static void update() {
		stringBuilder.append("1");
		stringBuffer.append("1");
	}

}
