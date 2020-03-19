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
public class TaskProducer extends Producer<Task> {

	public TaskProducer(BlockingQueue<Task> bq) {
		super(bq);
	}

}
