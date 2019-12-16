package threads.massage;

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
public class Sender implements Runnable {
	protected static Logger logger = Logger.getLogger(Sender.class.getName());
	private Data data;

	// standard constructors

	public Sender(Data data) {
		this.data = data;
	}

	public void run() {
		String packets[] = { "First packet", "Second packet", "Third packet",
				"Fourth packet", "End" };

		for (String packet : packets) {
			data.send(packet);

			// Thread.sleep() to mimic heavy server-side processing
			try {
				Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				logger.error("Thread interrupted", e);
			}
		}
	}
}