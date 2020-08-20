package synchronizers.phaser2;

import java.util.Random;
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
public class PhaserExample {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();

		Phaser phaser = new Phaser() {
			@Override
			protected boolean onAdvance(int phase, int registeredParties) {
				System.out.println("phase : " + phase
						+ ", registeredParties : " + registeredParties);
				return phase >= 1;
			}
		};
		for (int i = 0; i < 10; i++) {
			executorService.submit(new SomeThread(phaser));
		}
		System.out.println("terminated : " + phaser.isTerminated()
				+ ", phase : " + phaser.getPhase() + ", registered : "
				+ phaser.getRegisteredParties() + ", arrived : "
				+ phaser.getArrivedParties() + ", unarrived : "
				+ phaser.getUnarrivedParties());
		int phase = phaser.awaitAdvance(0);
		System.out.println("terminated : " + phaser.isTerminated()
				+ ", phase : " + phase + ", registered : "
				+ phaser.getRegisteredParties() + ", arrived : "
				+ phaser.getArrivedParties() + ", unarrived : "
				+ phaser.getUnarrivedParties());

		for (int i = 0; i < 10; i++) {
			executorService.submit(new SomeThread(phaser));
		}
		System.out.println("terminated : " + phaser.isTerminated()
				+ ", phase : " + phaser.getPhase() + ", registered : "
				+ phaser.getRegisteredParties() + ", arrived : "
				+ phaser.getArrivedParties() + ", unarrived : "
				+ phaser.getUnarrivedParties());
		phase = phaser.awaitAdvance(1);
		System.out.println("terminated : " + phaser.isTerminated()
				+ ", phase : " + phase + ", registered : "
				+ phaser.getRegisteredParties() + ", arrived : "
				+ phaser.getArrivedParties() + ", unarrived : "
				+ phaser.getUnarrivedParties());

		// phase = phaser.awaitAdvance(1);
		// System.out.println("terminated : " + phaser.isTerminated() +
		// ", phase : " + phase + ", registered : " +
		// phaser.getRegisteredParties() + ", arrived : " +
		// phaser.getArrivedParties() + ", unarrived : " +
		// phaser.getUnarrivedParties());
		executorService.shutdown();
	}
}

class SomeThread implements Runnable {

	private Phaser phaser;
	private static final Random RANDOM = new Random();

	SomeThread(Phaser phaser) {

		this.phaser = phaser;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getId() + " started...");
		execute();
		// execute();
	}

	private void execute() {
		phaser.register();
		try {
			Thread.sleep(5000 - RANDOM.nextInt(2500));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int arriveId = phaser.arriveAndDeregister();
		System.out.println(Thread.currentThread().getId()
				+ " done. Arrived at " + arriveId + ", terminated : "
				+ phaser.isTerminated() + ", unarrived : "
				+ phaser.getUnarrivedParties());
	}
}
