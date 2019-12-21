package cookbook.chapter4.threadexecutors.recipe10_2;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompletionServiceAndExecutorService {
	public static void main(String[] args) {
		ExecutorService taskExecutor = Executors.newCachedThreadPool();
		// CompletionService<Long> taskCompletionService =
		// new ExecutorCompletionService<Long>(taskExecutor);
		Callable<Long> callable = new Callable<Long>() {
			@Override
			public Long call() throws Exception {
				return 1L;
			}
		};

		Future<Long> future = // taskCompletionService.submit(callable);
		taskExecutor.submit(callable);
		// thay vi while thi taskCompletionService.take() se doi khi co task
		// hoan thanh
		while (!future.isDone()) {
			// Do some work...
			System.out.println("Working on something...");
		}
		try {
			System.out.println(future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}
