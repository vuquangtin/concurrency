package basic.containers;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

import basic.annotations.ThreadSafe;

@Slf4j
@ThreadSafe
public class ConcurrentSkipListSetExample {
	static Logger logger = Logger.getLogger(ConcurrentSkipListSetExample.class.getName());
	private static ConcurrentSkipListSet<Integer> set = new ConcurrentSkipListSet<>();
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
					setadd(i);
					semaphore.release();
				} catch (Exception e) {
					e.printStackTrace();
				}
				countDownLatch.countDown();
			});
		}
		countDownLatch.await();
		exec.shutdown();
		logger.info("set size = {}" + set.size());
	}

	private static void setadd(int i) {
		set.add(i);
	}
}
