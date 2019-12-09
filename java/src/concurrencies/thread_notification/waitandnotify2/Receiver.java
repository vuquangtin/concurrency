package waitandnotify2;

import java.util.concurrent.ThreadLocalRandom;

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
public class Receiver implements Runnable {
	private Data load;
	protected static Logger logger = Logger.getLogger(Receiver.class.getName());

	// standard constructors

	public Receiver(Data data) {
		this.load = data;
	}

	public void run() {
		for (String receivedMessage = load.receive(); !"End"
				.equals(receivedMessage); receivedMessage = load.receive()) {

			System.out.println(receivedMessage);

			// ...
			try {
				Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				logger.error("Thread interrupted", e);
			}
		}
	}
}
