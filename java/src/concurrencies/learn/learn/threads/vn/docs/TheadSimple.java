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
public class TheadSimple extends Thread {
	public void run() {
		System.out.println("thread is running...");
	}

	public static void main(String args[]) {
		TheadSimple t1 = new TheadSimple();
		t1.start();
	}
}