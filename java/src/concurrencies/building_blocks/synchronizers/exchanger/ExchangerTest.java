package synchronizers.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import synchronizers.models.DataBuffer;
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
public class ExchangerTest {
	private static Logger logger = Logger.getLogger(ExchangerTest.class);
	Exchanger<DataBuffer> exchanger = new Exchanger<DataBuffer>();
	DataBuffer initialEmptyBuffer = new DataBuffer(10);
	DataBuffer initialFullBuffer = new DataBuffer(10);
	AtomicInteger counter = new AtomicInteger();

	class FillingLoop implements Runnable {
		public void run() {
			DataBuffer currentBuffer = initialEmptyBuffer;
			try {
				while (currentBuffer != null) {
					addToBuffer(currentBuffer);
					if (currentBuffer.isFull()) {
						logger.info("Fill exchanging["
								+ counter.incrementAndGet() + "]");
						currentBuffer = exchanger.exchange(currentBuffer);
						logger.info("Fill exchanged["
								+ counter.incrementAndGet() + "]");
					}
				}
			} catch (InterruptedException ex) {
			}
		}

		public void addToBuffer(DataBuffer currentBuffer) {
			currentBuffer.add("hello");
			//logger.info("add:" + currentBuffer.get());
			try {
				Thread.currentThread().sleep(500);
			} catch (InterruptedException ex) {
				logger.info("InterruptedException");
			}
		}
	}

	class EmptyingLoop implements Runnable {
		public void run() {
			DataBuffer currentBuffer = initialFullBuffer;
			try {
				while (currentBuffer != null) {
					takeFromBuffer(currentBuffer);
					if (currentBuffer.isEmpty()) {
						logger.info("EmptyingLoop exchanging["
								+ counter.incrementAndGet() + "]");
						currentBuffer = exchanger.exchange(currentBuffer);
						logger.info("EmptyingLoop exchanged["
								+ counter.incrementAndGet() + "]");
					}

				}
			} catch (InterruptedException ex) {
			}
		}

		public void takeFromBuffer(DataBuffer currentBuffer) {
			currentBuffer.take();
			//logger.info("take:" + currentBuffer.take());
			try {
				Thread.currentThread().sleep(500);
			} catch (InterruptedException ex) {
				logger.info("InterruptedException");
			}

		}
	}

	void start() {
		new Thread(new FillingLoop()).start();
		new Thread(new EmptyingLoop()).start();
	}

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		ExchangerTest t = new ExchangerTest();
		t.start();
	}
}
