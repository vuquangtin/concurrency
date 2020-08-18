package asynchronous.async.byexample;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */

public class DownloadMultiplePartFileWithExecutor {

	final static int TIME_TO_DOWNLOAD = 5000;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("Start processor");
		long startTime = System.currentTimeMillis();

		List<String> downloadParts = Arrays.asList("Tập 1", "Tập 2", "Tập 3", "Tập 4", "Tập 5", "Tập 6", "Tập 7");

		// Tạo 1 list các Future
		List<CompletableFuture<String>> partContentFutures = downloadParts.stream()
				.map(webPageLink -> download(webPageLink, TIME_TO_DOWNLOAD)).collect(Collectors.toList());

		// Tạo riêng 1 Future
		CompletableFuture<String> partSpecial = CompletableFuture.supplyAsync(() -> {
			System.out.println("Downloading: Tập đặc biệt");
			try {
				Thread.sleep(7000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Done: Tập đặc biệt");
			return "Tập đặc biệt";
		});

		partContentFutures.add(partSpecial);

		CompletableFuture<Void> allFile = CompletableFuture
				.allOf(partContentFutures.toArray(new CompletableFuture[partContentFutures.size()]));

		CompletableFuture<String> mergeFile = allFile.thenApply(parts -> {// get
																			// all
																			// file
			return partContentFutures.stream().map(pageContentFuture -> pageContentFuture.join())
					.collect(Collectors.joining("+"));
		});
		System.out.println("Kết quả: " + mergeFile.get());
		System.out.println("Hoàn thành process trong :" + (System.currentTimeMillis() - startTime));
	}

	public static CompletableFuture<String> download(String namePart, int time) {
		final AtomicLong count = new AtomicLong(0);
		final ThreadPoolExecutor pool = new ThreadPoolExecutor(0, 10, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						Thread t = new Thread(r);
						String threadName = "thread-" + count.getAndIncrement();
						t.setName(threadName);
						return t;
					}
				});

		return CompletableFuture.supplyAsync(() -> {
			System.out.println("Downloading: " + namePart);
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Done: " + namePart);
			return namePart;
		}, pool);
	}
}