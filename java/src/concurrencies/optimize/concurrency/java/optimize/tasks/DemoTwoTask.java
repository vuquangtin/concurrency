package concurrency.java.optimize.tasks;

import java.util.Random;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class DemoTwoTask extends Task {
	public Object call() throws Exception {
		int pause = new Random().nextInt(1000);
		System.out.println("=========demo " + pause + " two ========");
		Thread.sleep(pause);
		return new Random().nextInt(1000);

	}
}