package threadpools;

import java.util.concurrent.ThreadFactory;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class CustomThreadFactory implements ThreadFactory {
	static volatile int i;

	@Override
	public synchronized Thread newThread(Runnable r) {
		i++;
		return new Thread(r, "pool-1-thread-" + i);
	}
}