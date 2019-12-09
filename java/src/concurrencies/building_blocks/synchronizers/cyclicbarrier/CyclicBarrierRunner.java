package synchronizers.cyclicbarrier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public final class CyclicBarrierRunner {

	private final List<Runnable> runnables;
	private final long timeout;
	private final TimeUnit type;
	private final AtomicReference<Throwable> exception = new AtomicReference<>();
	private final ThreadFactory threadFactory;

	// Latch for counting daemon threads.
	private CountDownLatch latch;

	/**
	 * Creates a {@link CyclicBarrierRunner} that blocks for the specified
	 * amount of time for all jobs to complete.
	 */
	public CyclicBarrierRunner(long timeout, TimeUnit type) {
		this.runnables = new ArrayList<>();
		this.timeout = timeout;
		this.type = type;
		this.threadFactory = new ThreadFactoryBuilder().setDaemon(true)
				.setNameFormat("cyclic-barrier-runner-%d")
				.setUncaughtExceptionHandler((t, e) -> setException(e)).build();
	}

	/**
	 * Submits a job.
	 */
	public void submit(Runnable runnable) {
		runnables.add(runnable);
	}

	/**
	 * Executes all jobs submitted. Blocks until all jobs are executed for the
	 * time specified upon construction. If any of the jobs complete
	 * exceptionally, then the rest of the jobs are abandoned and the exception
	 * is propagated to the main thread as a RuntimeException.
	 *
	 * @return whether all jobs completed on time.
	 * @throws RuntimeException
	 *             if any of the jobs complete exceptionally.
	 */
	public boolean execute() {
		int size = runnables.size();
		latch = new CountDownLatch(size);
		CyclicBarrier barrier = new CyclicBarrier(size + 1); // + 1 for final
																// barrier.
		// Modify all runnables with barriers and latches
		List<Runnable> modifiedRunnables = new ArrayList<>(size);
		for (Runnable runnable : runnables) {
			modifiedRunnables.add(() -> {
				try {
					barrier.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					throw new RuntimeException(e);
				}
				runnable.run();
				latch.countDown();
			});
		}
		// Queue up all jobs for running.
		for (Runnable runnable : modifiedRunnables) {
			threadFactory.newThread(runnable).start();
		}
		// Run the jobs
		boolean success;
		try {
			barrier.await();
			// block until everything is done.
			success = latch.await(timeout, type);
		} catch (InterruptedException | BrokenBarrierException e) {
			throw new RuntimeException(e);
		}
		Throwable ex = exception.get();
		if (ex != null) {
			// Propagate exception to main thread.
			throw new RuntimeException(ex);
		}
		return success;
	}

	private synchronized void setException(Throwable throwable) {
		exception.set(throwable);
		// Fail every other task fast.
		while (latch.getCount() > 0) {
			latch.countDown();
		}
	}
}