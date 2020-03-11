package learn.threads.vn;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreadTest extends Thread {
	public void run() {
		System.out.println("Make a thread…");
	}

	public static void main(String args[]) {
		ThreadTest t1 = new ThreadTest();
		t1.start();
	}
}
