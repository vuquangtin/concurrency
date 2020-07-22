package asynchronous.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import concurrencies.utilities.Log4jUtils;

public class CompletableFutureTest {
	public static Logger logger = Logger.getLogger(CompletableFutureTest.class);
	static int value = 75;

	static {
		value = 50;
		System.out.println(value);
	}
	{
		value = 25;
		System.out.println("normal block of code");
	}

	public static void main(String[] args) throws ExecutionException,
			InterruptedException {
		logger = Log4jUtils.initLog4j();
		System.out.println(value);

		logger.info(value + "");
		logger.debug(value + "");
		logger.error(value + "");
		logger.info(value + "");
		ExecutorService executorService = Executors.newCachedThreadPool();
		CompletableFuture<String> c1 = CompletableFuture.supplyAsync(() -> {
			logger.debug("call abc");
			return "abc";
		}, executorService);
		CompletableFuture<String> completableFuture = CompletableFuture
				.supplyAsync(() -> getData(10)).thenApply(
						data -> applyData(data));
		logger.debug(completableFuture.get());
		executorService.shutdown();
		logger.debug("shutdown");
	}

	private static String applyData(String data) {
		System.out.println(Thread.currentThread().getName());
		return data + "hello";
	}

	private static String getData(int i) {
		System.out.println(Thread.currentThread().getName());
		return "date" + i;
	}
}