package executors.completions.executorcompletionservices;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class CompletionExamples {

	static class Task<T> implements Callable<T> {

		private T i;

		private Task(T i) {
			this.i = i;
		}

		@Override
		public T call() throws Exception {
			ThreadUtils.randomSleep(200);
			return i;
		}

	}

	public static List<Future<Integer>> submit(ExecutorService executor,
			int nTasks) {
		return IntStream.range(0, nTasks)
				.mapToObj(i -> executor.submit(new Task<>(i)))
				.collect(toList());
	}

	public static List<Future<Integer>> submit(
			ExecutorCompletionService<Integer> executor, int nTasks) {
		return IntStream.range(0, nTasks)
				.mapToObj(i -> executor.submit(new Task<>(i)))
				.collect(toList());
	}

}