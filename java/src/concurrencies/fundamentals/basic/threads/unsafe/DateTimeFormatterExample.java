package basic.threads.unsafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import javax.annotation.concurrent.ThreadSafe;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@Slf4j
@ThreadSafe
public class DateTimeFormatterExample {
	static Logger logger = Logger.getLogger(DateTimeFormatterExample.class.getName());
	private static DateTimeFormatter dft = DateTimeFormat
			.forPattern("yyyyMMdd");

	private static int threadTotal = 200;
	private static int clientTotal = 5000;

	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		final Semaphore semaphore = new Semaphore(threadTotal);
		final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

		for (int index = 0; index < clientTotal; index++) {
			final int i = index;
			exec.execute(() -> {
				try {
					semaphore.acquire();
					jodaTimeTest(i);
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

	private static void jodaTimeTest(int i) {
		logger.info("" + i + DateTime.parse("20180908", dft).toDate());
	}
}
