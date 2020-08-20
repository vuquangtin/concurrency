package concurrency.java.tiny.netty;

import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
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
public interface EventExecutorGroup extends Executor, Iterable<EventExecutor> {

	EventExecutor next();

	@Override
	Iterator<EventExecutor> iterator();

	boolean isShuttingDown();

	boolean isTerminated();

	CompletableFuture<?> shutdownGracefully();

	CompletableFuture<?> shutdownGracefully(long quietPeriod, long timeout,
			TimeUnit timeUnit);

	CompletableFuture<?> terminationFuture();

	boolean awaitTermination(long timeout, TimeUnit timeUnit)
			throws InterruptedException;
}