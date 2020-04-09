package threadpools;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import learn.threads.vn.RequestHandler;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class Main {
	private static ThreadFactory threadFactory = new CustomThreadFactory();

	public static void main(String[] args) {
		try {
			// singleThreadExecutorDemo();
			// fixedThreadPoolExecutorDemo();
			// cachedThreadPoolExecutorDemo();
			// scheduledThreadPoolExecutorDemo();
			folkJoinPoolDemo();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void singleThreadExecutorDemo() {
		ExecutorService executor = Executors.newSingleThreadExecutor(threadFactory);
		// Có 10 request tới cùng lúc
		for (int i = 0; i < 100; i++) {
			executor.execute(new RequestHandler("request-" + i));
		}
		executor.shutdown(); // Không cho threadpool nhận thêm nhiệm vụ nào nữa
		while (!executor.isTerminated()) {
			// Chờ xử lý hết các request còn chờ trong Queue ...
		}
		System.out.println("Number of threads created:" + CustomThreadFactory.i);
	}

	private static void fixedThreadPoolExecutorDemo() {
		ExecutorService executor = Executors.newFixedThreadPool(5, threadFactory);
		// Có 10 request tới cùng lúc
		for (int i = 0; i < 100; i++) {
			executor.execute(new RequestHandler("request-" + i));
		}
		executor.shutdown(); // Không cho threadpool nhận thêm nhiệm vụ nào nữa
		while (!executor.isTerminated()) {
			// Chờ xử lý hết các request còn chờ trong Queue ...
		}
		System.out.println("Number of threads created:" + CustomThreadFactory.i);
	}

	private static void cachedThreadPoolExecutorDemo() throws InterruptedException {
		ExecutorService executor = Executors.newCachedThreadPool(threadFactory);
		// Có 10 request tới cùng lúc
		for (int i = 0; i < 100; i++) {
			executor.execute(new RequestHandler("request-" + i));
			Thread.sleep(200);
		}
		executor.shutdown(); // Không cho threadpool nhận thêm nhiệm vụ nào nữa
		while (!executor.isTerminated()) {
			// Chờ xử lý hết các request còn chờ trong Queue ...
		}
		System.out.println("Number of threads created:" + CustomThreadFactory.i);
	}

	private static void scheduledThreadPoolExecutorDemo() throws InterruptedException, ExecutionException {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(20, threadFactory);
		// Đặt lịch chạy task với delay tính bằng milli giây
		ScheduledRequestHandler scheduledRequestHandler = new ScheduledRequestHandler("request-");
		ScheduledFuture scheduledFuture1 = executor.schedule(scheduledRequestHandler, 5000, TimeUnit.MILLISECONDS);
		while (!scheduledFuture1.isDone()) {
			if (scheduledRequestHandler.i.get() > 20) {
				scheduledFuture1.cancel(false); // Hủy chạy task
			}
		}
		// // Đặt lịch chạy task với delay tính bằng milli giây, lặp lại sau mỗi
		// khoảng thời gian (tính từ lúc bắt đầu task trước đến lúc bắt đầu task
		// tiếp theo)
		ScheduledFuture scheduledFuture2 = executor.scheduleAtFixedRate(scheduledRequestHandler, 2000, 500,
				TimeUnit.MILLISECONDS);
		while (!scheduledFuture2.isDone()) {
			if (scheduledRequestHandler.i.get() > 40) {
				scheduledFuture2.cancel(false); // Hủy chạy task
			}
		}
		// Đặt lịch chạy task với delay tính bằng milli giây, lặp lại sau mỗi
		// khoảng thời gian (tính từ lúc kết thúc task trước đến lúc bắt đầu
		// task tiếp theo)
		ScheduledFuture scheduledFuture3 = executor.scheduleWithFixedDelay(scheduledRequestHandler, 2000, 500,
				TimeUnit.MILLISECONDS);
		while (!scheduledFuture3.isDone()) {
			if (scheduledRequestHandler.i.get() > 60) {
				scheduledFuture3.cancel(false); // Hủy chạy task
			}
		}
		executor.shutdown();
		System.out.println("Number of threads created:" + CustomThreadFactory.i);
	}

	private static void folkJoinPoolDemo() {
		TreeNode tree = new TreeNode(5, new TreeNode(3), new TreeNode(2, new TreeNode(2), new TreeNode(8)));
		ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
		int sum = forkJoinPool.invoke(new CountingTask(tree));
		System.out.println(sum);
	}
}