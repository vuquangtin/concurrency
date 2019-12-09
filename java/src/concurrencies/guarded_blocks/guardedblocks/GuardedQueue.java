package guardedblocks;

import java.util.LinkedList;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class GuardedQueue {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GuardedQueue.class);
	private final Queue<Integer> sourceList;

	public GuardedQueue() {
		this.sourceList = new LinkedList<>();
	}

	/**
	 * @return last element of a queue if queue is not empty
	 */
	public synchronized Integer get() {
		while (sourceList.isEmpty()) {
			try {
				LOGGER.info("waiting");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		LOGGER.info("getting");
		return sourceList.peek();
	}

	/**
	 * @param e
	 *            number which we want to put to our queue
	 */
	public synchronized void put(Integer e) {
		LOGGER.info("putting");
		sourceList.add(e);
		LOGGER.info("notifying");
		notify();
	}
}