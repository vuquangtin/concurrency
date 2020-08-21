package synchronizers.phaser.demo;

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
public class PhaserCountDemo {
	public static void main(String[] args) {
		MyPhaser myPhaser = new MyPhaser(1, 4);
		System.out.println("Starting\n");
		new Thread(new MyThreadPhaseDemo(myPhaser, "A")).start();
		new Thread(new MyThreadPhaseDemo(myPhaser, "B")).start();
		new Thread(new MyThreadPhaseDemo(myPhaser, "C")).start();

		// Wait for the specified number of phases to complete.
		while (!myPhaser.isTerminated()) {
			myPhaser.arriveAndAwaitAdvance();
		}
		System.out.println("The Phaser is terminated");
	}
}

class MyPhaser extends Phaser {
	int numPhases;

	MyPhaser(int parties, int phaseCount) {
		super(parties);
		numPhases = phaseCount - 1;
	}

	// Override onAdvance() to execute the specified number of phases.
	protected boolean onAdvance(int p, int regParties) {
		// This println() statement is for illustration only.
		// Normally, onAdvance() will not display output.
		System.out.println("Phase " + p + " [regParties:" + regParties
				+ "] completed.\n");

		// If all phases have completed, return true
		if (p == numPhases || regParties == 0) {
			return true;
		}
		// Otherwise, return false.
		return false;
	}
}

// A thread of execution that uses a Phaser.
class MyThreadPhaseDemo implements Runnable {

	Phaser phaser;
	String name;

	MyThreadPhaseDemo(Phaser p, String name) {
		phaser = p;
		this.name = name;
		p.register();
	}

	@Override
	public void run() {
		while (!phaser.isTerminated()) {
			System.out.println("Thread " + name + " Beginning Phase "
					+ phaser.getPhase());
			phaser.arriveAndAwaitAdvance();
			// Pause a bit to prevent jumbled output. This is for illustration
			// only, It is not required for the proper operation of the phaser.
			try {
				Thread.sleep(100);

			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
	}
}