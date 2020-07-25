package threadpools.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <h1>Extending Threadpool Executor with BeforeExecute method not working
 * properly for threads more than the pool size</h1> I am creating a multi
 * threaded programming with ThreadPoolExecutor. I want to interrupt a thread if
 * it is taking too much time to complete the task. So i am overriding
 * beforeExecute method. It works good for the specified number of threads.
 * Example if i am defining number of threads as 5, then five threads are
 * working fine. But for the rest of the tasks, it creates new threads but all
 * are failing saying sleep interrupted. <br/>
 * Request your help to fix this issue. <br/>
 * Here is the working code. If there is any other best approach, please post.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class TimedExecutorService2 extends ThreadPoolExecutor {
	long timeout;

	public TimedExecutorService2(int numThreads, long timeout, TimeUnit unit) {
		super(numThreads, numThreads, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1000));
		this.timeout = unit.toMillis(timeout);
	}

	@Override
	protected void beforeExecute(Thread thread, Runnable runnable) {
		System.out.println("beforeExecute:" + thread.getName());
		Thread interruptionThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// Wait until timeout and interrupt this thread
					System.out.println("timeout:" + timeout);
					Thread.sleep(timeout);
					thread.interrupt();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		interruptionThread.start();
	}

	public static void main(String[] args) {

		int numThreads = 4, timeout = 5;
		ThreadPoolExecutor timedExecutor = new TimedExecutorService2(numThreads, timeout, TimeUnit.SECONDS);
		for (int i = 0; i < 6; i++) {
			timedExecutor.execute(new Business(i));
		}
		timedExecutor.shutdown();
	}

	public static void main1(String args[]) throws Exception {
		final TimeoutExecutorService executor = new TimeoutExecutorService(6);
		final List<Future<?>> futures = new ArrayList<>();
		for (int i = 0; i < 16; i++) {
			futures.add(executor.submit(new Business(i), 4000));
		}

		for (final Future<?> future : futures) {
			future.get();
		}

		executor.workExecutor.shutdownNow();
		executor.timeoutExecutor.shutdownNow();
	}

}

class TimeoutExecutorService {
	final ExecutorService workExecutor;
	final ScheduledExecutorService timeoutExecutor = Executors.newSingleThreadScheduledExecutor();

	public TimeoutExecutorService(final int numThreads) {
		this.workExecutor = Executors.newFixedThreadPool(numThreads);
	}

	final Future<?> submit(final Runnable runnable, final long timeoutMillis) {
		// use an atomic reference to allow code inside a runnable to refer
		// to its own future
		final AtomicReference<Future<?>> futureReference = new AtomicReference<>();

		futureReference.set(workExecutor.submit(() -> {
			// schedule a different thread to cancel this one after a
			// certain amount of time
			final Future<?> cancelFuture = timeoutExecutor.schedule(() -> {
				futureReference.get().cancel(true);
			}, timeoutMillis, TimeUnit.MILLISECONDS);
			try {
				// do the thing
				runnable.run();
			} finally {
				// if the runnable completes before the cancelFuture
				// interrupts this thread,
				// prevent the cancelFuture from running
				cancelFuture.cancel(true);
			}
		}));

		return futureReference.get();
	}
}