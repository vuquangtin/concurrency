package concurrency.java.executors;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import concurrency.java.optimize.JavaThreadingOptimizationUsingThreadPoolExecutor;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ParallelExecutorService {
	private final ExecutorService executorService;
	private final List<ParallelExecutor> lsParallelExecutors = new CopyOnWriteArrayList<ParallelExecutor>();
	static Logger logger = Logger.getLogger(ParallelExecutorService.class.getName());

	public ParallelExecutorService(ExecutorService executorService) {
		this.executorService = executorService;

	}

	public void shutdown() {
		for (ParallelExecutor parallelExecutor : lsParallelExecutors) {
			parallelExecutor.shutdown();
		}
		lsParallelExecutors.clear();
	}

	public ParallelExecutor newBlockingParallelExecutor(int concurrencyLevel, int capacity) {
		ParallelExecutor p = new ParallelExecutorImpl(concurrencyLevel, capacity);
		lsParallelExecutors.add(p);
		return p;
	}

	public ParallelExecutor newParallelExecutor(int concurrencyLevel) {
		ParallelExecutor parallelExecutor;
		// todo: what if concurrencyLevel == 0?
		if (concurrencyLevel > 0 && concurrencyLevel < Integer.MAX_VALUE) {
			parallelExecutor = new ParallelExecutorImpl(concurrencyLevel, Integer.MAX_VALUE);
		} else {
			parallelExecutor = new FullyParallelExecutorImpl();
		}
		lsParallelExecutors.add(parallelExecutor);
		return parallelExecutor;
	}

	// todo: it can happen that a task is sleeping successfully, after the
	// shutdown has been called.
	class FullyParallelExecutorImpl implements ParallelExecutor {

		public void execute(Runnable command) {
			executorService.execute(command);
		}

		public void execute(Runnable command, int hash) {
			executorService.execute(command);
		}

		public void shutdown() {
		}

		public int getPoolSize() {
			return 0;
		}

		public int getActiveCount() {
			return 0;
		}
	}

	private class ParallelExecutorImpl implements ParallelExecutor {
		private final ExecutionSegment[] executionSegments;
		private final AtomicInteger offerIndex = new AtomicInteger();
		private final AtomicInteger activeCount = new AtomicInteger();

		/**
		 * Creates a new ParallelExecutorImpl
		 *
		 * @param concurrencyLevel
		 *            the concurrency level
		 * @param segmentCapacity
		 *            the segment capacity. If the segment capacity is
		 *            Integer.MAX_VALUE, there is no bound on the number of
		 *            tasks that can be stored in the segment. Otherwise
		 *            offering a task to be executed can block until there is
		 *            capacity to store the task.
		 */
		private ParallelExecutorImpl(int concurrencyLevel, int segmentCapacity) {
			this.executionSegments = new ExecutionSegment[concurrencyLevel];
			for (int i = 0; i < concurrencyLevel; i++) {
				executionSegments[i] = new ExecutionSegment(segmentCapacity);
			}
		}

		public void execute(Runnable command) {
			int hash = offerIndex.incrementAndGet();
			execute(command, hash);
		}

		public void execute(Runnable command, int hash) {
			if (command == null) {
				throw new NullPointerException("Runnable is not allowed to be null");
			}
			int index = (hash == Integer.MIN_VALUE) ? 0 : Math.abs(hash) % executionSegments.length;
			ExecutionSegment segment = executionSegments[index];
			segment.offer(command);
		}

		public void shutdown() {
			for (ExecutionSegment executionSegment : executionSegments) {
				executionSegment.shutdown();
			}
		}

		public int getPoolSize() {
			int size = 0;
			for (ExecutionSegment executionSegment : executionSegments) {
				size += executionSegment.getPoolSize();
			}
			return size;
		}

		public int getActiveCount() {
			return activeCount.get();
		}

		@SuppressWarnings("SynchronizedMethod")
		private class ExecutionSegment implements Runnable {
			private final BlockingQueue<Runnable> q;
			// this flag helps to guarantee that at most 1 thread at any given
			// moment is running commands from this ExecutionSegment.
			private final AtomicBoolean active = new AtomicBoolean(false);

			private ExecutionSegment(int capacity) {
				q = new LinkedBlockingQueue<Runnable>(capacity);
			}

			private void offer(Runnable command) {
				// put the item on the queue uninterruptibly.
				boolean interrupted = false;
				try {
					for (;;) {
						try {
							q.put(command);
							break;
						} catch (InterruptedException ie) {
							interrupted = true;
						}
					}
				} finally {
					if (interrupted) {
						Thread.currentThread().interrupt();
					}
				}
				// if the segment is active we don't need to schedule.
				if (active.get()) {
					return;
				}
				// now we need to do a cas to make sure
				if (active.compareAndSet(false, true)) {
					executorService.execute(ExecutionSegment.this);
				}
			}

			public void run() {
				activeCount.incrementAndGet();
				try {
					for (;;) {
						Runnable command = q.poll();
						if (command == null) {
							active.set(false);
							// Here is some complex logic coming: it can happen
							// that work was placed by another thread
							// after the q.poll. If we don't take care of this
							// situation, it could happen that work remains
							// unscheduled (and we don't want that).
							boolean finished;
							if (q.peek() == null) {
								// we are lucky, there was no new work scheduled
								// after the ExecutionSegment was made inactive.
								// It will now be the responsibility of another
								// thread to schedule execution and we can
								// finish.
								finished = true;
							} else {
								// we were unlucky; we decided to deactivate
								// this ExecutionSegment, but new work
								// was offered. If we can get this
								// ExecutionSegment active again, we keep
								// running, otherwise
								// it will be the responsibility of another
								// thread to schedule execution and we can
								// finish.
								// it can be that we are going to continue
								// executing, even though there is no work
								// anymore.
								// (some other thread could have processed the
								// work that we found with the peek). But that
								// is not a problem since the g.poll returns
								// null and this thread has the chance to
								// complete
								// anyway.
								finished = !active.compareAndSet(false, true);
							}
							if (finished) {
								break;
							}
						} else {
							try {
								command.run();
							} catch (Throwable e) {
								logger.debug(e.getMessage());
							}
						}
					}
				} finally {
					activeCount.decrementAndGet();
				}
			}

			private void shutdown() {
				Runnable r = q.poll();
				while (r != null) {
					r = q.poll();
				}
			}

			private int getPoolSize() {
				return active.get() ? 1 : 0;
			}
		}
	}
}