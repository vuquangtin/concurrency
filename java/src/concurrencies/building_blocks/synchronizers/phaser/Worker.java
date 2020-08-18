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
public class Worker extends Thread {
	Phaser phaser;
	private int result = 0;

	public Worker(Phaser phaser) {
		this.phaser = phaser;
	}

	@Override
	public void run() {
		System.out.println(this.getName() + " waiting to start, phase = "
				+ phaser.getPhase());
		int phase = phaser.awaitAdvance(0); // blockiert und liefert 1

		System.out.println(this.getName() + " running, phase = " + phase);
		Be.idleFor(200);
		System.out.println(this.getName() + " end");
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
}