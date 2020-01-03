package cookbook.chapter4.threadexecutors.recipe10_2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Completion {

	private static final int WAIT_TIME = 200;
	private static final int NUM_OF_THREADS = 3;

	private final List<String> printRequests = Arrays.asList("1", "2", "3",
			"4", "5", "6", "7", "8", "9", "10", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "10", "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"10", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "1", "2",
			"3", "4", "5", "6", "7", "8", "9", "10", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "10", "1", "2", "3", "4", "5", "6", "7", "8",
			"9", "10", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "1",
			"2", "3", "4", "5", "6", "7", "8", "9", "10", "1", "2", "3", "4",
			"5", "6", "7", "8", "9", "10", "1", "2", "3", "4", "5", "6", "7",
			"8", "9", "10", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
			"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "1", "2", "3",
			"4", "5", "6", "7", "8", "9", "10");

	public void useNormalLoop() {
		for (String image : printRequests) {
			try {
				Thread.sleep(WAIT_TIME);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
			System.out.print(image);
		}
	}

	public void normalExecutor() {
		ExecutorService svc = Executors.newFixedThreadPool(NUM_OF_THREADS);
		try {
			Set<Future<String>> printTaskFutures = new HashSet<Future<String>>();
			for (final String req : printRequests) {
				printTaskFutures.add(svc.submit(new PrintTask(req)));
			}
			for (Future<String> future : printTaskFutures) {
				System.out.print(future.get());
			}
		} catch (Exception e) {
			Thread.currentThread().interrupt();
		} finally {
			if (svc != null) {
				svc.shutdownNow();
			}
		}
	}

	public void completionService() {
		ExecutorService svc = Executors.newFixedThreadPool(NUM_OF_THREADS);
		CompletionService<String> csvc = new ExecutorCompletionService<String>(
				svc);
		for (final String req : printRequests) {
			csvc.submit(new PrintTask(req));
		}
		try {
			for (int i = 0, n = printRequests.size(); i < n; i++) {
				Future<String> fs = csvc.take();
				System.out.print(fs.get());
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();

		} catch (ExecutionException ee) {
			ee.printStackTrace();
		} finally {
			if (svc != null) {
				svc.shutdownNow();
			}
		}

	}

	private class PrintTask implements Callable<String> {
		private final String printString;

		private PrintTask(String print) {
			this.printString = print;
		}

		public String call() {
			try {
				Thread.sleep(WAIT_TIME);
			} catch (InterruptedException ie) {
				ie.printStackTrace();

			}
			return printString;
		}
	}

}