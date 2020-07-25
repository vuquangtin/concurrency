package common;

import java.util.Date;
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
public class MyJoin implements Runnable {
	@Override
	public void run() {
		System.out.printf("Test Begining data source loading: 时间：%s  数字：%d\n", new Date(), 10000);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.printf("Test loading has finished:%s\n", new Date());
	}

	static class JoinTest1 implements Runnable {

		@Override
		public void run() {
			System.out.printf("Test1 Begining data source loading: %s\n", new Date());
			try {
				TimeUnit.SECONDS.sleep(6);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.printf("Test1 loading has finished:%s\n", new Date());
		}
	}

	public static void main(String[] args) throws InterruptedException {
		MyJoin test = new MyJoin();
		Thread thread = new Thread(test, "test");

		JoinTest1 test1 = new JoinTest1();
		Thread thread1 = new Thread(test1, "test1");
		thread.start();
		thread1.start();

		thread.join();
		thread1.join();
		// 等到thread和thread1运行完之后才会运行
		System.out.println("Finshed!!");

	}
}