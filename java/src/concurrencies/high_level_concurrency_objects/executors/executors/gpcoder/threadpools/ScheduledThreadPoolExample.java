package executors.gpcoder.threadpools;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <h1>Sử dụng newScheduledThreadPool()</h1>
 * 
 * Trong chương trình trên, tôi đã tạo ra ThreadPool có kích thước cố định là 2.
 * Sau đó, tôi đã tạo 5 task (công việc) vào ThreadPool, vì kích thước
 * ThreadPool là 2, nên nó sẽ bắt đầu thực thi chương trình trên vói 5 task và
 * các task khác sẽ ở trạng thái đợi (waiting), ngay khi một task hoàn thành,
 * một task khác từ hàng đợi sẽ được chọn và thực thi. <br/>
 * Các thread này được lên kế hoạch thực thi bằng phương thức
 * scheduleWithFixedDelay(), sau khi thực thi lần đầu tiên, nó sẽ được gọi để
 * thực thi lại sau khoảng thời gian delay xác định. Phương thức này gồm 4 tham
 * số như sau: <br/>
 * <ul>
 * <li>Runnable: tác vụ sẽ được thực thi.</li>
 * <li>initialDelay: thời gian trì hoãn trước khi thực thi lần đầu tiên.</li>
 * <li>delay: thời gian trì hoãn kể từ khi thực thi lần trước đó kết thúc.</li>
 * <li>TimeUnit: đơn vị thời gian của tham số initialDelay và delay.</li>
 * </ul>
 * <br/>
 * Trong chương trình trên, tôi còn sử dụng một phương thức awaitTermination():
 * phương thức này xác định thời gian chờ tối đa để các task hoàn thành thực
 * thi.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ScheduledThreadPoolExample {

	public static final int NUM_OF_THREAD = 2;
	public static final int INITIAL_DELAY = 1; // second
	public static final int DELAY = 3; // second

	public static void main(String[] args) throws InterruptedException {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(NUM_OF_THREAD);

		for (int i = 1; i <= 5; i++) {
			Runnable worker = new WorkerThread("" + i);
			executor.scheduleWithFixedDelay(worker, INITIAL_DELAY, DELAY, TimeUnit.SECONDS);
		}

		// waits for termination for 10 seconds only
		executor.awaitTermination(10, TimeUnit.SECONDS);

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