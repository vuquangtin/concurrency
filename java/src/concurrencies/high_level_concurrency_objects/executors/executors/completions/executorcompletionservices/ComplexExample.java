package executors.completions.executorcompletionservices;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class ComplexExample {

	public static void main(String[] args) throws ExecutionException,
			InterruptedException {
		final ExecutorService service = Executors.newSingleThreadExecutor();

		List<Callable<Integer>> tasks = IntStream.range(0, 5).boxed()
				.map(MyTask::new).collect(Collectors.toList());

		final ExecutorCompletionService<Integer> completionService = new ExecutorCompletionService<>(
				service);

		tasks.forEach(completionService::submit);

		TimeUnit.SECONDS.sleep(5);

		service.shutdownNow();

		tasks.stream().filter(callable -> !((MyTask) callable).isSuccess())
				.forEach(System.out::println);

	}

	private static class MyTask implements Callable<Integer> {

		private final Integer value;

		private boolean success = false;

		MyTask(Integer value) {
			this.value = value;
		}

		@Override
		public Integer call() throws Exception {

			System.out.printf("The Task [%d] will be executed.\n", value);
			TimeUnit.SECONDS.sleep(value * 5 + 10);
			System.out.printf("The Task [%d] execute done.\n", value);
			success = true;
			return value;
		}

		@Override
		public String toString() {
			return "value: " + value;
		}

		public boolean isSuccess() {
			return success;
		}

	}

	// 返回值有问题
	private static void trap() throws InterruptedException {
		final ExecutorService service = Executors.newSingleThreadExecutor();

		List<Runnable> tasks = IntStream.range(0, 5).boxed()
				.map(ComplexExample::toTask).collect(Collectors.toList());

		final ExecutorCompletionService<Object> completionService = new ExecutorCompletionService<>(
				service);

		tasks.forEach(r -> completionService.submit(Executors.callable(r)));

		TimeUnit.SECONDS.sleep(5);

		final List<Runnable> runnables = service.shutdownNow();

		System.out.println(runnables.size());
		System.out.println(runnables);
	}

	private static void silly() throws ExecutionException, InterruptedException {
		final ExecutorService service = Executors.newFixedThreadPool(5);

		final List<Runnable> tasks = IntStream.range(0, 5).boxed()
				.map(ComplexExample::toTask).collect(Collectors.toList());
		List<Future<?>> futureList = new ArrayList<>();
		tasks.forEach(r -> futureList.add(service.submit(r)));
		/**
		 * 0最快也要等
		 */
		futureList.get(4).get();
		System.out.println("=======4=======");
		futureList.get(3).get();
		System.out.println("=======3=======");
		futureList.get(2).get();
		System.out.println("=======2=======");
		futureList.get(1).get();
		System.out.println("=======1=======");
		futureList.get(0).get();
		System.out.println("=======0=======");
	}

	private static void normal() throws ExecutionException,
			InterruptedException {
		final ExecutorService service = Executors.newFixedThreadPool(5);
		final List<Runnable> tasks = IntStream.range(0, 5).boxed()
				.map(ComplexExample::toTask).collect(Collectors.toList());
		final ExecutorCompletionService<Object> completionService = new ExecutorCompletionService<>(
				service);
		tasks.forEach(r -> completionService.submit(Executors.callable(r)));
		Future<?> future;
		while ((future = completionService.take()) != null) {
			System.out.println(future.get());
		}
	}

	private static Runnable toTask(int i) {
		return () -> {
			try {
				System.out.printf("The Task [%d] will be executed.\n", i);
				TimeUnit.SECONDS.sleep(i * 5 + 10);
				System.out.printf("The Task [%d] execute done.\n", i);
			} catch (InterruptedException e) {
				System.out.printf("The Task [%d] be interrupted.\n", i);
			}
		};
	}
}