package synchronizers.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.SynchronousQueue;

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
public class SynchronousQueueTest {
	private SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>();
	private static Logger logger = Logger.getLogger(SynchronousQueueTest.class);
	private CountDownLatch latch = new CountDownLatch(1);

	public static void start() {
		logger = Log4jUtils.initLog4j();
		SynchronousQueueTest t = new SynchronousQueueTest();
		Producer p = t.new Producer();
		Consumer c = t.new Consumer();

		new Thread(p).start();
		new Thread(c).start();
		try {
			logger.info("main prepare ");
			Thread.currentThread().sleep(2000);
			logger.info("main open latch");
		} catch (InterruptedException ex) {
			logger.info("InterruptedException");
		}

		t.getLatch().countDown();
	}

	class Producer implements Runnable {

		public void run() {
			try {
				logger.info("Producer waitting");
				latch.await();
				while (!Thread.currentThread().isInterrupted()) {
					logger.info("Producer putting.");
					queue.put(1);
					logger.info("Producer put done and sleeping.");
					Thread.currentThread().sleep(4000);
				}
			} catch (InterruptedException ex) {
				logger.info("InterruptedException");
			}

		}

	}

	class Consumer implements Runnable {
		public void run() {
			try {
				logger.info("Consumer waitting");
				latch.await();
				while (!Thread.currentThread().isInterrupted()) {
					logger.info("Consumer taking.");
					queue.take();
					logger.info("Consumer take done and sleeping.");
					Thread.currentThread().sleep(8000);
				}
			} catch (InterruptedException ex) {
				logger.info("InterruptedException");
			}

		}

	}

	public CountDownLatch getLatch() {
		return latch;
	}

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}

	public static void main(String[] args) {
		start();
	}
}