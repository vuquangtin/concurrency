package learn.threads.vn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class SingleThreadPoolExample {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		checkTime(executor);
	}

	static void checkTime(ExecutorService executor) {
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			executor.execute(new RequestHandler("request-" + i));
		}
		executor.shutdown(); // Không cho threadpool nhận thêm nhiệm vụ nào nữa

		while (!executor.isTerminated()) {
			// Chờ xử lý hết các request còn chờ trong Queue …
		}
		long end = System.currentTimeMillis();
		long totalTime = (end - begin) / 1000;
		System.out.println("Total time " + totalTime);
	}
}