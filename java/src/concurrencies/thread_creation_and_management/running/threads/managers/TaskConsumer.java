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
public class TaskConsumer extends Consumer<Task> {

	public TaskConsumer(BlockingQueue<Task> bq) {
		super(bq);
	}

}