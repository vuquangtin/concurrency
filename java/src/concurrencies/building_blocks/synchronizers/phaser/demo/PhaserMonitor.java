package synchronizers.phaser.demo;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class PhaserMonitor {

	public static void main(String[] args) throws Exception {

		Phaser phaser = new Phaser(3);

		for (int i = 0; i < 3; i++) {
			Task task = new Task(i + 1, phaser);
			Thread thread = new Thread(task);
			thread.start();
		}

		for (int i = 0; i < 10; i++) {
			System.out.printf("********************\n");
			System.out.printf("Main: Phaser Log\n");
			System.out.printf("Main: Phaser: Phase: %d\n", phaser.getPhase());
			System.out.printf("Main: Phaser: Registered Parties: %d\n",
					phaser.getRegisteredParties());
			System.out.printf("Main: Phaser: Arrived Parties: %d\n",
					phaser.getArrivedParties());
			System.out.printf("Main: Phaser: Unarrived Parties: %d\n",
					phaser.getUnarrivedParties());
			System.out.printf("********************\n");

			TimeUnit.SECONDS.sleep(1);
		}
	}

	public static class Task implements Runnable {

		private int time;

		private Phaser phaser;

		public Task(int time, Phaser phaser) {
			this.time = time;
			this.phaser = phaser;
		}

		@Override
		public void run() {
			/*
			 * Arrive to the phaser
			 */
			phaser.arrive();
			/*
			 * Phase 1
			 */
			System.out.printf("%s: Entering phase 1.\n", Thread.currentThread()
					.getName());
			try {
				TimeUnit.SECONDS.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.printf("%s: Finishing phase 1.\n", Thread
					.currentThread().getName());
			/*
			 * End of Phase 1
			 */
			phaser.arriveAndAwaitAdvance();
			/*
			 * Phase 2
			 */
			System.out.printf("%s: Entering phase 2.\n", Thread.currentThread()
					.getName());
			try {
				TimeUnit.SECONDS.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.printf("%s: Finishing phase 2.\n", Thread
					.currentThread().getName());
			/*
			 * End of Phase 2
			 */
			phaser.arriveAndAwaitAdvance();
			/*
			 * Phase 3
			 */
			System.out.printf("%s: Entering phase 3.\n", Thread.currentThread()
					.getName());
			try {
				TimeUnit.SECONDS.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.printf("%s: Finishing phase 3.\n", Thread
					.currentThread().getName());
			/*
			 * End of Phase 3
			 */
			phaser.arriveAndDeregister();
		}
	}

}