package executors.executor;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class Task implements Runnable {
	@Override
	public void run() {
		// task details
	}

	ExecutorService executor;

	public static void main(String[] args) {
		// ExecutorService executor = Executors.newFixedThreadPool(10);
	}

	public void execute1() {
		executor.submit(new Task());

		executor.submit(() -> {
			new Task();
		});

		try {
			executor.awaitTermination(20l, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void execute() {
		ScheduledExecutorService executorService = Executors
				.newSingleThreadScheduledExecutor();

		Future<String> future = executorService.schedule(() -> {
			// ...
				return "Hello world";
			}, 1, TimeUnit.SECONDS);
		System.out.println(future);
		ScheduledFuture<?> scheduledFuture = executorService.schedule(() -> {
			// ...
			}, 1, TimeUnit.SECONDS);

		executorService.shutdown();
		System.out.println(scheduledFuture);
		executorService.scheduleAtFixedRate(() -> {
			// ...
			}, 1, 10, TimeUnit.SECONDS);

		executorService.scheduleWithFixedDelay(() -> {
			// ...
			}, 1, 10, TimeUnit.SECONDS);
		List<Runnable> listRun = executorService.shutdownNow();
		for (Runnable runnable : listRun) {
			System.out.print(runnable.hashCode());
		}
	}

	public void invoke() {
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		Future<String> future = executorService.submit(() -> {
			// ...
				Thread.sleep(10000l);
				return "Hello world";
			});

		String str = null;
		if (future.isDone() && !future.isCancelled()) {
			try {
				str = future.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		System.out.println(str);
		try {
			future.get(10, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}
	}
}
