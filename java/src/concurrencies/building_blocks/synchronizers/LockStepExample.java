package synchronizers;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public abstract class LockStepExample {
	protected final static int TASKS_PER_BATCH = 3;
	protected final static int BATCHES = 5;

	protected final void doTask(int batch) {
		System.out.printf("Task start %d%n", batch);
		int ms = ThreadLocalRandom.current().nextInt(500, 3000);
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		System.out.printf("Task in batch %d took %dms%n", batch, ms);
	}
}