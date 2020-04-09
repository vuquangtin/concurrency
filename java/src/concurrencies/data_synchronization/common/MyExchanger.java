package common;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class MyExchanger {
	public static void main(String[] args) {
		ExecutorService service = new ThreadPoolExecutor(3, 4, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<>(3),
				new ThreadPoolExecutor.DiscardOldestPolicy());

		final Exchanger exchanger = new Exchanger();

		service.execute(() -> {
			try {
				String str = "test";
				System.out.println("线程" + Thread.currentThread().getName() + "正在把数据" + str + "换出去");

				Thread.sleep(3000);
				String str1 = (String) exchanger.exchange(str);
				System.out.println("线程" + Thread.currentThread().getName() + "换回来的数据为" + str1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		service.execute(() -> {
			try {
				String tes = "testtt";
				System.out.println("线程" + Thread.currentThread().getName() + "正在把数据" + tes + "换出去");

				Thread.sleep(3000);
				String tes1 = (String) exchanger.exchange(tes);
				System.out.println("线程" + Thread.currentThread().getName() + "换回来的数据为" + tes1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		});
		service.shutdown();
	}
}