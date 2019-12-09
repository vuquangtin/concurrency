package basic.threads.unsafe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

import basic.annotations.NotThreadSafe;

@Slf4j
@NotThreadSafe
public class ArrayListExample {
	static Logger logger = Logger.getLogger(CollectionSyncExample.class.getName());
	private static List<Integer> list = new ArrayList<>();

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
		logger.info("list size = {}" + list.size());
	}

	private static void add() {
		list.add(1);
	}

}
