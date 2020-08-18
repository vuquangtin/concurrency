package synchronizers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * https://www.javaspecialists.eu/archive/Issue257.html
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class LockStepCountDownLatch extends LockStepExample {
	public static void main(String... args) {
		LockStepCountDownLatch lse = new LockStepCountDownLatch();
		ExecutorService pool = Executors.newFixedThreadPool(TASKS_PER_BATCH);
		for (int batch = 0; batch < BATCHES; batch++) {
			// We need a new CountDownLatch per batch, since they
			// cannot be reset to their initial value
			CountDownLatch latch = new CountDownLatch(TASKS_PER_BATCH);
			for (int i = 0; i < TASKS_PER_BATCH; i++) {
				int batchNumber = batch + 1;
				pool.submit(() -> lse.task(latch, batchNumber));
			}
		}
		pool.shutdown();
	}

	public void task(CountDownLatch latch, int batch) {
		latch.countDown();
		boolean interrupted = Thread.interrupted();
		while (true) {
			try {
				latch.await();
				break;
			} catch (InterruptedException e) {
				interrupted = true;
			}
		}
		if (interrupted)
			Thread.currentThread().interrupt();
		doTask(batch);
	}
}
/*-
 * Task start 1
 Task start 1
 Task start 1
 Task in batch 1 took 747ms
 Task in batch 1 took 1087ms
 Task in batch 1 took 2780ms
 Task start 2
 Task start 2
 Task start 2
 Task in batch 2 took 584ms
 Task in batch 2 took 634ms
 Task in batch 2 took 2194ms
 Task start 3
 Task start 3
 Task start 3
 Task in batch 3 took 603ms
 Task in batch 3 took 1868ms
 Task in batch 3 took 2874ms
 Task start 4
 Task start 4
 Task start 4
 Task in batch 4 took 1035ms
 Task in batch 4 took 1724ms
 Task in batch 4 took 2527ms
 Task start 5
 Task start 5
 Task start 5
 Task in batch 5 took 1579ms
 Task in batch 5 took 1602ms
 Task in batch 5 took 2752ms
 */

