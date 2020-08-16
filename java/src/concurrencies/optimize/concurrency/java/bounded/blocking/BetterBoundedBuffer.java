package concurrency.java.bounded.blocking;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.java.concurrency.Building_Custom_Synchronizers.BaseBoundedBuffer;

import concurrency.java.optimize.JavaThreadingOptimizationUsingThreadPoolExecutor;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class BetterBoundedBuffer<V> extends BaseBoundedBuffer<V> {
	// 条件谓词: not-full (!isFul1())
	// 条件谓词: not - empty (!isEmpty())
	static Logger logger = Logger.getLogger(BetterBoundedBuffer.class.getName());

	public BetterBoundedBuffer(int size) {
		super(size);
	}

	/**
	 * 阻塞，直到: not-full
	 * 
	 * @param v
	 * @throws InterruptedException
	 */
	public synchronized void put(V v) throws InterruptedException {
		while (isFull()) {
			logger.info("线程{}put" + Thread.currentThread().getName());
			wait();
		}
		// 对BoundedBuffer进行优化
		boolean empty = isEmpty();
		doPut(v);
		if (empty) {
			// notifyAll(); // 会丢失通知
			notify();
		}
		logger.info("线程{}put唤醒" + Thread.currentThread().getName());
	}

	/**
	 * 阻塞，直到: not - empty
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public synchronized V take() throws InterruptedException {
		String name = Thread.currentThread().getName();
		while (isEmpty()) {
			logger.info("{}take" + name);
			Thread.sleep(3000);
			wait();
		}
		boolean ful1 = isFull();
		V v = doTake();
		if (ful1) {
			notifyAll();
			// notify(); // 会丢失通知
		}
		logger.info("{}take唤醒" + name);
		return v;
	}

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(6);
		final BetterBoundedBuffer boundedBuffer = new BetterBoundedBuffer(5);
		for (int i = 1; i < 7; i++) {
			int finalI = i;
			executorService.submit(() -> {
				Thread.currentThread().setName("线程" + finalI);
				try {
					if (finalI == 6) {
						Thread.sleep(5000);
						boundedBuffer.put(finalI);
					} else {
						boundedBuffer.take();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
		executorService.shutdown();
	}
}