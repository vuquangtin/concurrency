package basic.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskExample {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
			@Override
			public String call() {
				try {
					// simulating long running task
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("task finished");
				return "The result";
			}
		});

		new Thread(futureTask).start();
		System.out.println("Thread started");
		String s = futureTask.get();
		System.out.println(s);
	}
}