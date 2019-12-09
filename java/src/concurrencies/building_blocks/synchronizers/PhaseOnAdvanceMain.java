package synchronizers;

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

public class PhaseOnAdvanceMain {
	public static void main(String[] args) {
		Phaser phaser = new Phaser() {
			protected boolean onAdvance(int phase, int parties) {
				System.out.println("Inside onAdvance(): phase  = " + phase
						+ ",  Registered Parties = " + parties);
				// Do not terminate the phaser by returning false
				return false;
			}
		};
		// Register the self (the "main" thread) as a party
		phaser.register();
		System.out.println("#1: isTerminated():" + phaser.isTerminated());
		// Since we have only one party registered, this arrival will advance
		// the phaser and registered parties reduces to zero
		phaser.arriveAndDeregister();
//
//		final int TASK_COUNT = 3;
//		phaser.bulkRegister(TASK_COUNT);
//		for (int i = 1; i <= TASK_COUNT; i++) {
//			String taskName = "Task #" + i;
//			MyTask task = new MyTask(taskName, phaser);
//			task.start();
//		}

		// Trigger another phase advance
		phaser.register();
		phaser.arriveAndDeregister();

		System.out.println("#2: isTerminated():" + phaser.isTerminated());
		phaser.forceTermination();
		System.out.println("#3: isTerminated():" + phaser.isTerminated());
	}
}

class MyTask extends Thread {
	private Phaser phaser;
	private String taskName;

	public MyTask(String taskName, Phaser phaser) {
		this.taskName = taskName;
		this.phaser = phaser;
	}

	@Override
	public void run() {
		System.out.println(taskName + ":Initializing...");

		int sleepTime = 3;
		try {
			Thread.sleep(sleepTime * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(taskName + ":Initialized...");
		// Wait for all parties to arrive to start the task
		phaser.arriveAndAwaitAdvance();
		System.out.println(taskName + ":Started...");
	}
}