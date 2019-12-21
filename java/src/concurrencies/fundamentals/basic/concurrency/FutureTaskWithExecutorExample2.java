package basic.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureTaskWithExecutorExample2 {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				// simulating long running task
				Thread.sleep(1000);
				System.out.println("returning");
				return "The result";
			}
		});

		ExecutorService es = Executors.newSingleThreadExecutor();
		es.execute(futureTask);
		String s = futureTask.get();
		System.out.println(s);
		es.shutdown();
	}
}