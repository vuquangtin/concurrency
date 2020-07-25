package executors.gpcoder.threadpools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <h1>sử dụng newFixedThreadPool()</h1> Trong chương trình trên, tôi đã tạo ra
 * ThreadPool có kích thước cố định là 5. Sau đó, tôi đã tạo 10 task (công việc)
 * vào ThreadPool, vì kích thước ThreadPool là 5, nên nó sẽ bắt đầu thực thi
 * chương trình trên vói 5 task và các task khác sẽ ở trạng thái đợi (waiting),
 * ngay khi một task hoàn thành, một task khác từ hàng đợi sẽ được chọn và thực
 * thi.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class FixedThreadPoolExample {

	public static final int NUM_OF_THREAD = 5;

	public static void main(String[] args) {

		ExecutorService executor = Executors.newFixedThreadPool(NUM_OF_THREAD);

		for (int i = 1; i <= 10; i++) {
			Runnable worker = new WorkerThread("" + i);
			executor.execute(worker);
		}

		/*
		 * Initiates an orderly shutdown in which previously submitted tasks are
		 * executed, but no new tasks will be accepted. Invocation has no
		 * additional effect if already shut down. This method does not wait for
		 * previously submitted tasks to complete execution. Use
		 * awaitTermination to do that.
		 */
		executor.shutdown();

		// Wait until all threads are finish
		while (!executor.isTerminated()) {
			// Running ...
		}

		System.out.println("Finished all threads");
	}
}