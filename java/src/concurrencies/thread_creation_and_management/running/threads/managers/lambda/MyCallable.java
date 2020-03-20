package running.threads.managers.lambda;

import java.util.concurrent.Callable;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class MyCallable implements Callable<String> {

	@Override
	public String call() {
		String threadName = Thread.currentThread().getName();
		System.out.println("Running in " + threadName);
		try {
			Thread.sleep(5000);
			return threadName;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
}