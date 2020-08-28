package concurrency.java.tiny.netty;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public abstract class AbstractEventExecutor implements EventExecutor {

	static final long DEFAULT_QUIET_SHUTDOWN_PERIOD = 2;
	static final long DEFAULT_SHUTDOWN_TIMEOUT = 15;

	private final Collection<EventExecutor> self = Arrays.asList(this);

	@Override
	public EventExecutor next() {
		return this;
	}

	@Override
	public Iterator<EventExecutor> iterator() {
		return self.iterator();
	}

	@Override
	public boolean inEventLoop() {
		return inEventLoop(Thread.currentThread());
	}

	@Override
	public CompletableFuture<?> shutdownGracefully() {
		return shutdownGracefully(DEFAULT_QUIET_SHUTDOWN_PERIOD,
				DEFAULT_SHUTDOWN_TIMEOUT, TimeUnit.SECONDS);
	}
}