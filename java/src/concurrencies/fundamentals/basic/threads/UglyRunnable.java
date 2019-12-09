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
public class UglyRunnable implements Runnable {
	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			// Heavy operation
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Other operation
		}
	}
}
