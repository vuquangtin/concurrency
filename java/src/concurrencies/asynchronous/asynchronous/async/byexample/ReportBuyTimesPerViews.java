package asynchronous.async.byexample;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */

public class ReportBuyTimesPerViews {

	final static int TIME_TO_GET_BUY_TIMES = 5000;
	final static int TIME_TO_GET_VIEWS = 5000;
	final static int TIME_TO_COMPUTE = 5000;
	final static int VIEWS = 1000;
	final static int BUY_TIMES = 69;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("Start processor");
		long startTime = System.currentTimeMillis();
		CompletableFuture<Integer> getBuyTimes = CompletableFuture.supplyAsync(() -> {
			execute("getBuyTimes", TIME_TO_GET_BUY_TIMES);
			return BUY_TIMES;
		});

		CompletableFuture<Integer> getViews = CompletableFuture.supplyAsync(() -> {
			execute("getViews", TIME_TO_GET_VIEWS);
			return VIEWS;
		});

		CompletableFuture<Double> computeBuyTimesPerViews = getBuyTimes.thenCombine(getViews, (buyTimes, views) -> {
			execute("computeBuyTimesPerViews", TIME_TO_GET_VIEWS);
			return (double) buyTimes / views;
		});
		Double result = computeBuyTimesPerViews.get();
		System.out.println("Tỉ lệ : " + result);
		System.out.println("Hoàn thành process trong :" + (System.currentTimeMillis() - startTime) + "ms");
	}

	static void execute(String name, int time) {
		try {
			Thread.sleep(time);
			System.out.println("done task : " + name);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}
}