package basic.containers;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

import basic.annotations.ThreadSafe;

@Slf4j
@ThreadSafe
public class ConcurrentSkipListMapExample {
	private static Map<Integer, Integer> map = new ConcurrentSkipListMap<>();
	static Logger logger = Logger.getLogger(ConcurrentSkipListMapExample.class.getName());
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
					add(i);
					semaphore.release();
				} catch (Exception e) {
					e.printStackTrace();
				}
				countDownLatch.countDown();
			});
		}
		countDownLatch.await();
		exec.shutdown();
		logger.info("map size = {}" + map.size());
	}

	private static void add(int i) {
		map.put(i, i);
	}

}
