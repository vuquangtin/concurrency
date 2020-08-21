package synchronizers.phaser.demo;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class PhaserUsage {

	public static void main(String[] args) throws InterruptedException {
		PhaserUsage usage = new PhaserUsage();
		System.out.println(System.currentTimeMillis());

		// This will register the phaser itself as a participant
		final Phaser phaser = new Phaser(1);
		List<Runnable> tasks = new LinkedList<Runnable>();
		for (int i = 0; i < 4; i++) {
			tasks.add(new PhaserUsage().new Printer(i, phaser));
		}
		ExecutorService executorService = Executors.newFixedThreadPool(tasks
				.size());
		int taskId = 0;
		for (final Runnable task : tasks) {
			System.out.println("Scheduling task " + taskId);
			executorService.submit(task);
			taskId++;
		}
		// Having the main thread arrive will result in the # of registered
		// threads matching those that have arrived
		phaser.arriveAndAwaitAdvance();
		System.out.println("End tasks " + System.currentTimeMillis());
		System.out.println("Phase " + phaser.getPhase());
		phaser.arriveAndDeregister();
		executorService.shutdown();
	}

	public class Printer implements Runnable {
		int count = 0;
		final Phaser phaser;

		public Printer(int i, Phaser phaser) {
			count = i;
			this.phaser = phaser;
			System.out.println("Thread " + count + " registered to phase "
					+ this.phaser.register());
		}

		@Override
		public void run() {
			System.out.println("This is phase " + phaser.getPhase()
					+ " and thread count " + count);
			phaser.arriveAndAwaitAdvance();
			System.out.println(count + " " + System.currentTimeMillis());
			phaser.arriveAndDeregister();
		}
	}
}
