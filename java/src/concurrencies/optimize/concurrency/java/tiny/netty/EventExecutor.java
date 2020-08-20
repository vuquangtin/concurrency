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
public interface EventExecutor extends EventExecutorGroup {

	EventExecutorGroup parent();

	@Override
	EventExecutor next();

	boolean inEventLoop();

	boolean inEventLoop(Thread thread);

	@Override
	boolean isShuttingDown();

	@Override
	boolean isTerminated();

	@Override
	CompletableFuture<?> shutdownGracefully();

	@Override
	CompletableFuture<?> shutdownGracefully(long quietPeriod, long timeout,
			TimeUnit timeUnit);

	@Override
	CompletableFuture<?> terminationFuture();

	@Override
	boolean awaitTermination(long timeout, TimeUnit timeUnit)
			throws InterruptedException;
}
