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
public class UsingRunnable implements Runnable {

	private int cnt = 0;

	@Override
	public void run() {
		cnt++;
		System.out.println("Tao thread su dung tu khoa implements: " + cnt);
	}

}