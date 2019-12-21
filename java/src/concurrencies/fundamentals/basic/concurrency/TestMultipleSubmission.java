package basic.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestMultipleSubmission implements Runnable {

	static int MAX = 66;

	public TestMultipleSubmission() {
	}

	@Override
	public void run() {
		double[][] dp = new double[11000][11000];
		for (int i = 0; i < dp.length; i++) {
			for (int j = 0; j < dp[i].length; j++) {
				dp[i][j] = i * j;
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("********* TestMultipleSubmission.java: Testing reused task profiling *********");
		ExecutorService e1 = Executors.newFixedThreadPool(1);

		for (int i = 0; i < MAX; i++) {
			e1.submit(new TestMultipleSubmission());
		}

		e1.shutdown();

		try {
			e1.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new AssertionError();
		}
	}
}