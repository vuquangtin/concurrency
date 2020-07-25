package executors.gpcoder.threadpools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <h1>sử dụng newSingleThreadExecutor()</h1> Trong chương trình trên, tôi đã
 * tạo ra ThreadPool sử dụng phương thức newSingleThreadExecutor() vì vậy kích
 * thước của ThreadPool là 1, nên nó sẽ bắt đầu thực thi chương trình trên với 1
 * task và các task khác sẽ ở trạng thái đợi (waiting), ngay khi một task hoàn
 * thành, một task khác từ hàng đợi sẽ được chọn và thực thi. thu
 * tu:1-->2-->3-->4...->n
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class SingleThreadExecutorExample {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newSingleThreadExecutor();

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
