package learn.threads.vn.docs;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class FirstThread implements Runnable {

	@Override
	public void run() {
		for (int i = 1; i <= 5; i++) {
			System.out.println("Thong diep tu thread dau tien: " + i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ie) {
				System.err.println(ie.toString());
			}
		}
	}
}
