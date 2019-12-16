package asynchronous.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompletableFutureTest {
	static final Logger logger = LoggerFactory
			.getLogger(CompletableFutureTest.class);
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
		System.out.println(value);

		logger.info(value + "");
		logger.debug(value + "");
		logger.error(value + "");
		logger.info(value + "");
		ExecutorService executorService = Executors.newCachedThreadPool();
		CompletableFuture<String> c1 = CompletableFuture.supplyAsync(() -> {
			System.out.println("abc");
			return "abc";
		}, executorService);
		CompletableFuture<String> completableFuture = CompletableFuture
				.supplyAsync(() -> getData(10)).thenApply(
						data -> applyData(data));
		System.out.println(completableFuture.get());
		executorService.shutdown();
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