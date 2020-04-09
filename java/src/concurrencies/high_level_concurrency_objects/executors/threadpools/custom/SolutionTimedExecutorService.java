package threadpools.custom;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The problem seems to be that your timeout thread interrupts the worker thread
 * whether it finished its task on time or not. As worker threads in
 * ThreadPoolExecutor are reused, the timeout thread may be interrupting one of
 * the next tasks running on the worker thread. <br/>
 * Edit <br/>
 * The solution proved to be quite a bit more complex than I had anticipated.
 * <br/>
 * However I think I've got it. <br/>
 * First I introduced a PriorityBlockingQueue, so I can have only one thread
 * that will cancel all timed out tasks, instead of having an extra thread per
 * task. <br/>
 * I made an internal class TimedOutRunnable that holds the timeout time (vs.
 * System.nanoTime()) so I can order on the timeout, and I am independent of the
 * system clock. <br/>
 * I use a one off Sempahore so the cancel thread gets started only once, and
 * only when at least one task is executed. <br/>
 * In the beforeExecute, I queue a TimedOutRunnable instance, and in the
 * afterExecute, I find it, and mark it finished (if still present) <br/>
 * On shutDown I need to interrupt the cancelThread. <br/>
 * The code on the cancel thread is fairly crazy, especially because of how it
 * needs to deal with being interrupted. Before shutDown it needs to get the
 * head of the queue and block if it's empty. Then after shutDown (marked by
 * being interrupted) it needs to continue until the queue is empty, so no more
 * blocking take() but poll() instead. <br/>
 * On interruption we also need to requeue the TimedOutRunnable we were sleeping
 * for, as we'll need to still handle a possible cancel for it. <br/>
 * Disclaimer : tested only superficially
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class SolutionTimedExecutorService extends ThreadPoolExecutor {
	long timeout;

	public SolutionTimedExecutorService(int numThreads, long timeout, TimeUnit unit) {
		super(numThreads, numThreads, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1000));
		this.timeout = unit.toNanos(timeout);
	}

	@Override
	public void execute(Runnable command) {
		super.execute(command);
	}

	private static class TimedOutRunnable implements Comparable<TimedOutRunnable> {

		private final Thread thread;
		private final Runnable runnable;
		private final long timeoutAt;
		private volatile boolean finished = false;

		TimedOutRunnable(Thread thread, Runnable runnable, long timeoutAt) {
			this.thread = thread;
			this.runnable = runnable;
			this.timeoutAt = timeoutAt;
		}

		@Override
		public int compareTo(TimedOutRunnable o) {
			return Long.compare(timeoutAt, o.timeoutAt);
		}

		void cancel() {
			if (!finished) {
				thread.interrupt();
			}
		}

		void markFinished() {
			finished = true;
		}
	}

	private final PriorityBlockingQueue<TimedOutRunnable> timeoutQueue = new PriorityBlockingQueue<>();
	private final Set<TimedOutRunnable> runningTasks = Collections.newSetFromMap(new ConcurrentHashMap<>());

	private final Thread cancelThread = new Thread(() -> {
		while (!Thread.currentThread().isInterrupted()) {
			TimedOutRunnable timedOutRunnable = null;
			try {
				timedOutRunnable = timeoutQueue.take();
				long timeoutAt = timedOutRunnable.timeoutAt;
				long now = System.nanoTime();
				TimeUnit.NANOSECONDS.sleep(timeoutAt - now);
				timedOutRunnable.cancel();
				runningTasks.remove(timedOutRunnable);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				timeoutQueue.add(timedOutRunnable); // requeue so we finish the
													// timeout
			}
		}
		while (!timeoutQueue.isEmpty()) {
			TimedOutRunnable timedOutRunnable = null;
			try {
				timedOutRunnable = timeoutQueue.poll();
				if (timedOutRunnable != null) {
					long timeoutAt = timedOutRunnable.timeoutAt;
					long now = System.nanoTime();
					TimeUnit.NANOSECONDS.sleep(timeoutAt - now);
					timedOutRunnable.cancel();
					runningTasks.remove(timedOutRunnable);
				}
			} catch (InterruptedException e) {
				// thread ends once we're done
				timeoutQueue.add(timedOutRunnable); // requeue so we finish the
													// timeout
			}
		}
	});

	private final Semaphore startPermission = new Semaphore(1);

	@Override
	protected void beforeExecute(Thread thread, Runnable runnable) {
		if (startPermission.tryAcquire()) {
			cancelThread.setName("CancelThread");
			cancelThread.start();
		}
		TimedOutRunnable timedOutRunnable = new TimedOutRunnable(thread, runnable, System.nanoTime() + timeout);
		runningTasks.add(timedOutRunnable);
		timeoutQueue.add(timedOutRunnable);
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		Optional<TimedOutRunnable> found = runningTasks.stream()
				.filter(timedOutRunnable -> timedOutRunnable.runnable == r).findAny();
		if (found.isPresent()) {
			TimedOutRunnable timedOutRunnable = found.get();
			timedOutRunnable.markFinished();
			runningTasks.remove(timedOutRunnable);
		}
	}

	@Override
	public void shutdown() {
		if (!isShutdown()) {
			execute(cancelThread::interrupt);
		}
		super.shutdown();
	}

	@Override
	public List<Runnable> shutdownNow() {
		if (!isShutdown()) {
			execute(cancelThread::interrupt);
		}
		return super.shutdownNow();
	}

	public static void main(String[] args) {

		int numThreads = 2, timeout = 7;
		ThreadPoolExecutor timedExecutor = new TimedExecutorService2(numThreads, timeout, TimeUnit.SECONDS);

		for (int i = 0; i < 6; i++) {
			timedExecutor.execute(new Business(i));
		}
		timedExecutor.shutdown();

	}

}