package synchronizers.phaser2;

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

public class PhaserTest7 {
	private static final int TASKSPERPHASER = 4;

	public static void main(String[] args) {
		final int phaseToTerminate = 3;

		Phaser phaser = new Phaser() {
			@Override
			protected boolean onAdvance(int phase, int registeredParties) {
				System.out.println("====== " + phase + " ======");
				return phase == phaseToTerminate || registeredParties == 0;
			}
		};

		final Task07[] tasks = new Task07[10];
		Builder(tasks, 0, tasks.length, phaser);

		for (int i = 0; i < tasks.length; i++) {
			System.out.println("starting thread, id: " + i);
			final Thread thread = new Thread(tasks[i]);
			thread.start();
		}
	}

	private static void Builder(Task07[] tasks, int lo, int hi, Phaser phaser) {
		if (hi - lo > TASKSPERPHASER) {
			for (int i = lo; i < hi; i += TASKSPERPHASER) {
				int j = Math.min(i + TASKSPERPHASER, hi);
				Builder(tasks, i, j, new Phaser(phaser));
			}
		} else {
			for (int i = lo; i < hi; ++i)
				tasks[i] = new Task07(i, phaser);
		}
	}

	static class Task07 implements Runnable {
		private final Phaser phaser;
		private int i;

		Task07(int i, Phaser phaser) {
			this.i = i;
			this.phaser = phaser;
			this.phaser.register();
		}

		@Override
		public void run() {
			while (!phaser.isTerminated()) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
				}
				System.out.println("in Task.run(), phase: " + phaser.getPhase()
						+ ", id: " + this.i);
				phaser.arriveAndAwaitAdvance();
			}
		}
	}
}