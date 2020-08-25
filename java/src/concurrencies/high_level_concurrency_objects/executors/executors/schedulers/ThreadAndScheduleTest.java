package executors.schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreadAndScheduleTest {

	public static ExecutorService cachedPool1 = Executors
			.newScheduledThreadPool(100);
	public static ExecutorService cachedPool2 = Executors
			.newSingleThreadScheduledExecutor();

	public static void main(String[] args) {
		ExecutorService cachedPool = Executors.newCachedThreadPool();
		cachedPool.execute(new MyThread("thread 1"));

		ScheduledExecutorService newScheduledThreadPool3 = Executors
				.newScheduledThreadPool(100);
		ScheduledExecutorService newScheduledThreadPool2 = newScheduledThreadPool3;
		ScheduledExecutorService newScheduledThreadPool = newScheduledThreadPool2;
		ScheduledExecutorService scheduledPool = newScheduledThreadPool;
		scheduledPool.schedule(new MyThread("thread 2"), 2, TimeUnit.SECONDS);
		scheduledPool.scheduleAtFixedRate(new MyThread("thread 3"), 1, 3,
				TimeUnit.SECONDS);
		scheduledPool.scheduleWithFixedDelay(new MyThread("thread 4"), 1, 5,
				TimeUnit.SECONDS);

		// 不重复的定时任务
		ScheduledExecutorService ss = Executors.newScheduledThreadPool(10);
		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			ss.schedule(new MyThread("thread " + i), 3000,
					TimeUnit.MILLISECONDS);
		}
	}

}

class MyThread implements Runnable {

	private String name;

	public MyThread(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		System.out.println(name + "-----MyThread------");

	}

}