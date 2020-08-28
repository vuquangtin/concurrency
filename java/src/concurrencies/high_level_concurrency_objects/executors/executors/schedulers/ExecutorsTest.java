package executors.schedulers;

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
public class ExecutorsTest {
	public static void main(String[] args) {
		ScheduledExecutorService newScheduledThreadPool = Executors
				.newScheduledThreadPool(1);
		newScheduledThreadPool.schedule(new Runnable() {
			@Override
			public void run() {
				System.out.println("我在跑");
			}
		}, 3, TimeUnit.SECONDS);

		newScheduledThreadPool.schedule(new Runnable() {
			@Override
			public void run() {
				System.out.println("我是第二个");

			}
		}, 1, TimeUnit.SECONDS);

		newScheduledThreadPool.schedule(new Runnable() {
			@Override
			public void run() {
				System.out.println("我是第三个");

			}
		}, 8, TimeUnit.SECONDS);
		newScheduledThreadPool.shutdown();
	}
}
