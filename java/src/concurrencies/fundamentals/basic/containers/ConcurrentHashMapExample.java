package basic.containers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

import basic.annotations.ThreadSafe;

@Slf4j
@ThreadSafe
public class ConcurrentHashMapExample {
	static Logger logger = Logger.getLogger(ConcurrentHashMapExample.class
			.getName());
	private static int threadNum = 200;
	private static int clientNum = 5000;
	private static Map<Integer, Integer> map = new ConcurrentHashMap<>();

	public static void main(String[] args) throws InterruptedException {

		ExecutorService exec = Executors.newCachedThreadPool();
		final Semaphore semaphore = new Semaphore(threadNum);
		final CountDownLatch countDownLatch = new CountDownLatch(clientNum);

		for (int i = 0; i < clientNum; i++) {

			final int threadNum = i;

			exec.execute(() -> {
				try {
					semaphore.acquire();
					func(threadNum);
					semaphore.release();
				} catch (Exception e) {
					System.out.println(e);
				}
				countDownLatch.countDown();
			});

		}
		countDownLatch.await();
		exec.shutdown();
		System.out.println("size : " + map.size());
	}

	public static void func(int threadNum) {
		map.put(threadNum, threadNum);
	}

}
