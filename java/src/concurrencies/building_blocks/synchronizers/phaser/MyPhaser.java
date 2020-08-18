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
public class MyPhaser extends Phaser {
	@Override
	protected boolean onAdvance(int phase, int registeredParties) {
		System.out
				.println("----------------------- onAdvance begin -----------------------");
		System.out.println("phase = " + phase + ", arrived parts = "
				+ this.getArrivedParties() + ", registered parts "
				+ registeredParties + ", thread = "
				+ Thread.currentThread().getName());
		System.out
				.println("------------------------ onAdvance end ------------------------");
		return super.onAdvance(phase, registeredParties);
	}
}