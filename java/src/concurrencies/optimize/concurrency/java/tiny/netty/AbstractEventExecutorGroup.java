package concurrency.java.tiny.netty;

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
public abstract class AbstractEventExecutorGroup implements EventExecutorGroup {

	@Override
	public CompletableFuture<?> shutdownGracefully() {
		return shutdownGracefully(AbstractEventExecutor.DEFAULT_QUIET_SHUTDOWN_PERIOD,
				AbstractEventExecutor.DEFAULT_SHUTDOWN_TIMEOUT, TimeUnit.SECONDS);
	}
}