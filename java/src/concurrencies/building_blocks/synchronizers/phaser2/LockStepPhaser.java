package synchronizers.phaser2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

import synchronizers.all.LockStepExample;

/**
 * 
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
		final int parties = TASKS_PER_BATCH;
		ExecutorService pool = Executors.newFixedThreadPool(parties);
		Phaser phaser = new Phaser(parties) {
			@Override
			protected boolean onAdvance(int phase, int registeredParties) {
				System.out.println("phase:" + phase + "\tregisteredParties:"
						+ registeredParties);
				return super.onAdvance(phase, registeredParties);
			}
		};

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