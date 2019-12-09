package synchronizers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Phaser;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
class AdderTask extends Thread {
	private Phaser phaser;
	private String taskName;
	private List<Integer> list;

	public AdderTask(String taskName, Phaser phaser, List<Integer> list) {
		this.taskName = taskName;
		this.phaser = phaser;
		this.list = list;
	}/* www . j a va 2 s. c om */

	@Override
	public void run() {
		do {
			System.out.println(taskName + " added " + 2);
			// Add the integer to the list
			list.add(2);

			// Wait for all parties to arrive at the phaser
			phaser.arriveAndAwaitAdvance();
		} while (!phaser.isTerminated());
	}
}

/***
 * Use Phaser in multiple phases by synchronizing multiple parties in each
 * phase.
 * <ul>
 * <li>
 * Each tasks add integer in each phase and add them to a List.</li>
 * <li>
 * After the Phaser is terminated, compute the sum of all the generated
 * integers.</li>
 * </ul>
 * 
 * @author vuquangtin
 *
 */
public class MultiplePhasesMain {
	public static void main(String[] args) {
		final int PHASE_COUNT = 4;
		Phaser phaser = new Phaser() {
			public boolean onAdvance(int phase, int parties) {
				System.out.println("Phase:" + phase + ", Parties:" + parties
						+ ", Arrived:" + this.getArrivedParties());
				boolean terminatePhaser = false;
				if (phase >= PHASE_COUNT - 1 || parties == 0) {
					terminatePhaser = true;
				}
				return terminatePhaser;
			}
		};
		List<Integer> list = Collections
				.synchronizedList(new ArrayList<Integer>());
		int ADDER_COUNT = 3;
		phaser.bulkRegister(ADDER_COUNT + 1);
		for (int i = 1; i <= ADDER_COUNT; i++) {
			String taskName = "Task #" + i;
			AdderTask task = new AdderTask(taskName, phaser, list);
			task.start();
		}
		while (!phaser.isTerminated()) {
			phaser.arriveAndAwaitAdvance();
		}
		// Phaser is terminated now. Compute the sum
		int sum = 0;
		for (Integer num : list) {
			sum = sum + num;
		}
		System.out.println("Sum = " + sum);
	}
}