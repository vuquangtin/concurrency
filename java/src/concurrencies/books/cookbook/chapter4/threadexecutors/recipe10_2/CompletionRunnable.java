package cookbook.chapter4.threadexecutors.recipe10_2;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class MyCallable implements Callable<Integer> {
	Runnable r;
	boolean statusOK;

	MyCallable(Runnable r) {
		this.r = r;
	}

	public Integer call() {
		System.out.println("call ");
		r.run();
		// check status of runnable
		try {
			if (((MyRunnable) r).getStatus()) {
				System.out.println("return 1 ");
				return 1;
			} else {
				System.out.println("return -1 ");
				return -1;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}
}

class MyRunnable implements Runnable {
	public volatile static boolean status;

	@Override
	public void run() {
		System.out.println("run ");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		status = true;

	}

	public boolean getStatus() {
		return status;
	}
}

public class CompletionRunnable {
	private static final int NUM_OF_THREADS = 1;

	public static void main(String[] args) throws ExecutionException,
			TimeoutException, InterruptedException {
		ExecutorService svc = Executors.newFixedThreadPool(NUM_OF_THREADS);
		CompletionService<Integer> executorCompletionService = new ExecutorCompletionService<Integer>(
				svc);
		MyRunnable run = new MyRunnable();
		Future<Integer> future = executorCompletionService
				.submit(new MyCallable(run));

		System.out.println(executorCompletionService.take().get());
		// if (future.isDone()) {
		System.out.println(future.get());
		// }
		svc.shutdown();
	}

}
