package threadpools.futures;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class FutureDemo {

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();

		testNormal(3, 3, 5);
		testAsynchronous(3, 3, 5);

		System.out.printf("Elapsed time: %d seconds\n", (System.currentTimeMillis() - start) / 1000);
	}

	/***
	 * Chạy thông thường Tiếp theo, chúng ta thực hiện nhiều lần hàm đó theo
	 * cách bình thường (như chúng ta vẫn hay làm ) trong hàm testNormal: <br/>
	 * String r1 = doLongTask(s1);<br/>
	 * String r2 = doLongTask(s2);<br/>
	 * String r3 = doLongTask(s3);<br/>
	 * Thực tế, chúng ta có thể thực hiện 1 hàm nhiều lần hoặc thực hiện nhiều
	 * hàm khác nhau. <br/>
	 * Hãy thử thực hiện hàm to đó: <br/>
	 * testNormal(3, 3, 5);<br/>
	 * Tổng thời gian thực hiện sẽ là 3+3+5 = 11 (giây).
	 * 
	 * @param s1
	 * @param s2
	 * @param s3
	 */
	private static void testNormal(int s1, int s2, int s3) {
		String r1 = doLongTask(s1);
		String r2 = doLongTask(s2);
		String r3 = doLongTask(s3);

		System.out.println(r1);
		System.out.println(r2);
		System.out.println(r3);
	}

	private static void testAsynchronous(int s1, int s2, int s3) throws InterruptedException, ExecutionException {
		int poolSize = 3;
		ExecutorService executor = Executors.newFixedThreadPool(poolSize);

		Future<String> r1 = executor.submit(() -> {
			return doLongTask(s1);
		});
		Future<String> r2 = executor.submit(() -> {
			return doLongTask(s2);
		});
		Future<String> r3 = executor.submit(() -> {
			return doLongTask(s3);
		});

		System.out.println(r1.get());
		System.out.println(r2.get());
		System.out.println(r3.get());

		executor.shutdown();
	}

	private static String doLongTask(int second) {
		try {
			Thread.sleep(1000 * second);
			return "Finish after " + second + " second";
		} catch (InterruptedException ex) {
			return null;
		}
	}
}
