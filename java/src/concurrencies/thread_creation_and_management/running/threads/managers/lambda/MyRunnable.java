package running.threads.managers.lambda;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class MyRunnable implements Runnable {

	@Override
	public void run() {
		try {
			String threadName = Thread.currentThread().getName();
			System.out.println("Running in " + threadName);
			Thread.sleep(5000);
			System.out.println("Finish in " + threadName);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}