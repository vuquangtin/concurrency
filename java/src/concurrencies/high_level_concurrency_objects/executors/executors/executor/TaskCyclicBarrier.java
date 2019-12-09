package executors.executor;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class TaskCyclicBarrier implements Runnable {
	static Logger logger = Logger.getLogger(TaskCyclicBarrier.class.getName());
	private CyclicBarrier barrier;

	public TaskCyclicBarrier(CyclicBarrier barrier) {
		this.barrier = barrier;
	}

	@Override
	public void run() {
		try {
			logger.info(Thread.currentThread().getName() + " is waiting");
			barrier.await();
			logger.info(Thread.currentThread().getName() + " is released");
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

	public void start() {

		CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
			// ...
				logger.info("All previous tasks are completed");
			});

		Thread t1 = new Thread(new TaskCyclicBarrier(cyclicBarrier), "T1");
		Thread t2 = new Thread(new TaskCyclicBarrier(cyclicBarrier), "T2");
		Thread t3 = new Thread(new TaskCyclicBarrier(cyclicBarrier), "T3");

		if (!cyclicBarrier.isBroken()) {
			t1.start();
			t2.start();
			t3.start();
		}
	}
	static Semaphore semaphore = new Semaphore(10);
	 
	public void execute() throws InterruptedException {
	 
		logger.info("Available permit : " + semaphore.availablePermits());
		logger.info("Number of threads waiting to acquire: " + 
	      semaphore.getQueueLength());
	 
	    if (semaphore.tryAcquire()) {
	        semaphore.acquire();
	        // ...
	        semaphore.release();
	    }
	 
	}
}