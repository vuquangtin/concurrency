package executors.completions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutionCompletionServiceMain {

	public static void main(String[] args) {
		MultiplyingTask multiplyingTask1 = new MultiplyingTask("Task 1", 10, 20, 2000l);
		MultiplyingTask multiplyingTask2 = new MultiplyingTask("Task 2", 30, 40, 4000l);
		MultiplyingTask multiplyingTask3 = new MultiplyingTask("Task 3", 40, 50, 3000l);
		MultiplyingTask multiplyingTask4 = new MultiplyingTask("Task 4", 50, 60, 1000l);

		ExecutorService executorService = Executors.newFixedThreadPool(4);
		CompletionService<Integer> executorCompletionService = new ExecutorCompletionService<>(executorService);
		List<Future<Integer>> futures = new ArrayList<Future<Integer>>();
		futures.add(executorCompletionService.submit(multiplyingTask1));
		futures.add(executorCompletionService.submit(multiplyingTask2));
		futures.add(executorCompletionService.submit(multiplyingTask3));
		futures.add(executorCompletionService.submit(multiplyingTask4));

		for (int i = 0; i < futures.size(); i++) {
			try {
				Integer result = executorCompletionService.take().get();
				System.out.println("Result: " + result);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		executorService.shutdown();
	}
}
