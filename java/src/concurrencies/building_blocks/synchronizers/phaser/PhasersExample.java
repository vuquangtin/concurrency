package synchronizers.phaser;

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
public class PhasersExample {
	public static void main(String[] args) {

		/*
		 * Creates a new phaser with 1 registered unArrived parties and initial
		 * phase number is 0
		 */
		Phaser phaser = new Phaser(1);
		System.out.println("new phaser with 1 registered unArrived parties"
				+ " created and initial phase  number is 0 ");

		// Create 3 threads
		Thread thread1 = new Thread(new MyRunnable(phaser, "first"), "Thread-1");
		Thread thread2 = new Thread(new MyRunnable(phaser, "second"),
				"Thread-2");
		Thread thread3 = new Thread(new MyRunnable(phaser, "third"), "Thread-3");

		System.out.println("\n--------Phaser has started---------------");
		// Start 3 threads
		thread1.start();
		thread2.start();
		thread3.start();

		// get current phase
		int currentPhase = phaser.getPhase();
		/*
		 * arriveAndAwaitAdvance() will cause thread to wait until current phase
		 * has been completed i.e. until all registered threads call
		 * arriveAndAwaitAdvance()
		 */
		phaser.arriveAndAwaitAdvance();
		System.out.println("------Phase-" + currentPhase
				+ " has been COMPLETED----------");

		// ------NEXT PHASE BEGINS------

		currentPhase = phaser.getPhase();
		phaser.arriveAndAwaitAdvance();
		System.out.println("------Phase-" + currentPhase
				+ " has been COMPLETED----------");

		/*
		 * current thread Arrives and deRegisters from phaser. DeRegistration
		 * reduces the number of parties that may be required to advance in
		 * future phases.
		 */
		phaser.arriveAndDeregister();

		// check whether phaser has been terminated or not.
		if (phaser.isTerminated())
			System.out.println("\nPhaser has been terminated");

	}
}

class MyRunnable implements Runnable {

	Phaser phaser;

	MyRunnable(Phaser phaser, String name) {
		this.phaser = phaser;
		this.phaser.register(); // Registers/Add a new unArrived party to this
								// phaser.
		System.out.println(name + " - New unarrived party has "
				+ "been registered with phaser");
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()
				+ " - party has arrived and is working in " + "Phase-"
				+ phaser.getPhase());
		phaser.arriveAndAwaitAdvance();

		// Sleep has been used for formatting output
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// ------NEXT PHASE BEGINS------

		System.out.println(Thread.currentThread().getName()
				+ " - party has arrived and is working in " + "Phase-"
				+ phaser.getPhase());
		phaser.arriveAndAwaitAdvance();

		phaser.arriveAndDeregister();
	}

}

/*-OUTPUT

 new phaser with 1 registered unArrived parties created and initial phase  number is 0
 first - New unarrived party has been registered with phaser
 second - New unarrived party has been registered with phaser
 third - New unarrived party has been registered with phaser

 --------Phaser has started---------------
 Thread-1 - party has arrived and is working in Phase-0
 Thread-2 - party has arrived and is working in Phase-0
 Thread-3 - party has arrived and is working in Phase-0
 ------Phase-0 has been COMPLETED----------
 Thread-3 - party has arrived and is working in Phase-1
 Thread-2 - party has arrived and is working in Phase-1
 Thread-1 - party has arrived and is working in Phase-1
 ------Phase-1 has been COMPLETED----------

 Phaser has been terminated

 */