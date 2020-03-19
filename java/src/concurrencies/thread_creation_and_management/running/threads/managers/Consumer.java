package running.threads.managers;

import java.util.concurrent.BlockingQueue;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class Consumer<T> {

	private BlockingQueue<T> bq;

	public Consumer(BlockingQueue<T> bq) {
		super();
		this.bq = bq;

	}

	/**
	 * Retrieves and removes the head of this queue, waiting if necessary until
	 * an element becomes
	 *
	 * @return
	 * @throws InterruptedException
	 *             - if interrupted while waiting
	 */
	public T take() throws InterruptedException {
		return bq.take();
	}

}
