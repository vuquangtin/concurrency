package synchronizers.phaser.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

import synchronizers.LockStepExample;

/**
 * 
 * Lastly, we show the LockStepPhaser. We can reuse the phaser for the batches,
 * like with the CyclicBarrier. The Phaser also knows which phase it is in, thus
 * we do not need to pass along the batch number. And the task() method? All the
 * complicated interrupt handling code gets reduced to a one-liner
 * phaser.arriveAndAwaitAdvance(). Simply brilliant!
 * 
 * 
 * 
 * Some more reasons why Phaser is the preferred solution over CountDownLatch
 * and CyclicBarrier: It is implemented with a ManagedBlocker. This means that
 * if our Phaser blocks a thread in the common fork-join pool, another will be
 * created to keep the parallelism at the desired level. Also, we can set up
 * Phaser in a tree to reduce contention. This is a bit complicated, I admit.
 * But it can be done. We cannot do this with the other synchronizers like
 * CountDownLatch and CyclicBarrier.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class LockStepPhaser extends LockStepExample {
	public static void main(String... args) {
		LockStepPhaser lse = new LockStepPhaser();
		ExecutorService pool = Executors.newFixedThreadPool(TASKS_PER_BATCH);
		Phaser phaser = new Phaser(TASKS_PER_BATCH);
		for (int batch = 0; batch < BATCHES; batch++) {
			for (int i = 0; i < TASKS_PER_BATCH; i++) {
				pool.submit(() -> lse.task(phaser));
			}
		}
		pool.shutdown();
	}

	public void task(Phaser phaser) {
		phaser.arriveAndAwaitAdvance();
		doTask(phaser.getPhase());
	}
}
