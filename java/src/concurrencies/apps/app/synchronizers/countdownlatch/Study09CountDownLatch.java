package app.synchronizers.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import concurrencies.utilities.Log4jUtils;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Study09CountDownLatch {
	static Logger logger = Logger.getLogger(Study09CountDownLatch.class
			.getName());

	public static void main(String[] args) throws InterruptedException {
		logger = Log4jUtils.initLog4j();
		CountDownLatch latch = new CountDownLatch(3);

		for (int i = 0; i < 3; i++) {
			Thread th = new Thread(() -> {
				try {
					System.out.println("wait start:"
							+ Thread.currentThread().getName());
					TimeUnit.SECONDS.sleep(1);
					System.out.println("wait end:  "
							+ Thread.currentThread().getName());
				} catch (InterruptedException ex) {
					logger.debug(null, ex);
				}
				latch.countDown();
			});
			th.start();
		}

		latch.await(2, TimeUnit.SECONDS); // 最大2秒wait
		if (latch.getCount() == 0) {
			System.out.println("Finish");
		} else {
			System.out.println("Timeout");
		}

	}

}