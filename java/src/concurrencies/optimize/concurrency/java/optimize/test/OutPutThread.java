package concurrency.java.optimize.test;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class OutPutThread extends Thread {
	@Override
	public void run() {
		try {
			Thread.sleep(2000);
			System.out.println(System.currentTimeMillis() + " run "
					+ Thread.currentThread().getName());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
