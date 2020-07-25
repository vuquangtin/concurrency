package custom.threadfactory;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Main {
	public static void main(String[] args) {
		ThreadFactory customThreadfactory = new ThreadFactoryBuilder().setNamePrefix("ImageDownloadersPool-Thread")
				.setDaemon(false).setPriority(Thread.MAX_PRIORITY)
				.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
					@Override
					public void uncaughtException(Thread t, Throwable e) {
						System.err
								.println(String.format("Thread %s threw exception - %s", t.getName(), e.getMessage()));

					}
				}).build();

		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(3, customThreadfactory);
		newFixedThreadPool.submit(() -> {
			System.out.println("hello");
		});

		ThreadFactory newCustomThreadfactory = new ThreadFactoryBuilder().setNamePrefix("ImageDownloadersPool-Thread")
				.build();
		ExecutorService newCustomFixedThreadPool = Executors.newFixedThreadPool(3, newCustomThreadfactory);
		newCustomFixedThreadPool.submit(() -> {
			System.out.println("hello");
		});
		int x = 0;
		System.out.println(x);
		newFixedThreadPool.shutdown();
		newCustomFixedThreadPool.shutdown();
	}
}
