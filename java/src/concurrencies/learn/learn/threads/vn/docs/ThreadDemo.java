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
public class ThreadDemo {
	public static void main(String[] args) {
		// Tao doi tuong
		FirstThread ft = new FirstThread();
		SecondThread st = new SecondThread();

		// Bat dau thread thu nhat
		Thread t1 = new Thread(ft);
		t1.start();

		// Bat dau thread thu hai
		Thread t2 = new Thread(st);
		t2.start();

	}
}