package executors.schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class TaskSchedulerTest {

	@Test
	public void testScheduleCallable() throws Exception {
		ScheduledExecutorService scheduledExecutorService = Executors
				.newScheduledThreadPool(1);
		ScheduledFuture scheduledFuture = scheduledExecutorService.schedule(
				() -> "world", 1, SECONDS);

		System.out
				.println("Schedule callable - Hello " + scheduledFuture.get());
		scheduledExecutorService.shutdown();
	}

	@Test
	public void testScheduleRunnable() throws Exception {
		ScheduledExecutorService scheduledExecutorService = Executors
				.newScheduledThreadPool(1);
		ScheduledFuture scheduledFuture = scheduledExecutorService
				.schedule((Runnable) () -> System.out
						.println("Schedule runnable - Hello world"), 1, SECONDS);

		scheduledFuture.get();

		scheduledExecutorService.shutdown();
	}

	@Test
	public void testScheduleAtFixedRate() throws Exception {
		ScheduledExecutorService scheduledExecutorService = Executors
				.newScheduledThreadPool(1);
		List<ScheduledFuture> scheduledFutures = new ArrayList<>();
		scheduledFutures.add(scheduledExecutorService.scheduleAtFixedRate(
				new PrinterTask(1), 1, 1, SECONDS));

		waitAndShutdown(scheduledExecutorService);
	}

	private void waitAndShutdown(
			ScheduledExecutorService scheduledExecutorService)
			throws InterruptedException {
		Thread.sleep(10 * 1000);
		scheduledExecutorService.shutdown();
	}

	@Test
	public void testScheduleWithFixedDelay() throws Exception {
		ScheduledExecutorService scheduledExecutorService = Executors
				.newScheduledThreadPool(1);
		List<ScheduledFuture> scheduledFutures = new ArrayList<>();
		scheduledFutures.add(scheduledExecutorService.scheduleWithFixedDelay(
				new PrinterTask(1), 1, 1, SECONDS));

		waitAndShutdown(scheduledExecutorService);
	}

	@Test
	public void testScheduleWithFixedDelayMultiTaskOneThread() throws Exception {
		scheduleWithFixedDelay(3, 1);
	}

	@Test
	public void testScheduleWithFixedDelayMultiTaskMultiThread()
			throws Exception {
		scheduleWithFixedDelay(3, 3);
	}

	private void scheduleWithFixedDelay(final int nbrTask, final int nbrThread)
			throws InterruptedException {
		ScheduledExecutorService scheduledExecutorService = Executors
				.newScheduledThreadPool(nbrThread);
		List<ScheduledFuture> scheduledFutures = new ArrayList<>();
		for (int i = 1; i <= nbrTask; i++) {
			scheduledFutures.add(scheduledExecutorService
					.scheduleWithFixedDelay(new PrinterTask(i), 1, 1, SECONDS));
		}

		waitAndShutdown(scheduledExecutorService);
	}

	@Test
	public void testAutoCancelPrinerTask() throws Exception {
		ScheduledExecutorService scheduledExecutorService = Executors
				.newScheduledThreadPool(3);
		List<ScheduledFuture> scheduledFutures = new ArrayList<>();
		for (int i = 1; i <= 3; i++) {
			scheduledFutures.add(new AutoCancelPrinterTask(i, 5)
					.scheduleWithFixedDelay(scheduledExecutorService, 1, 1,
							SECONDS));
		}

		waitAndShutdown(scheduledExecutorService);
	}
}

class PrinterTask implements Runnable {
	private final int id;
	private int count = 0;

	public PrinterTask(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		System.out.println("PrinterTask " + id + " - Hello world " + count++);
	}
}

class AutoCancelPrinterTask implements Runnable {
	private final int id;
	private final int max;
	private int count = 0;
	private ScheduledFuture self;

	public AutoCancelPrinterTask(int id, int max) {
		this.id = id;
		this.max = max;
	}

	@Override
	public void run() {
		System.out.println("PrinterTask " + id + " - Hello world " + count++);
		if (count == max) {
			self.cancel(false);
		}
	}

	public ScheduledFuture scheduleWithFixedDelay(
			ScheduledExecutorService scheduledExecutorService,
			long initialDelay, long delay, TimeUnit unit) {
		self = scheduledExecutorService.scheduleWithFixedDelay(this,
				initialDelay, delay, unit);
		return self;
	}
}