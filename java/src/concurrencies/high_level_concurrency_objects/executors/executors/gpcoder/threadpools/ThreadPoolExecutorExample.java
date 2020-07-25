package executors.gpcoder.threadpools;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Tạo chương trình minh họa thực thi tác vụ nhiều tác vụ WorkerThread, được
 * giám sát bởi MonitorThread với các tùy chỉnh RejectedExecutionHandler,
 * ThreadFactory. <br/>
 * Trong chương trình trên, tôi đã tạo ra ThreadPool sử dụng ThreadPoolExecutor
 * với: <br/>
 * <ul>
 * <li>Số lượng Thread ban đầu là 2 (corePoolSize ) và tối đa là 4
 * (maximumPoolSize) Thread trong ThreadPool.</li>
 * <li>Một workQueue để lưu giữ các task sẽ được thực thi.</li>
 * <li>Chương trình sẽ yêu cầu thực thi 10 task.</li>
 * <li>Do maximumPoolSize là 4 nên chỉ có 4 Thread được thực thi cùng lúc,
 * workQueue là 2 nên sẽ có 2 task được nằm trong hàng đợi (BlockingQueue), 4
 * Thread còn lại sẽ bị từ chối thực thi.</li>
 * <li>Sau khi Thread hoàn thành thực thi task một khoảng thời gian
 * keepAliveTime, nếu số lượng Thread trong ThreadPool lớn hơn corePoolSize và
 * workQueue không còn task yêu cầu thực thi thì Thread sẽ bị đóng lại (đóng các
 * Thread cho đến khi số lượng Thread = corePoolSize trong ThreadPool).</li>
 * <li>ThreadFactory sẽ tạo tên Thread với tiếp đầu ngữ là là
 * GPCoder-ThreadPool.</li>
 * <li>MonitorThread định kỳ khoảng 3 giây sẽ hiển thị thông tin của executor
 * hiện tại.</li>
 * </ul>
 * 
 * <h2>Một vài lưu ý về sử dụng ExecutorService</h2> Khi bạn đã thêm tất cả các
 * task cần thiết để thực thi vào executor bạn nên tắt nó bằng phương thức
 * shutdown(). Khi bạn gọi phương thức này có nghĩa ExcutorService sẽ từ chối
 * nhận thêm các task, và một khi tất cả các nhiệm vụ đã được thêm vào trước đó
 * đã hòan thành thì Executor sẽ được tắt. Có nghĩa tất cả các task được thêm
 * vào trước khi gọi shutdown() đều sẽ được thực thi, các task thêm sau sẽ bị từ
 * chối (rejected). <br/>
 * Nếu bạn muốn tắt ExecutorService ngay lập tức, bạn có thể gọi phương thức
 * shutdownNow(). Điều này sẽ cố gắng ngăn chặn tất cả các nhiệm vụ ngay lập tức
 * và loại bỏ các nhiệm vụ đã được đưa vào Queue nhưng chưa được thực thi. Không
 * có gì đảm bảo về việc tắt các nhiệm vụ đang chạy hoàn toàn, nhưng phương thức
 * này sẽ cố gắng để tắt tất cả chúng. <br/>
 * Lưu ý: Sử dụng phương thức shutdownNow() cũng tương tự việc sử dụng
 * interupt() trong Thread. Để an toàn và chắc chắn hơn khi stop các task đang
 * được thực thi bạn nên thêm vào 1 biến flag chung cho các nhiệm vụ, khi bạn
 * stop tất cả Thread sử dụng kèm cả shutdownNow() và set giá trị cho biến flag
 * để quá trình dừng lại.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ThreadPoolExecutorExample {

	public static void main(String args[]) throws InterruptedException {

		// the number of threads to keep in the pool, even if they are idle
		final int CORE_POOL_SIZE = 2; //

		// the maximum number of threads to allow in the pool
		final int MAX_POOL_SIZE = 4;

		// the queue to use for holding tasks before they are executed
		final long KEEP_ALIVE_TIME = 10;

		// the queue to use for holding tasks before they are executed. This
		// queue will
		// hold only the Runnable tasks submitted by the execute method.
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(2);

		// RejectedExecutionHandler implementation
		RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();

		// The handler to use when execution is blocked because the thread
		// bounds
		// and queue capacities are reached
		// ThreadFactory threadFactory = Executors.defaultThreadFactory();
		ThreadFactory threadFactory = new ThreadFactoryBuilder().setNamePrefix("GPCoder-ThreadPool").setDaemon(false)
				.setPriority(Thread.MAX_PRIORITY).setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
					@Override
					public void uncaughtException(Thread t, Throwable e) {
						System.err.println(String.format("Custom Exception: Thread %s threw exception - %s",
								t.getName(), e.getMessage()));

					}
				}).build();

		// creating the ThreadPoolExecutor
		ThreadPoolExecutor executorPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
				TimeUnit.SECONDS, workQueue, threadFactory, rejectionHandler);

		// start the monitoring thread
		MonitorThread monitor = new MonitorThread(executorPool, 3);
		Thread monitorThread = new Thread(monitor);
		monitorThread.start();

		// submit work to the thread pool
		for (int i = 1; i <= 10; i++) {
			executorPool.execute(new WorkerThread("cmd" + i));
		}

		Thread.sleep(30000);

		// shut down the pool
		executorPool.shutdown();

		// shut down the monitor thread
		Thread.sleep(5000);
		monitor.shutdown();

	}
}