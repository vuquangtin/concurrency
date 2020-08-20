package concurrency.java.tiny.netty;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class DefaultThreadFactory implements ThreadFactory {

	private final static AtomicInteger factoryNumber = new AtomicInteger();

	private AtomicInteger index = new AtomicInteger();
	private String prefixName;

	public DefaultThreadFactory(String prefix) {
		this.prefixName = prefix + "-" + factoryNumber.incrementAndGet() + "-";
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(r);
		thread.setName(prefixName + index.incrementAndGet());
		return thread;
	}
}