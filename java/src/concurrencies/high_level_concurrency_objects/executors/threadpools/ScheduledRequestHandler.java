package threadpools;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ScheduledRequestHandler implements Runnable {
	String name;
	AtomicInteger i;

	public ScheduledRequestHandler(String name) {
		this.name = name;
		this.i = new AtomicInteger(1);
	}

	@Override
	public void run() {
		try {
			// Bắt đầu xử lý request đến
			System.out.println(Thread.currentThread().getName() + " Starting process " + name + i.get());
			// cho ngủ 500 milis để ví dụ là quá trình xử lý mất 0,5 s
			Thread.sleep(500);
			// Kết thúc xử lý request
			System.out.println(Thread.currentThread().getName() + " Finished process " + name + i.getAndIncrement());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}