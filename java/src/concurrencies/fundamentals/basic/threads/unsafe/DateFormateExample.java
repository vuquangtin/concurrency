package basic.threads.unsafe;

import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

import basic.annotations.NotRecommend;
import basic.annotations.NotThreadSafe;

/**
 * SimpleDateFormat 不是一个线程安全的类, 当其被多线程使用时, 会抛出异常
 */

@Slf4j
@NotThreadSafe
@NotRecommend
public class DateFormateExample {
	static Logger logger = Logger.getLogger(DateFormateExample.class.getName());
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyyMMdd");

	private static int threadTotal = 200;
	private static int clientTotal = 5000;

	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		final Semaphore semaphore = new Semaphore(threadTotal);
		final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

		for (int index = 0; index < clientTotal; index++) {
			exec.execute(() -> {
				try {
					semaphore.acquire();
					// format();
					safeFormat();
					semaphore.release();
				} catch (Exception e) {
					e.printStackTrace();
				}
				countDownLatch.countDown();
			});
		}
		countDownLatch.await();
		exec.shutdown();
	}

	private static void format() {
		try {
			simpleDateFormat.parse("20180908");
		} catch (Exception e) {
			logger.error("parse excetion", e);
		}
	}

	/**
	 * 使用线程封闭的写法来保证线程安全
	 */
	//@ThreadSafe
	private static void safeFormat() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			sdf.parse("20180908");
		} catch (Exception e) {
			logger.error("parse excetion", e);
		}
	}

}
