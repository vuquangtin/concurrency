package executors.completions.executorcompletionservices;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class ExecutorCompletionServiceExample {

	final List<String> topSites = Arrays.asList("www.google.com",
			"www.youtube.com", "www.yahoo.com", "www.msn.com",
			"www.wikipedia.org", "www.baidu.com", "www.microsoft.com",
			"www.qq.com", "www.bing.com", "www.ask.com", "www.adobe.com",
			"www.taobao.com", "www.youku.com", "www.soso.com",
			"www.wordpress.com", "www.sohu.com", "www.windows.com",
			"www.163.com", "www.tudou.com", "www.amazon.com");

	final ExecutorService pool = Executors.newFixedThreadPool(5);
	final ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(
			pool);

	public void callAllSites() {
		for (final String site : topSites) {
			completionService.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					System.out.println("Called:" + site);
					return (new URL("http://" + site)).toString();
				}
			});
		}
	}

	public void retrieveUrls() throws InterruptedException {
		for (int i = 0; i < topSites.size(); ++i) {
			final Future<String> future = completionService.take();
			try {
				final String content = future.get();
				System.out.println("Fetched:" + content);
				// ...process contents
			} catch (ExecutionException e) {
				System.out.println("Error while downloading" + e.getCause());
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ExecutorCompletionServiceExample service = new ExecutorCompletionServiceExample();
		service.callAllSites();
		service.retrieveUrls();
		service.pool.shutdown();
	}
}