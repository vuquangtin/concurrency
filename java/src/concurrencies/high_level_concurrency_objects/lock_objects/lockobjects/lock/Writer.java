package lockobjects.lock;

import java.util.concurrent.locks.Lock;

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
public class Writer implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(Writer.class);

	private Lock writeLock;

	private String name;

	private long writingTime;

	/**
	 * Create new Writer who writes for 250ms
	 * 
	 * @param name
	 *            - Name of the thread owning the writer
	 * @param writeLock
	 *            - Lock for this writer
	 */
	public Writer(String name, Lock writeLock) {
		this(name, writeLock, 250L);
	}

	/**
	 * Create new Writer
	 * 
	 * @param name
	 *            - Name of the thread owning the writer
	 * @param writeLock
	 *            - Lock for this writer
	 * @param writingTime
	 *            - amount of time (in milliseconds) for this reader to engage
	 *            writing
	 */
	public Writer(String name, Lock writeLock, long writingTime) {
		this.name = name;
		this.writeLock = writeLock;
		this.writingTime = writingTime;
	}

	@Override
	public void run() {
		writeLock.lock();
		try {
			write();
		} catch (InterruptedException e) {
			LOGGER.info("InterruptedException when writing", e);
			Thread.currentThread().interrupt();
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Simulate the write operation
	 */
	public void write() throws InterruptedException {
		LOGGER.info("{} begin", name);
		Thread.sleep(writingTime);
		LOGGER.info("{} finished after writing {}ms", name, writingTime);
	}
}