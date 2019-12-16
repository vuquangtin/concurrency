package threads.massage;

import org.apache.log4j.Logger;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class Data {
	private String packet;
	protected static Logger logger = Logger.getLogger(Data.class.getName());
	// True if receiver should wait
	// False if sender should wait
	private boolean transfer = true;

	public synchronized void send(String packet) {
		while (!transfer) {
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				logger.error("Thread interrupted", e);
			}
		}
		transfer = false;

		this.packet = packet;
		notifyAll();
	}

	public synchronized String receive() {
		while (transfer) {
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				logger.error("Thread interrupted", e);
			}
		}
		transfer = true;

		notifyAll();
		return packet;
	}
}