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
public class Reader implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(Reader.class);

	private Lock readLock;

	private String name;

	private long readingTime;

	/**
	 * Create new Reader
	 * 
	 * @param name
	 *            - Name of the thread owning the reader
	 * @param readLock
	 *            - Lock for this reader
	 * @param readingTime
	 *            - amount of time (in milliseconds) for this reader to engage
	 *            reading
	 */
	public Reader(String name, Lock readLock, long readingTime) {
		this.name = name;
		this.readLock = readLock;
		this.readingTime = readingTime;
	}

	/**
	 * Create new Reader who reads for 250ms
	 * 
	 * @param name
	 *            - Name of the thread owning the reader
	 * @param readLock
	 *            - Lock for this reader
	 */
	public Reader(String name, Lock readLock) {
		this(name, readLock, 250L);
	}

	@Override
	public void run() {
		readLock.lock();
		try {
			read();
		} catch (InterruptedException e) {
			LOGGER.info("InterruptedException when reading", e);
			Thread.currentThread().interrupt();
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * Simulate the read operation
	 * 
	 */
	public void read() throws InterruptedException {
		LOGGER.info("{} begin", name);
		Thread.sleep(readingTime);
		LOGGER.info("{} finish after reading {}ms", name, readingTime);
	}
}