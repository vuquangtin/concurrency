package executors.completions;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorCompletionServiceTest2<T> {
	public static void main(String[] args) {
		final ExecutorService pool = Executors.newFixedThreadPool(2);
		final CompletionService<String> service = new ExecutorCompletionService<String>(pool);
		final List<? extends Callable<String>> callables = Arrays.asList(new SleepingCallable("slow", 5000),
				new SleepingCallable("quick", 500));
		for (final Callable<String> callable : callables) {
			service.submit(callable);
		}
		pool.shutdown();
		try {
			while (!pool.isTerminated()) {
				final Future<String> future = service.take();
				System.out.println(future.get());
			}
		} catch (ExecutionException | InterruptedException ex) {
		}
	}

	static class SleepingCallable implements Callable<String> {

		final String name;
		final long period;

		SleepingCallable(final String name, final long period) {
			this.name = name;
			this.period = period;
		}

		public String call() {
			try {
				Thread.sleep(period);
			} catch (InterruptedException ex) {
			}
			return name;
		}
	}
}