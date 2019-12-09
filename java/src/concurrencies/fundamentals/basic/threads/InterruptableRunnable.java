package basic.threads;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class InterruptableRunnable implements Runnable {
	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			// Heavy operation
		}
	}
}