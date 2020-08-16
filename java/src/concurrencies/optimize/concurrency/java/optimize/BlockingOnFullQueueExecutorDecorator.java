package concurrency.java.optimize;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

import javax.annotation.Nonnull;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */

public class BlockingOnFullQueueExecutorDecorator implements Executor {

	private static final class PermitReleasingDecorator implements Runnable {

		@Nonnull
		private final Runnable delegate;

		@Nonnull
		private final Semaphore semaphore;

		private PermitReleasingDecorator(@Nonnull final Runnable task, @Nonnull final Semaphore semaphoreToRelease) {
			this.delegate = task;
			this.semaphore = semaphoreToRelease;
		}

		@Override
		public void run() {
			try {
				this.delegate.run();
			} finally {
				// however execution goes, release permit for next task
				this.semaphore.release();
			}
		}

		@Override
		public final String toString() {
			return String.format("%s[delegate='%s']", getClass().getSimpleName(), this.delegate);
		}
	}

	@Nonnull
	private final Semaphore taskLimit;

	@Nonnull
	private final Duration timeout;

	@Nonnull
	private final Executor delegate;

	public BlockingOnFullQueueExecutorDecorator(@Nonnull final Executor executor, final int maximumTaskNumber,
			@Nonnull final Duration maximumTimeout) {
		this.delegate = Objects.requireNonNull(executor, "'executor' must not be null");
		if (maximumTaskNumber < 1) {
			throw new IllegalArgumentException(
					String.format("At least one task must be permitted, not '%d'", maximumTaskNumber));
		}
		this.timeout = Objects.requireNonNull(maximumTimeout, "'maximumTimeout' must not be null");
		if (this.timeout.isNegative()) {
			throw new IllegalArgumentException("'maximumTimeout' must not be negative");
		}
		this.taskLimit = new Semaphore(maximumTaskNumber);
	}

	@Override
	public final void execute(final Runnable command) {
		Objects.requireNonNull(command, "'command' must not be null");
		try {
			// attempt to acquire permit for task execution
			if (!this.taskLimit.tryAcquire(this.timeout.toMillis(), MILLISECONDS)) {
				throw new RejectedExecutionException(String.format("Executor '%s' busy", this.delegate));
			}
		} catch (final InterruptedException e) {
			// restore interrupt status
			Thread.currentThread().interrupt();
			throw new IllegalStateException(e);
		}

		this.delegate.execute(new PermitReleasingDecorator(command, this.taskLimit));
	}

	@Override
	public final String toString() {
		return String.format("%s[availablePermits='%s',timeout='%s',delegate='%s']", getClass().getSimpleName(),
				this.taskLimit.availablePermits(), this.timeout, this.delegate);
	}
}
